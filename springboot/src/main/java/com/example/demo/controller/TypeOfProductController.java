package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Equipment;
import com.example.demo.entity.Product;
import com.example.demo.entity.TypeOfProduct;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.TypeOfProductMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
/**
 * 对产品种类表的控制层的业务逻辑实现方法
 */
@RestController
@RequestMapping("/typeofproduct")
public class TypeOfProductController {
    @Resource
    TypeOfProductMapper typeOfProductMapper;

    @Resource
    ProductMapper productMapper;
    /**
     * 新增产品种类
     */
    @PostMapping
    public Result<?> save(@RequestBody TypeOfProduct typeOfProduct){
        typeOfProductMapper.insert(typeOfProduct);
        return Result.success();
    }
    /**
     * 更新产品种类
     */
    @PutMapping
    public Result<?> update(@RequestBody TypeOfProduct typeOfProduct){
        typeOfProductMapper.updateById(typeOfProduct);
        return Result.success();
    }
    /**
     * 删除产品种类
     */
    @DeleteMapping("/{typeOfProductId}")
    public Result<?> delete(@PathVariable int typeOfProductId) {
        String result = typeOfProductMapper.selectById(typeOfProductId).getProductType();
        List<Product> list = productMapper.selectList(Wrappers.<Product>lambdaQuery().eq(Product::getProductType,result));
        if(!list.isEmpty()) {
            return Result.error("-1", "已被引用，无法删除");
        }else {
            typeOfProductMapper.deleteById(typeOfProductId);
            return Result.success();
        }
    }
    /**
     * 模糊查询产品种类
     */
    @GetMapping
    public Result<?> findAll(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<TypeOfProduct> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(TypeOfProduct::getProductType, search);
        }
        Page<TypeOfProduct> typeOfProductPage = typeOfProductMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(typeOfProductPage);
    }
}
