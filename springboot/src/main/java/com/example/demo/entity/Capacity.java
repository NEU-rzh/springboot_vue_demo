package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("capacity_info")
@Data
public class Capacity {
    @TableId(value="cap_id",type= IdType.AUTO)
    private int capId;
    private int equipmentId;
    private int capacity;
    private int productId;
}
