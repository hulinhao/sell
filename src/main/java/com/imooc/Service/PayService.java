package com.imooc.Service;

import com.imooc.dto.OrderDto;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

public interface PayService {

    //发起微信支付
    PayResponse create(OrderDto orderDto);

    //微信支付结果异步通知
    void notify(String notifyString);

    //退款
    RefundResponse refund(OrderDto orderDto);
}
