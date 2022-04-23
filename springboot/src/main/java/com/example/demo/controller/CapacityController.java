package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.VO.EquipmentProductCapVO;
import com.example.demo.common.Result;
import com.example.demo.entity.Capacity;
import com.example.demo.entity.Factory;
import com.example.demo.mapper.CapacityMapper;
import com.example.demo.mapper.FactoryMapper;
import com.example.demo.service.EquipmentProductCapService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
/**
 * 对一个产能表的控制层的业务逻辑实现方法
 */
@RestController
@RequestMapping("/cap")
public class CapacityController {
    @Resource
    CapacityMapper capacityMapper;

    @Resource
    EquipmentProductCapService equipmentProductCapService;

    @Resource
    FactoryMapper factoryMapper;

    /**
     * 新建产能的方法
     * 注意判断插入是否有重复
     */
    @PostMapping
    public Result<?> save(@RequestBody Capacity capacity){
        Capacity cap= capacityMapper.selectOne(Wrappers.<Capacity>lambdaQuery().eq(Capacity::getProductId,capacity.getProductId()).eq(Capacity::getEquipmentId,capacity.getEquipmentId()));
        if(cap != null)
        {
            return Result.error("-1", "已有产能，无法增加");
        }else {
            capacityMapper.insert(capacity);
            return Result.success();
        }
    }
    /**
     * 对一个产能表的数据修改的方法
     */
    @PutMapping
    public Result<?> update(@RequestBody Capacity capacity){
        capacityMapper.updateById(capacity);
        return Result.success();
    }
    /**
     * 更具产能的ID对产能表作删除操作
     */
    @DeleteMapping("/{capId}")
    public Result<?> delete(@PathVariable int capId) {
        LambdaQueryWrapper<Capacity> wrapper = Wrappers.<Capacity>lambdaQuery().eq(Capacity::getCapId,capId);
        capacityMapper.delete(wrapper);
        return Result.success();
    }
    /**
     * 找到所有的属于该工厂的设备的产能
     */
    @GetMapping
    public Result<?> findAll(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam int uId){
        int fId = factoryMapper.selectOne(Wrappers.<Factory>lambdaQuery().eq(Factory::getPersonInChargeId,uId)).getFactoryId();

        Page<EquipmentProductCapVO> equipmentProductCapVOPage = equipmentProductCapService.getAll(new Page<>(pageNum,pageSize),fId);
        return Result.success(equipmentProductCapVOPage);
    }
}
