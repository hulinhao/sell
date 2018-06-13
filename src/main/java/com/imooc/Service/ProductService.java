package com.imooc.Service;

import com.imooc.dao.ProductInfoDao;
import com.imooc.dto.CartDto;
import com.imooc.entity.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface ProductService {

    //查询所有上架商品
    List<ProductInfo> findByProductStatus();

    /** 加库存 **/
    void increaseStock(List<CartDto> cartDtoList);

    /** 减库存 **/
    void decreaseStock(List<CartDto> cartDtoList);
}
