package com.imooc.controller;

import com.imooc.Exception.SellException;
import com.imooc.Service.OrderService;
import com.imooc.converter.OrderForm2OrderDtoConverter;
import com.imooc.dto.OrderDto;
import com.imooc.enums.ResultEnum;
import com.imooc.form.OrderForm;
import com.imooc.utils.ResultVoUtil;
import com.imooc.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order/")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    //创建订单
    @RequestMapping("create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
         if(bindingResult.hasErrors()){
             log.error("【参数不正确】orderForm:{}"+orderForm);
             throw new SellException(ResultEnum.PARAMS_ERROR.getCode(),
                     bindingResult.getFieldError().getDefaultMessage());
         }
         OrderDto orderDto = OrderForm2OrderDtoConverter.Converter(orderForm);
         if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
             log.error("【创建订单】购物车不能为空");
             throw new SellException(ResultEnum.CART_EMPTY);
         }
         OrderDto createResult = orderService.create(orderDto);
         Map<String,Object> map = new HashMap<>();
         map.put("orderId",createResult.getOrderId());
         return ResultVoUtil.success(map);
    }

    //订单列表
    @RequestMapping("list")
    public ResultVo<OrderDto> list(@RequestParam("openid") String openid, @RequestParam("page") int page,@RequestParam("size") int size){

        if(StringUtils.isEmpty(openid)){
            log.error("【订单列表】openid为空！");
            return ResultVoUtil.error(ResultEnum.OPENID_EMPTY);
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<OrderDto> orderDtoList = orderService.findList(openid,pageable);

        return ResultVoUtil.success(orderDtoList.getContent());
    }

    //订单详情
    @RequestMapping("detail")
    public ResultVo<OrderDto> detail(@RequestParam("orderId") String orderId ,@RequestParam("openid") String openid){
        if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(openid)){
            log.error("【订单详情】orderId为空");
            return ResultVoUtil.error(ResultEnum.PARAMS_ERROR);
        }
        OrderDto orderDto = orderService.findOne(orderId);
        return ResultVoUtil.success(orderDto);
    }

    //取消订单
    @RequestMapping("cancel")
    public ResultVo cancel(@RequestParam("orderId") String orderId ,@RequestParam("openid") String openid){
        if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(openid)){
            log.error("【订单详情】orderId为空");
            return ResultVoUtil.error(ResultEnum.PARAMS_ERROR);
        }
        OrderDto orderDto_= orderService.findOne(orderId);
        OrderDto orderDto = orderService.cancel(orderDto_);
        return ResultVoUtil.success(orderDto);
    }
}
