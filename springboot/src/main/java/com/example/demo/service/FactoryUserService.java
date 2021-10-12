package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.VO.FactoryUserVO;

public interface FactoryUserService extends IService<FactoryUserVO> {
    Page<FactoryUserVO> getFactoryUse(Page<FactoryUserVO> page,String search);

}
