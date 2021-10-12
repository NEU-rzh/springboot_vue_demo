package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.VO.FactoryUserVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * 对工厂用户的dao层封装接口
 */
public interface FactoryUserMapper extends BaseMapper<FactoryUserVO> {
    /**
     * 查询联合信息
     */
    @Select("SELECT ft.*,ut.tel,ut.usercode,ut.uname FROM factory ft,user_tab ut WHERE ut.user_id=ft.person_in_charge_id AND ft.factory_name LIKE #{search}")
    List<FactoryUserVO> getFactoryUser(Page page,@Param("search")String search);
}
