package com.imooc.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {

    PARAMS_ERROR(200,"参数不正确"),
    OPENID_EMPTY(201,"openid为空！"),

    PRODUCT_NOT_EXIST(100,"商品不存在"),
    PRODUCT_STOCK_DEFICIENCY(101,"库存不足"),
    ORDER_NOT_EXIST(102,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(103,"订单详情不存在"),
    ORDER_STATUS_ERROR(104,"订单状态不正确"),
    ORDER_UPDATE_ERROR(105,"订单更新失败"),
    ORDER_DETAIL_EMPTY(106,"订单详情为空"),
    ORDER_PAY_STATUS_ERROR(107,"订单支付状态不正确"),
    CART_EMPTY(108,"购物车为空"),

    WECHAT_MP_ERROR(110,"微信公众号错误");

    ;

    private Integer code ;
    private  String msg;

    ResultEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
