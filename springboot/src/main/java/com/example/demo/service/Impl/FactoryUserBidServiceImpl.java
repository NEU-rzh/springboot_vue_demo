package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.FactoryUserBidVO;
import com.example.demo.VO.FactoryUserEquipmentVO;
import com.example.demo.mapper.FactoryUserBidMapper;
import com.example.demo.mapper.FactoryUserEquipmentMapper;
import com.example.demo.service.FactoryUserBidService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FactoryUserBidServiceImpl extends ServiceImpl<FactoryUserBidMapper, FactoryUserBidVO> implements FactoryUserBidService {
    @Resource
    private FactoryUserBidMapper factoryUserBidMapper;

    public Page<FactoryUserBidVO> getFUB(Page<FactoryUserBidVO> page, int oId) {
        return page.setRecords(factoryUserBidMapper.getfub(page,oId));
}
}
