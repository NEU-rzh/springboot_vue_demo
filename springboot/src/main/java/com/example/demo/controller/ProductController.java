package com.example.demo.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
/**
 * 对一个产品表的控制层的业务逻辑实现方法
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    ProductMapper productMapper;
    /**
     * 对产品新增的方法
     */
    @PostMapping
    public Result<?> save(@RequestBody Product product){
        productMapper.insert(product);
        return Result.success();
    }
    /**
     * 对产品更新的方法
     */
    @PutMapping
    public Result<?> update(@RequestBody Product product){
        productMapper.updateById(product);
        return Result.success();
    }
    /**
     * 对产品删除的方法
     */
    @DeleteMapping("/{productId}")
    public Result<?> delete(@PathVariable int productId) {
        productMapper.deleteById(productId);
        return Result.success();
    }
    /**
     * 对产品模糊查询的方法
     */
    @GetMapping
    public Result<?> findAll(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Product> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Product::getProductName, search);
        }
        Page<Product> productPage = productMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(productPage);
    }
}
