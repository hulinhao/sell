package com.imooc.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.Exception.SellException;
import com.imooc.dto.OrderDto;
import com.imooc.entity.OrderDetail;
import com.imooc.enums.ResultEnum;
import com.imooc.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OrderForm2OrderDtoConverter {

    public static OrderDto Converter(OrderForm orderForm){
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
        try{
            Gson gson = new Gson();
            orderDetails = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("【对象转换】错误，String = {}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAMS_ERROR);
        }

        orderDto.setOrderDetailList(orderDetails);
        return orderDto;
    }
}
