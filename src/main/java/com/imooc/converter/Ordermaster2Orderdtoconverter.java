package com.imooc.converter;

import com.imooc.dto.OrderDto;
import com.imooc.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

public class Ordermaster2Orderdtoconverter {

    //将OrderMaster对象转化为 orderDto 对象
    public static OrderDto orderMasterCaseConvertOrderDto(OrderMaster orderMaster){
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        return orderDto;
    }
}
