package com.imooc.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class OrderDetail {

    @Id
    private String detailId;
    private String orderId;
    private String productId;
    /** 商品名称**/
    private String productName;
    /** 当前价格,单位分**/
    private BigDecimal productPrice;
    /** 数量**/
    private int productQuantity;
    /** 小图**/
    private String productIcon;
    /** 创建时间**/
    private String createTime;
    /** 修改时间**/
    private String updateTime;

}
