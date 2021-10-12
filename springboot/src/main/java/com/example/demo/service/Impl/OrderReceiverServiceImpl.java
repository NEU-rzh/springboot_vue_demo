package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.OrderReceiverVO;
import com.example.demo.mapper.OrderReceiverMapper;
import com.example.demo.service.OrderReceiverService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderReceiverServiceImpl extends ServiceImpl<OrderReceiverMapper, OrderReceiverVO> implements OrderReceiverService {
    @Resource
    private OrderReceiverMapper orderReceiverMapper;

    public Page<OrderReceiverVO> getOR(Page<OrderReceiverVO> page, String search) {
        return page.setRecords(orderReceiverMapper.getOrderReceiver(page,search));
    }

    public Page<OrderReceiverVO> getORB(Page<OrderReceiverVO> page, String search) {
        return page.setRecords(orderReceiverMapper.getorb(page,search));
    }

    public Page<OrderReceiverVO> getORS(Page<OrderReceiverVO> page, String search,int facId) {
        return page.setRecords(orderReceiverMapper.getors(page,search,facId));
    }

    public Page<OrderReceiverVO> getORT(Page<OrderReceiverVO> page, String search,String uName) {
        return page.setRecords(orderReceiverMapper.getort(page,search,uName));
    }
}
