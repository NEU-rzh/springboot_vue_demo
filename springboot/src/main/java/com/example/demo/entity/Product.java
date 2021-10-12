package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("product")
@Data
public class Product {
    @TableId(value="product_id",type= IdType.AUTO)
    private int productId;
    private String productName;
    private String productType;
    private String productSpecification;
    private String productIntroduction;
}