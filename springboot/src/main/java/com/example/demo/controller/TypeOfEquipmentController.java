package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Equipment;
import com.example.demo.entity.TypeOfEquipment;
import com.example.demo.mapper.EquipmentMapper;
import com.example.demo.mapper.TypeOfEquipmentMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
/**
 * 对设备种类表的控制层的业务逻辑实现方法
 */
@RestController
@RequestMapping("/typeofequipment")
public class TypeOfEquipmentController {

    @Resource
    TypeOfEquipmentMapper typeOfEquipmentMapper;

    @Resource
    EquipmentMapper equipmentMapper;
    /**
     * 新增设备种类
     */
    @PostMapping
    public Result<?> save(@RequestBody TypeOfEquipment typeOfEquipment){
        typeOfEquipmentMapper.insert(typeOfEquipment);
        return Result.success();
    }
    /**
     * 更新设备种类
     */
    @PutMapping
    public Result<?> update(@RequestBody TypeOfEquipment typeOfEquipment){
        typeOfEquipmentMapper.updateById(typeOfEquipment);
        return Result.success();
    }
    /**
     * 删除设备种类
     * 注意判断是否被引用
     */
    @DeleteMapping("/{equipmentTypeId}")
    public Result<?> delete(@PathVariable int equipmentTypeId) {
        String result = typeOfEquipmentMapper.selectById(equipmentTypeId).getEquipmentType();
        List<Equipment> equipment = equipmentMapper.selectList(Wrappers.<Equipment>lambdaQuery().eq(Equipment::getEquipmentType,result));
        if(!equipment.isEmpty()) {
            return Result.error("-1", "已被引用，无法删除");
        }else {
            typeOfEquipmentMapper.deleteById(equipmentTypeId);
            return Result.success();
        }
    }
    /**
     * 模糊查询所有设备种类
     */
    @GetMapping
    public Result<?> findAll(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<TypeOfEquipment> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(TypeOfEquipment::getEquipmentType, search);
        }
        Page<TypeOfEquipment> typeOfEquipmentPage = typeOfEquipmentMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(typeOfEquipmentPage);
    }
}
