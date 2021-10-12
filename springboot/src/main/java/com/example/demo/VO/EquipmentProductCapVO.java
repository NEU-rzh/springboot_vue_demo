package com.example.demo.VO;

import com.example.demo.entity.Capacity;
import lombok.Data;

@Data
public class EquipmentProductCapVO extends Capacity {
    int equipmentId;
    int factoryId;
    String productName;
    String equipmentName;
}
