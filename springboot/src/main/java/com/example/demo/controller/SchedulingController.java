package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Scheduling;
import com.example.demo.mapper.ProcedureMapper;
import com.example.demo.mapper.SchedulingMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
/**
 * 对排产明细表的控制层的业务逻辑实现方法
 */
@RestController
@RequestMapping("/scheduling")
public class SchedulingController {
    @Resource
    SchedulingMapper schedulingMapper;

    @Resource
    ProcedureMapper procedureMapper;
    /**
     * 新增排产设备的方法
     * 注意判断开始时间结束时间先后
     */
    @PostMapping
    public Result<?> save(@RequestBody Scheduling scheduling){
        if(scheduling.getSTime().compareTo(scheduling.getFTime())>=0)
        {
            return Result.error("-1","开始时间大于结束时间，请重新选择");
        }else {
            scheduling.setOdId(4);
            schedulingMapper.insert(scheduling);
            return Result.success();
        }
    }
    /**
     * 更新排产明细表
     */
    @PutMapping
    public Result<?> update(@RequestBody Scheduling scheduling){
        schedulingMapper.updateById(scheduling);
        return Result.success();
    }
    /**
     * 调用排场的存储过程
     */
    @PutMapping("/call")
    public Result<?> call(){
        procedureMapper.sd_order();
        return Result.success();
    }
    /**
     * 产出排产信息
     */
    @DeleteMapping("/{odId}/{emId}")
    public Result<?> delete(@PathVariable int odId,
                            @PathVariable int emId) {
        LambdaQueryWrapper<Scheduling> wrapper =Wrappers.<Scheduling>lambdaQuery().eq(Scheduling::getOdId,odId).eq(Scheduling::getEmId,emId);
        schedulingMapper.delete(wrapper);
        return Result.success();
    }
    /**
     * 对该订单的排产信息查询
     */
    @GetMapping
    public Result<?> findAll(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "") int oId){
        LambdaQueryWrapper<Scheduling> wrapper = Wrappers.<Scheduling>lambdaQuery().eq(Scheduling::getOdId,oId);
        Page<Scheduling> schedulingPage = schedulingMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(schedulingPage);
    }
}
