package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.VO.FactoryUserBidVO;
import com.example.demo.common.Result;
import com.example.demo.entity.Bid;
import com.example.demo.entity.Equipment;
import com.example.demo.entity.Factory;
import com.example.demo.entity.Order;
import com.example.demo.mapper.BidMapper;
import com.example.demo.mapper.FactoryMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.service.FactoryUserBidService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
/**
 * 对一个Bid表的控制层的业务逻辑实现方法
 */
@RestController
@RequestMapping("/bid")
public class BidController {
    @Resource
    BidMapper bidMapper;

    @Resource
    FactoryMapper factoryMapper;

    @Resource
    FactoryUserBidService factoryUserBidService;

    @Resource
    OrderMapper orderMapper;
    /**
     * 找到所有属于该订单的投标的信息
     */
    @GetMapping()
    public Result<?> findBid(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam int oId){
        Page<FactoryUserBidVO> factoryUserBidVOPage = factoryUserBidService.getFUB(new Page<>(pageNum,pageSize),oId);
        return Result.success(factoryUserBidVOPage);
    }
    /**
     * 对订单进行投标操作
     * 注意对工厂状态的判断
     * 注意对是否重复投标判断
     */
    @PostMapping()
    public Result<?> bid(@RequestBody Bid bid,
                         @RequestParam int uId){
        //获取工厂ID
        LambdaQueryWrapper<Factory> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Factory::getPersonInChargeId,uId);
        int facId = factoryMapper.selectOne(wrapper).getFactoryId();

        int facState = factoryMapper.selectOne(wrapper).getLoginState();
        //对工厂状态进行判断
        if(facState != 1 ){
            return Result.error("-1","工厂已关闭，无法投标");
        }

        //对是否投过标做判断
        Bid b = bidMapper.selectOne(Wrappers.<Bid>lambdaQuery().eq(Bid::getFactoryId,facId).eq(Bid::getOrderId,bid.getOrderId()));
        if (b != null)
        {
            return Result.error("-1","已投过标，无法重复投标");
        }
        else {
            bid.setFactoryId(facId);
            bidMapper.insert(bid);
            return Result.success();
        }
    }
    /**
     * 对投标进行选标的方法
     * 注意设置中标状态为1
     * 设置对应对应订单的状态为投标结束
     */
    @PutMapping("/choosebid")
    public Result<?> chooseBid(@RequestBody Bid bid) {
        bid.setBidStatus(1);
        bidMapper.updateById(bid);
        return Result.success();
    }
}
