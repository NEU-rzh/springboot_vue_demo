package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
/**
 * 对存储过程的dao层封装接口
 */
public interface ProcedureMapper extends BaseMapper {
    @Update("call sd_order()")
    void sd_order();

    @Update("call choose_bid(#{bidId})")
    void choose_bid(@Param("bidId")Integer bidId);
}
