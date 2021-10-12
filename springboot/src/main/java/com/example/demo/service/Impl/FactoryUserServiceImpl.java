package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.FactoryUserVO;
import com.example.demo.entity.Factory;
import com.example.demo.mapper.FactoryMapper;
import com.example.demo.mapper.FactoryUserMapper;
import com.example.demo.service.FactoryUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FactoryUserServiceImpl extends ServiceImpl<FactoryUserMapper, FactoryUserVO> implements FactoryUserService {
    @Resource
    private FactoryUserMapper factoryUserMapper;

    public Page<FactoryUserVO> getFactoryUse(Page<FactoryUserVO> page,String search) {
        return page.setRecords(factoryUserMapper.getFactoryUser(page,search));
    }
}