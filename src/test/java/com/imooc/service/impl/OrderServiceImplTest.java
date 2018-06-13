package com.imooc.service.impl;

import com.imooc.Service.OrderService;
import com.imooc.Service.impl.OrderServiceImpl;
import com.imooc.dto.CartDto;
import com.imooc.dto.OrderDto;
import com.imooc.entity.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired private OrderServiceImpl orderService;

    private final String buyerOpenid = "abc123";

    @Test
    public void create() {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName("hulinhao");
        orderDto.setBuyerOpenid(buyerOpenid);
        orderDto.setBuyerAddress("武汉");
        orderDto.setBuyerPhone("13100678593");

        List<OrderDetail> cartDtoList = new ArrayList<OrderDetail>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("aaa");
        orderDetail.setProductQuantity(7);
        cartDtoList.add(orderDetail);

        orderDto.setOrderDetailList(cartDtoList);
        orderService.create(orderDto);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findList() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}