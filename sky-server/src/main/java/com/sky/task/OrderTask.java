package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper OrderMapper;

    /**
     * 处理超时订单
     */
    @Scheduled(cron = "0 * * * * ?") //每分钟触发一次
    public void processTimeoutOrder(){
        log.info("处理超时订单: {}", LocalDateTime.now());

        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        //select * from order where status = ? and order_time < 当前时间-15分钟
        List<Orders> ordersList = OrderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, time);

        if(ordersList != null && ordersList.size() > 0){
            for(Orders orders : ordersList){
                //更新订单状态为已取消
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("订单超时，系统自动取消");
                orders.setCancelTime(LocalDateTime.now());

                //更新订单表
                OrderMapper.update(orders);
            }
        }

    }


    /**
     * 处理处于派送中的订单
     */
    @Scheduled(cron = "0 0 1 * * ?") //每天凌晨1点触发
    public void processDeliveryOrder(){

        log.info("处理处于派送中的订单: {}", LocalDateTime.now());

        LocalDateTime time = LocalDateTime.now().plusMinutes(-60);

        //查询处于派送中的订单
        List<Orders> ordersList = OrderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, time);

        if(ordersList != null && ordersList.size() > 0){
            for(Orders orders : ordersList){
                //更新订单状态为已取消
                orders.setStatus(Orders.COMPLETED);

                //更新订单表
                OrderMapper.update(orders);
            }
        }

    }




}
