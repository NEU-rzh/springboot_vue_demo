package com.example.demo.controller;

import com.example.demo.mapper.ReceiverMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
/**
 * 对一个联系人信息表的控制层的业务逻辑实现方法
 */
@RestController
@RequestMapping("/receiver")
public class ReceiverController {
    @Resource
    ReceiverMapper receiverMapper;
}
