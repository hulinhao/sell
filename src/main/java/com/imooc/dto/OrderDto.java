package com.imooc.dto;

import com.imooc.entity.OrderDetail;
import com.imooc.enums.OrderStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单类
 */
@Data
public class OrderDto {

    private String orderId;
    /** 买家名字**/
    private String buyerName;
    /** 买家电话**/
    private String buyerPhone;
    /** 买家地址**/
    private String buyerAddress;
    /** 买家微信openid**/
    private String buyerOpenid;
    /** 订单总金额'**/
    private BigDecimal orderAmount ;
    /** 订单状态, 默认为新下单**/
    private int orderStatus ;
    /** 支付状态, 默认未支付**/
    private int payStatus ;
    /** 创建时间**/
    private Date createTime;
    /**更新时间**/
    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
