package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("order_tab")
@Data
public class Order {
    @TableId(value="order_id",type= IdType.AUTO)
    private int orderId;
    private String productName;
    private int quantity;
    private Date deliveryTime;
    private int receiverId;
    private Date bidDeadline;
    private String orderState;
}
