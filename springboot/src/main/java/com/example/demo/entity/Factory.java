package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("factory")
@Data
public class Factory {
    @TableId(value="factory_id",type= IdType.AUTO)
    private int factoryId;
    private String factoryName;
    private String factoryIntroduction;
    private int personInChargeId;
    private int loginState;
    private String type;
}
