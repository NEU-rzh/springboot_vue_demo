package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("type_of_product")
@Data
public class TypeOfProduct {
    @TableId(value="type_of_product_id",type= IdType.AUTO)
    private int typeOfProductId;
    private String productType;
}