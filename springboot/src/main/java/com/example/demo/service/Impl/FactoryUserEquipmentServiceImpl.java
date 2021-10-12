package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.FactoryUserEquipmentVO;
import com.example.demo.mapper.FactoryUserEquipmentMapper;
import com.example.demo.service.FactoryUserEquipmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FactoryUserEquipmentServiceImpl extends ServiceImpl<FactoryUserEquipmentMapper, FactoryUserEquipmentVO> implements FactoryUserEquipmentService {
    @Resource
    private FactoryUserEquipmentMapper factoryUserEquipmentMapper;

    public Page<FactoryUserEquipmentVO> getFUE(Page<FactoryUserEquipmentVO> page, String search,int uId) {
        return page.setRecords(factoryUserEquipmentMapper.getFactoryUserEquipment(page,search,uId));
    }

    public Page<FactoryUserEquipmentVO> getF(Page<FactoryUserEquipmentVO> page,int uId){
        return page.setRecords(factoryUserEquipmentMapper.getFactory(page,uId));
    }
}
