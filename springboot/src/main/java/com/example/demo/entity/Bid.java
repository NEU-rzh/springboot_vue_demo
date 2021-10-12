package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("bid")
@Data
public class Bid {
    @TableId(value="bid_id",type= IdType.AUTO)
    private int bidId;
    private int orderId;
    private int factoryId;
    private int bidPrice;
    private int bidStatus;
}
