package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.VO.FactoryUserVO;
import com.example.demo.entity.Factory;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * 对工厂的dao层封装接口
 */
public interface FactoryMapper extends BaseMapper<Factory> {

}
