package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /**
     * 根据订单id删除订单明细数据
     * @param orderId
     */
    @Delete("delete from order_detail where order_id = #{orderId}")
    void delete(Long orderId);


    /**
     * 批量插入订单明细数据
     * @param orderDetails
     */
    void insertBatch(List<OrderDetail> orderDetails);

    /**
     * 根据订单id查询订单明细
     * @param orderId
     * @return
     */
    List<OrderDetail> getByOrderId(Long orderId);
}
