package cn.lhzs.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by ZHX on 2017/5/15.
 */
@Component("scheduleTask")
public class ScheduleTask {
    @Scheduled(cron = "0 * 16 * * ?")
    public void cleanTimeoutProduct(){
        System.out.println("aaaaaaaaaaaaaa");
    }
}
