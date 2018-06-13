package com.imooc.Service.impl;

import com.imooc.Exception.SellException;
import com.imooc.Service.ProductService;
import com.imooc.dao.ProductInfoDao;
import com.imooc.dto.CartDto;
import com.imooc.entity.ProductInfo;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.enums.ResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductInfoDao productInfoDao;

    /** 查询上架商品 **/
    @Override
    public List<ProductInfo> findByProductStatus() {
        return productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public void increaseStock(List<CartDto> cartDtoList) {
        for(CartDto cartDto:cartDtoList) {
            ProductInfo productInfo = productInfoDao.findById(cartDto.getProductId()).get();
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer number = productInfo.getProductStock() + cartDto.getProductQuantity();

            productInfo.setProductStock(number);
            productInfoDao.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {
        for(CartDto cartDto:cartDtoList) {
            ProductInfo productInfo = productInfoDao.findById(cartDto.getProductId()).get();
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer number = productInfo.getProductStock() - cartDto.getProductQuantity();
            if(number < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_DEFICIENCY);
            }
            productInfo.setProductStock(number);
            productInfoDao.save(productInfo);
        }
    }


}
