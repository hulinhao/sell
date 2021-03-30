package com.imooc.Service.impl;

import com.imooc.Exception.SellException;
import com.imooc.Service.OrderService;
import com.imooc.Service.PayService;
import com.imooc.Service.ProductService;
import com.imooc.converter.Ordermaster2Orderdtoconverter;
import com.imooc.dao.OrderDetailDao;
import com.imooc.dao.OrderMasterDao;
import com.imooc.dao.ProductCategoryDao;
import com.imooc.dao.ProductInfoDao;
import com.imooc.dto.CartDto;
import com.imooc.dto.OrderDto;
import com.imooc.entity.OrderDetail;
import com.imooc.entity.OrderMaster;
import com.imooc.entity.ProductInfo;
import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private OrderMasterDao orderMasterDao;
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductInfoDao productInfoDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private PayService payService;

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //1.查询商品（数量，单价）
        for(OrderDetail orderDetail : orderDto.getOrderDetailList()) {
            ProductInfo productInfo = productInfoDao.findById(orderDetail.getProductId()).get();
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2.计算总价
            orderAmount = new BigDecimal(orderDetail.getProductQuantity())
                    .multiply(productInfo.getProductPrice()).add(orderAmount);
            //3.订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailDao.save(orderDetail);
        }
        //3.写入订单数据库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(OrderStatusEnum.PAY_WAIT.getCode());
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMasterDao.save(orderMaster);
        orderDto.setOrderId(orderId);
        //4.扣库存
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream().map(e ->
                new CartDto(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.decreaseStock(cartDtoList);

        return orderDto;
    }

    @Override
    public OrderDto findOne(String orderId) {
        OrderMaster orderMaster = orderMasterDao.findByOrderId(orderId);
        if(orderMaster == null){
            return null;
        }
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> page = orderMasterDao.findByBuyerOpenid(buyerOpenid,pageable);
        OrderDto orderDto = new OrderDto();
        List<OrderDto> orderDtoList =
                page.getContent().stream().map(e -> Ordermaster2Orderdtoconverter.orderMasterCaseConvertOrderDto(e)).collect(Collectors.toList());
        Page<OrderDto> orderDtoPage = new  PageImpl(orderDtoList,pageable,page.getTotalElements());
        return orderDtoPage;
    }

    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        //判断订单状态
        OrderMaster orderMaster = orderMasterDao.findByOrderId(orderDto.getOrderId());
        if(orderMaster== null){
            log.error("订单:{} 不存在",orderDto.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if(orderMaster.getOrderStatus() != OrderStatusEnum.NEW.getCode()){
            log.error("【取消订单】订单不能取消");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster updateOrderMaster = orderMasterDao.save(orderMaster);
        if(updateOrderMaster == null){
            log.error("【取消订单】失败：{}",orderMaster.toString());
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        //返回库存
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情",orderDto);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDto> cartDtos = orderDto.getOrderDetailList().stream()
                .map(e -> new CartDto(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDtos);
        //如果已支付，需要退款
        if(orderDto.getPayStatus() ==  OrderStatusEnum.PAY_SUCCESS.getCode()){
            //payService.refund(orderDto);
        }

        return Ordermaster2Orderdtoconverter.orderMasterCaseConvertOrderDto(updateOrderMaster);
    }

    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        //判断订单状态
        OrderMaster orderMaster = orderMasterDao.findByOrderId(orderDto.getOrderId());
        if(orderMaster== null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if(orderMaster.getOrderStatus() != OrderStatusEnum.NEW.getCode()){
            log.error("【完结订单】订单状态不正确{}",orderMaster);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster updateOrderMaster = orderMasterDao.save(orderMaster);
        if(updateOrderMaster == null){
            log.error("【取消订单】订单取消失败：{}",orderMaster.toString());
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return Ordermaster2Orderdtoconverter.orderMasterCaseConvertOrderDto(updateOrderMaster);
    }

    @Override
    @Transactional
    public OrderDto paid(OrderDto orderDto) {
        //判断订单状态
        OrderMaster orderMaster = orderMasterDao.findByOrderId(orderDto.getOrderId());
        if(orderMaster== null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if(orderMaster.getOrderStatus() != OrderStatusEnum.NEW.getCode()){
            log.error("【完结订单】订单状态不正确",orderMaster);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(orderMaster.getPayStatus()!= OrderStatusEnum.PAY_WAIT.getCode()){
            log.error("【支付订单】订单不是待支付状态{}",orderMaster);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderMaster.setOrderStatus(OrderStatusEnum.PAY_SUCCESS.getCode());
        OrderMaster updateOrderMaster = orderMasterDao.save(orderMaster);
        return Ordermaster2Orderdtoconverter.orderMasterCaseConvertOrderDto(updateOrderMaster);
    }
}
