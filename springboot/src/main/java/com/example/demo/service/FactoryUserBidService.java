package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.VO.FactoryUserBidVO;
import com.example.demo.VO.FactoryUserEquipmentVO;

public interface FactoryUserBidService extends IService<FactoryUserBidVO> {
    Page<FactoryUserBidVO> getFUB(Page<FactoryUserBidVO> page, int oId);
}
