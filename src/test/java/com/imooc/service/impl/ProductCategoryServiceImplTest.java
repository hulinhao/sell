package com.imooc.service.impl;

import com.imooc.Service.impl.ProductCategoryServiceImpl;
import com.imooc.entity.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import java.awt.print.Pageable;
import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    ProductCategoryServiceImpl productCategoryService;
    Pageable pageable;

    @Test
    public void findByCategoryIdIn() {
        System.out.println(productCategoryService.findByCategoryIdIn(Arrays.asList(2,3,4)).size());
    }

    @Test
    public void findOne() {
        System.out.println(productCategoryService.findOne(1));
    }

    @Test
    public void findAll() {
        System.out.println(productCategoryService.findAll().size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("石林");
        productCategory.setCategoryType(22);
        System.out.println(productCategoryService.save(productCategory));

    }
}