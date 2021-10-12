package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.VO.FactoryUserEquipmentVO;
import com.example.demo.VO.FactoryUserVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * 对工厂用户设备的dao层封装接口
 */
public interface FactoryUserEquipmentMapper extends BaseMapper<FactoryUserEquipmentVO> {
    /**
     * 根据用户ID查询联合信息
     */
    @Select("SELECT em.*,ut.user_id,ft.person_in_charge_id FROM factory ft,user_tab ut,equipment em WHERE ut.user_id=ft.person_in_charge_id AND ft.factory_id=em.factory_id AND ut.user_id=#{uId} AND em.equipment_name LIKE #{search}")
    List<FactoryUserEquipmentVO> getFactoryUserEquipment(Page page, @Param("search")String search,@Param("uId")int uId);
    /**
     * 根据用户ID查询设备状态为闲置中的来联合信息
     */
    @Select("SELECT em.*,ut.user_id,ft.person_in_charge_id FROM factory ft,user_tab ut,equipment em WHERE ut.user_id=ft.person_in_charge_id AND ft.factory_id=em.factory_id AND ut.user_id=#{uId} AND em.equipment_state='闲置中' ")
    List<FactoryUserEquipmentVO> getFactory(Page page,@Param("uId")int uId);
}
