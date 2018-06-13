package com.imooc.controller;

import com.imooc.Service.ProductCategoryService;
import com.imooc.Service.ProductService;
import com.imooc.entity.ProductCategory;
import com.imooc.entity.ProductInfo;
import com.imooc.utils.ResultVoUtil;
import com.imooc.vo.ProductInfoVo;
import com.imooc.vo.ProductVo;
import com.imooc.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product/")
public class BuyerProductController {

    @Autowired
    ProductService productInfoService;
    @Autowired
    ProductCategoryService productCategoryService;

    @RequestMapping("list")
    public ResultVo getList(){
        //1.查询所有商品
        List<ProductInfo> productInfoList = productInfoService.findByProductStatus();

        //2.查询所有类目
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        //3.拼装数据
        List<ProductVo> productVoList = new ArrayList<ProductVo>();
        for(ProductCategory data:productCategoryList){
            ProductVo productVo = new ProductVo();
            List<ProductInfoVo> productInfoVoList = new ArrayList<ProductInfoVo>();
            for(ProductInfo obj : productInfoList){
                ProductInfoVo productInfoVo = new ProductInfoVo();
                BeanUtils.copyProperties(obj,productInfoVo);
                if(obj.getCategoryType().equals(data.getCategoryType())){
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setCategoryName(data.getCategoryName());
            productVo.setCategoryType(data.getCategoryType());
            productVo.setProductInfoVoList(productInfoVoList);
            productVoList.add(productVo);
        }

        return ResultVoUtil.success(productVoList);
    }
}
