package com.imooc.Service;

import com.imooc.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    //查询多个
    List<ProductCategory> findByCategoryIdIn(List<Integer> categoryIdIn);

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeIn);

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    ProductCategory save(ProductCategory productCategory);
}
