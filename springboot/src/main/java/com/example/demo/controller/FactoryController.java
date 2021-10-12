package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.VO.FactoryUserVO;
import com.example.demo.common.Result;
import com.example.demo.entity.Factory;
import com.example.demo.mapper.FactoryMapper;
import com.example.demo.mapper.FactoryUserMapper;
import com.example.demo.service.FactoryUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
/**
 * 对一个工厂表的控制层的业务逻辑实现方法
 */
@RestController
@RequestMapping("/factory")
public class FactoryController {

    @Resource
    FactoryMapper factoryMapper;

    @Resource
    FactoryUserService factoryUserService;

    @Resource
    FactoryUserMapper factoryUserMapper;
    /**
     * 对一个工厂添加的方法
     */
    @PostMapping
    public Result<?> save(@RequestBody Factory factory){
        factoryMapper.insert(factory);
        return Result.success();
    }
    /**
     * 更新工厂信息
     */
    @PutMapping
    public Result<?> update(@RequestBody Factory factory){
        if(factory.getLoginState()==1) factory.setLoginState(0);
        else if(factory.getLoginState()==0) factory.setLoginState(1);
        factoryMapper.updateById(factory);
        return Result.success();
    }
    /**
     * 删除工厂信息
     */
    @DeleteMapping("/{factoryId}")
    public Result<?> delete(@PathVariable int factoryId) {
        factoryMapper.deleteById(factoryId);
        return Result.success();
    }
    /**
     * 模糊查询所有的工厂的方法
     */
    @GetMapping
    public Result<?> findAll(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "") String search){
        Page<FactoryUserVO> factoryUserVOPage = factoryUserService.getFactoryUse(new Page<>(pageNum,pageSize),"%"+search+"%");
        return Result.success(factoryUserVOPage);
    }
    /**
     * 获得本用户的所有工厂的方法
     */
    @GetMapping("/{userId}")
    public Result<?> getUid(@PathVariable int userId){
        int fId = factoryUserMapper.selectById(userId).getFactoryId();
        System.out.println(fId);
        return Result.success(fId);
    }
}
