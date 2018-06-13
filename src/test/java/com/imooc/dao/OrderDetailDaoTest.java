package com.imooc.dao;

import com.imooc.entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {

    @Autowired OrderDetailDao orderDetailDao;
    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetails = orderDetailDao.findByOrderId("abc");
        Assert.assertEquals(2,orderDetails.size());
    }
    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail() ;
        orderDetail.setOrderId("add");
        orderDetail.setDetailId("333");
        orderDetail.setProductIcon("http://192.168.232.128/cai.jpg");
        orderDetail.setProductId("aaa");
        orderDetail.setProductName("烧肉");
        orderDetail.setProductPrice(new BigDecimal(99));
        orderDetail.setProductQuantity(1);

        orderDetailDao.save(orderDetail);
    }
}