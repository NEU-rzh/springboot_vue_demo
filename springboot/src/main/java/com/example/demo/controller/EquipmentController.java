package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.VO.FactoryUserEquipmentVO;
import com.example.demo.VO.FactoryUserVO;
import com.example.demo.common.Result;
import com.example.demo.entity.Equipment;
import com.example.demo.mapper.EquipmentMapper;
import com.example.demo.mapper.FactoryUserEquipmentMapper;
import com.example.demo.service.FactoryUserEquipmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
/**
 * 对一个设备表的控制层的业务逻辑实现方法
 */
@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    @Resource
    EquipmentMapper equipmentMapper;

    @Resource
    FactoryUserEquipmentService factoryUserEquipmentService;
    /**
     * 新增设备信息的方法
     */
    @PostMapping
    public Result<?> save(@RequestBody Equipment equipment){
        equipmentMapper.insert(equipment);
        return Result.success();
    }
    /**
     * 对设备表的信息更新的方法
     */
    @PutMapping
    public Result<?> update(@RequestBody Equipment equipment){
        equipmentMapper.updateById(equipment);
        return Result.success();
    }
    /**
     * 对设备开或关机的方法
     */
    @PutMapping("/equipmentopenclose")
    public Result<?> updateOpenClose(@RequestBody Equipment equipment){
        if (equipment.getEquipmentState().equals("已关闭")) equipment.setEquipmentState("闲置中");
        else if(equipment.getEquipmentState().equals("闲置中")) equipment.setEquipmentState("已关闭");
        equipmentMapper.updateById(equipment);
        return Result.success();
    }
    /**
     * 根据设备ID对设备删除的方法
     */
    @DeleteMapping("/{equipmentId}")
    public Result<?> delete(@PathVariable int equipmentId) {
        equipmentMapper.deleteById(equipmentId);
        return Result.success();
    }
    /**
     * 查找所有设备的方法
     * 对参数search模糊查询
     */
    @GetMapping
    public Result<?> findAll(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Equipment> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Equipment::getEquipmentName, search);
        }
        Page<Equipment> equipmentPage = equipmentMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(equipmentPage);
    }
    /**
     * 查询所有属于该工厂的设备的方法
     */
    @GetMapping("/fequipment")
    public Result<?> findFac(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "") String search,
                             @RequestParam int uId){
        Page<FactoryUserEquipmentVO> factoryUserEquipmentVOPage = factoryUserEquipmentService.getFUE(new Page<>(pageNum,pageSize),"%"+search+"%",uId);
        return Result.success(factoryUserEquipmentVOPage);
    }
    /**
     * 找到对于本工厂的所有空闲设备的方法
     */
    @GetMapping("/free")
    public Result<?> find(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam int uId){
        Page<FactoryUserEquipmentVO> factoryUserEquipmentVOPage = factoryUserEquipmentService.getF(new Page<>(pageNum,pageSize),uId);
        return Result.success(factoryUserEquipmentVOPage);
    }
    /**
     * 找到所有未被租用的设备
     */
    @GetMapping("/rent")
    public Result<?> rent(@RequestParam(defaultValue = "1") Integer pageNumRent,
                            @RequestParam(defaultValue = "10") Integer pageSizeRent,
                            @RequestParam(defaultValue = "") String searchRent){
        LambdaQueryWrapper<Equipment> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(searchRent)) {
            wrapper.like(Equipment::getEquipmentName, searchRent);
        }
        wrapper.eq(Equipment::getRentState,"未被租用");
        Page<Equipment> equipmentPage = equipmentMapper.selectPage(new Page<>(pageNumRent,pageSizeRent),wrapper);
        return Result.success(equipmentPage);
    }

    /**
     * 对产能中心的设备租用的方法
     */
    @PutMapping("rentem")
    public Result<?> rentEm(@RequestBody Equipment equipment,
                            @RequestParam int facId){
        equipment.setRentState("已被租用");
        equipment.setFactoryId(facId);
        equipmentMapper.updateById(equipment);
        return Result.success();
    }
}
