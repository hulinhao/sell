package com.imooc.dao;

import com.imooc.entity.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {

    @Autowired private OrderMasterDao orderMasterDao;

    @Test
    public void findByBuyerOpenid() {
        Page<OrderMaster> orderMasterPage =  orderMasterDao.findByBuyerOpenid("123456_wx",new PageRequest(1,2));
        for(OrderMaster data : orderMasterPage.getContent()){
            System.out.println(data.toString());;
        }
        orderMasterPage.getTotalElements();
    }

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("ppp");
        orderMaster.setBuyerAddress("武汉");
        orderMaster.setBuyerName("hulinhao");
        orderMaster.setBuyerOpenid("123456_wx");
        orderMaster.setBuyerPhone("13100678593");
        orderMaster.setOrderAmount(new BigDecimal(520.00));
        orderMasterDao.save(orderMaster);
    }
}