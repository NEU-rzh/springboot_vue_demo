package com.example.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.VO.OrderReceiverVO;

public interface OrderReceiverService extends IService<OrderReceiverVO> {
    Page<OrderReceiverVO> getOR(Page<OrderReceiverVO> page, String search);

    Page<OrderReceiverVO> getORB(Page<OrderReceiverVO> page, String search);

    Page<OrderReceiverVO> getORS(Page<OrderReceiverVO> page, String search,int facId);

    Page<OrderReceiverVO> getORT(Page<OrderReceiverVO> page, String search,String uName);
}
