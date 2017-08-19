package cn.lhzs.task;

import cn.lhzs.service.intf.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduleTask {
    @Autowired
    public ProductService productService;

    @Scheduled(cron = "0 0 2 * * ? ")
    public void taskCycle() {
        productService.timerDeleteTask(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
}