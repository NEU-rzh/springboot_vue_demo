package com.example.demo.VO;

import com.example.demo.entity.Equipment;
import lombok.Data;

@Data
public class FactoryUserEquipmentVO extends Equipment {
    int factoryId;
    int personInChargeId;
    int userId;
}
