package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.EquipmentProductCapVO;
import com.example.demo.VO.FactoryUserBidVO;
import com.example.demo.mapper.EquipmentProductCapMapper;
import com.example.demo.mapper.FactoryUserBidMapper;
import com.example.demo.service.EquipmentProductCapService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

@Service
public class EquipmentProductCapServiceImpl extends ServiceImpl<EquipmentProductCapMapper, EquipmentProductCapVO> implements EquipmentProductCapService {
    @Resource
    private EquipmentProductCapMapper equipmentProductCapMapper;

    public Page<EquipmentProductCapVO> getAll(Page<EquipmentProductCapVO> page, int fId) {
        return page.setRecords(equipmentProductCapMapper.findAll(page, fId));
    }
}
