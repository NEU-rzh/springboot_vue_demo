package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.VO.OrderReceiverVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
/**
 * 对订单联系人信息的dao层封装接口
 */
public interface OrderReceiverMapper extends BaseMapper<OrderReceiverVO> {
    /**
     * 查询订单状态不为已保存的联合信息
     */
    @Select("SELECT ot.*,rt.receiver,rt.receiver_tel,rt.receiver_address FROM receiver_tab rt,order_tab ot WHERE ot.receiver_id=rt.receiver_id AND ot.product_name LIKE #{search} AND ot.order_state<>'已保存'")
    List<OrderReceiverVO> getOrderReceiver(Page page, @Param("search")String search);
    /**
     * 查询订单状态为已发布的联合信息
     */
    @Select("SELECT ot.*,rt.receiver,rt.receiver_tel,rt.receiver_address FROM receiver_tab rt,order_tab ot WHERE ot.receiver_id=rt.receiver_id AND ot.product_name LIKE #{search} AND ot.order_state='发布'")
    List<OrderReceiverVO> getorb(Page page,@Param("search")String search);
    /**
     * 查询订单状态不为已保存和发布的联合信息
     */
    @Select("SELECT ot.*,rt.receiver,rt.receiver_tel,rt.receiver_address,bid.factory_id FROM receiver_tab rt,order_tab ot,bid WHERE bid.factory_id=#{facId} AND ot.order_id=bid.order_id AND ot.receiver_id=rt.receiver_id AND ot.product_name LIKE #{search} AND ot.order_state<>'发布' AND ot.order_state<>'已保存' AND bid.bid_status=1")
    List<OrderReceiverVO> getors(Page page,@Param("search")String search,@Param("facId")int facId);
    /**
     * 查询订单为该用户的联合信息
     */
    @Select("SELECT ot.*,rt.receiver,rt.receiver_tel,rt.receiver_address FROM receiver_tab rt,order_tab ot WHERE ot.receiver_id=rt.receiver_id AND ot.product_name LIKE #{search} AND rt.receiver=#{uName}")
    List<OrderReceiverVO> getort(Page page,@Param("search")String search,@Param("uName")String uName);
}
