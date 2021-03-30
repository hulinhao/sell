package com.imooc.controller;

import com.imooc.Exception.SellException;
import com.imooc.Service.OrderService;
import com.imooc.dto.OrderDto;
import com.imooc.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private OrderService orderService;

//    //支付订单
//    @RequestMapping("create")
//    public PayResponse create(@RequestParam("orderId") String orderId,
//                              @RequestParam("returnUrl") String returnUrl){
//        //1.查询订单
//        OrderDto orderDto = orderService.findOne(orderId);
//        if(orderDto == null){
//            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
//        }
//        //TODO 2.发起支付   （对接微信支付）
//        return new PayResponse();
//    }

    //微信支付回调
    @RequestMapping("notify")
    public ModelAndView notify(@RequestParam String notifyString){
        //1.回调处理

        //2.通知微信回调已经处理（微信停止回调）
        return new ModelAndView("pay/success");
    }
}
