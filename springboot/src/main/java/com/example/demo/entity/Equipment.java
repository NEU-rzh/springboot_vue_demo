package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("equipment")
@Data
public class Equipment {
    @TableId(value="equipment_id",type= IdType.AUTO)
    private int equipmentId;
    private String equipmentName;
    private String equipmentType;
    private String equipmentSpecification;
    private String equipmentState;
    private String rentState;
    private int factoryId;
}
