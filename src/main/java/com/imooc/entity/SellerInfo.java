package com.imooc.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@DynamicUpdate
public class SellerInfo {
    @Id
    private String id;
    private String username;
    private String password;
    private String openid;
    private String createTime;
    private String updateTime;
}
