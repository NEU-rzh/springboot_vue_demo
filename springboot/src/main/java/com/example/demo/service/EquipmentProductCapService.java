package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.VO.EquipmentProductCapVO;
import com.example.demo.VO.FactoryUserBidVO;

public interface EquipmentProductCapService extends IService<EquipmentProductCapVO>{
    Page<EquipmentProductCapVO> getAll(Page<EquipmentProductCapVO> page, int fId);
}
