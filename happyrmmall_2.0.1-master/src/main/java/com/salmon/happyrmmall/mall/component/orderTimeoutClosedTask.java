package com.salmon.happyrmmall.mall.component;

import com.salmon.happyrmmall.mall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @date 2022/4/11 - 14:29
 * Created by Salmon
 */
@Component
@Slf4j
public class orderTimeoutClosedTask {

    @Autowired
    private IOrderService iOrderService;


    @Scheduled(cron = "0 0/10 * ? * ?") //每十分钟扫描一次，未支付则取消订单
    private void closeTimeoutOrder(){
        Integer count = iOrderService.closeTimeoutOrder().getData();
        log.info(new StringBuilder().append("取消订单并更新商品库存,订单取消数量为:").append(count).toString());
    }
}
