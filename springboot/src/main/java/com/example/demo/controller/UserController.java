package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 对用户表的控制层的业务逻辑实现方法
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserMapper userMapper;
    /**
     *用户登陆的判断及跳转界面的方法
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsercode,user.getUsercode()).eq(User::getPword,user.getPword()));
        if(res == null){
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success(res);
    }
    /**
     *注册方法
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
//        Timestamp d = new Timestamp(System.currentTimeMillis());
//        user.setCreateTime(d);
//        user.setUpdateTime(d);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setCreateTime(df.format(System.currentTimeMillis()));
        user.setUpdateTime(df.format(System.currentTimeMillis()));
        userMapper.insert(user);
        return Result.success(user);
    }
    /**
     * 新增用户
     */
    @PostMapping
    public Result<?> save(@RequestBody User user) {
//        Timestamp d = new Timestamp(System.currentTimeMillis());
//        user.setCreateTime(d);
//        user.setUpdateTime(d);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setCreateTime(df.format(System.currentTimeMillis()));
        user.setUpdateTime(df.format(System.currentTimeMillis()));
        userMapper.insert(user);
        return Result.success(user);
    }
    /**
     * 更新用户方法
     */
    @PutMapping
    public Result<?> update(@RequestBody User user) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setUpdateTime(df.format(System.currentTimeMillis()));
        userMapper.updateById(user);
        return Result.success();
    }
    /**
     * 删除用户方法
     */
    @DeleteMapping("/{userId}")
    public Result<?> delete(@PathVariable int userId) {
        userMapper.deleteById(userId);
        return Result.success();
    }
    /**
     * 模糊查询用户方法
     */
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if (StrUtil.isNotBlank(search)){
            wrapper.like(User::getUname,search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(userPage);
    }
}
