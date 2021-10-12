package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.VO.FactoryUserEquipmentVO;

public interface FactoryUserEquipmentService extends IService<FactoryUserEquipmentVO> {
    Page<FactoryUserEquipmentVO> getFUE(Page<FactoryUserEquipmentVO> page, String search,int oId);

    Page<FactoryUserEquipmentVO> getF(Page<FactoryUserEquipmentVO> page,int uId);
}
