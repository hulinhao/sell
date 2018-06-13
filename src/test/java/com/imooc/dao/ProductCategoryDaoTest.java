package com.imooc.dao;

import com.imooc.entity.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest {

    @Autowired ProductCategoryDao productCategoryDao;

    @Test
    public void findAll(){
        List<ProductCategory> productCategory = productCategoryDao.findAll();
        System.out.println(productCategory.get(0).getCategoryName());
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("热销榜");
        productCategory.setCategoryType(4);
        productCategoryDao.save(productCategory);
    }

    @Test
    public void updateTest(){
        Optional<ProductCategory> productCategory = productCategoryDao.findById(6);
        productCategory.get().setCategoryType(5);
        productCategoryDao.save(productCategory.get());
    }

    @Test
    public void findByCategoryIdIn(){
        List<ProductCategory> list = productCategoryDao.findByCategoryIdIn(Arrays.asList(1,2,3,4));
        System.out.println(list.size());
    }
}