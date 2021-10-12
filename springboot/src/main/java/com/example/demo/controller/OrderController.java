package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.VO.FactoryUserVO;
import com.example.demo.VO.OrderReceiverVO;
import com.example.demo.common.Result;
import com.example.demo.entity.Factory;
import com.example.demo.entity.Order;
import com.example.demo.entity.Receiver;
import com.example.demo.mapper.FactoryMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.ReceiverMapper;
import com.example.demo.service.OrderReceiverService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
/**
 * 对一个订单表的控制层的业务逻辑实现方法
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    OrderMapper orderMapper;

    @Resource
    OrderReceiverService orderReceiverService;

    @Resource
    FactoryMapper factoryMapper;

    @Resource
    ReceiverMapper receiverMapper;
    /**
     * 新增订单的方法
     */
    @PostMapping
    public Result<?> save(@RequestBody Order order){
        orderMapper.insert(order);
        return Result.success();
    }
    /**
     * 更新订单的方法
     */
    @PutMapping
    public Result<?> update(@RequestBody Order order){
        orderMapper.updateById(order);
        return Result.success();
    }
    /**
     * 对选标操作的方法
     */
    @PutMapping("/choosebid")
    public Result<?> choose(@RequestBody Order order) {
        order.setOrderState("投标结束");
        orderMapper.updateById(order);
        return Result.success();
    }
    /**
     * 删除订单的方法
     */
    @DeleteMapping("/{orderId}")
    public Result<?> delete(@PathVariable int orderId) {
        orderMapper.deleteById(orderId);
        return Result.success();
    }
    /**
     * 查询所有订单信息的方法
     */
    @GetMapping
    public Result<?> findAll(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "") String search){
//        LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
//        if (StrUtil.isNotBlank(search)) {
//            wrapper.like(Order::getProductName, search);
//        }
//        Page<Order> orderPage = orderMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
//        return Result.success(orderPage);
        Page<OrderReceiverVO> orderReceiverVOPage = orderReceiverService.getOR(new Page<>(pageNum,pageSize),"%"+search+"%");
        return Result.success(orderReceiverVOPage);
    }
    /**
     * 更新订单和投标信息联合查询的方法
     */
    @GetMapping("/bid")
    public Result<?> findBid(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "") String search){
        Page<OrderReceiverVO> orderReceiverVOPage = orderReceiverService.getORB(new Page<>(pageNum,pageSize),"%"+search+"%");
        return Result.success(orderReceiverVOPage);
    }
    /**
     * 查询本用户的订单及联系人信息的联合查询的方法
     */
    @GetMapping("/schedule")
    public Result<?> findSchedule(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "") String search,
                                @RequestParam int uId){
        int facId = factoryMapper.selectOne(Wrappers.<Factory>lambdaQuery().eq(Factory::getPersonInChargeId,uId)).getFactoryId();
        Page<OrderReceiverVO> orderReceiverVOPage = orderReceiverService.getORS(new Page<>(pageNum,pageSize),"%"+search+"%",facId);
        return Result.success(orderReceiverVOPage);
    }
    /**
     * 查询所有属于登录经销商的订单
     */
    @GetMapping("/trader")
    public Result<?> findTraderOrder(@RequestParam(defaultValue = "1") Integer pageNum,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "") String search,
                                     @RequestParam String uName){
        Page<OrderReceiverVO> orderReceiverVOPage = orderReceiverService.getORT(new Page<>(pageNum,pageSize),"%"+search+"%",uName);
        return Result.success(orderReceiverVOPage);
    }
    /**
     * 更新订单状态为完工的方法
     */
    @PutMapping("/schedule1")
    public Result<?> update1(@RequestBody Order order){
        order.setOrderState("完工");
        orderMapper.updateById(order);
        return Result.success();
    }
    /**
     * 更新订单状态为已发货的方法
     */
    @PutMapping("/schedule2")
    public Result<?> update2(@RequestBody Order order){
        order.setOrderState("已发货");
        orderMapper.updateById(order);
        return Result.success();
    }
    /**
     * 更新订单状态为已完成的方法
     */
    @PutMapping("/schedule3")
    public Result<?> update3(@RequestBody Order order){
        order.setOrderState("已完成");
        orderMapper.updateById(order);
        return Result.success();
    }
}
