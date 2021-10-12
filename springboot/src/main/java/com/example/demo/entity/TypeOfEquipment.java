package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("type_of_equipment")
@Data
public class TypeOfEquipment {
    @TableId(value="equipment_type_id",type= IdType.AUTO)
    private int equipmentTypeId;
    private String equipmentType;
}
