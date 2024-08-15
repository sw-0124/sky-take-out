package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 自定义定时任务类
 */
@Component
@Slf4j
public class MyTask {

    /**
     * 定时任务 每5秒执行一次
     */
    //@Scheduled(cron = "0/5 * * * * ?")   //指定什么时间点触发
    public void executeTask(){
        log.info("定时任务执行了");
    }

}
