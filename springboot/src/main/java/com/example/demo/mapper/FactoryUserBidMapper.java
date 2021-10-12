package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.VO.FactoryUserBidVO;
import com.example.demo.VO.FactoryUserEquipmentVO;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * 对工厂用户投标的dao层封装接口
 */
public interface FactoryUserBidMapper extends BaseMapper<FactoryUserBidVO> {
    /**
     * 根据订单号查询所有未中标的联合信息
     */
    @Select("SELECT bid.*,ut.tel,ut.uname,ft.factory_name FROM factory ft,user_tab ut,bid WHERE ut.user_id=ft.person_in_charge_id AND ft.factory_id=bid.factory_id AND bid.order_id=#{oId} AND bid.bid_status=0")
    List<FactoryUserBidVO> getfub(Page page,@Param("oId")int oId);
}
