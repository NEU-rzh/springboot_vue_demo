package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.VO.EquipmentProductCapVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * 对设备产品产能联合的dao层封装接口
 */
public interface EquipmentProductCapMapper extends BaseMapper<EquipmentProductCapVO> {
    /**
     * 根据工厂ID查询所有联合信息
     */
    @Select("SELECT cap.*,pd.*,em.* FROM equipment em,product pd,capacity_info cap WHERE cap.equipment_id = em.equipment_id AND cap.product_id = pd.product_id AND em.factory_id=#{fId}")
    List<EquipmentProductCapVO> findAll(Page<EquipmentProductCapVO> page, @Param("fId")int fId);
}
