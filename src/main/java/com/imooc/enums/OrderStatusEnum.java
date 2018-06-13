package com.imooc.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"取消"),
    PAY_WAIT(0,"等待支付"),
    PAY_SUCCESS(1,"支付完成"),
    ;

    private int code ;
    private String msg;

    OrderStatusEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
