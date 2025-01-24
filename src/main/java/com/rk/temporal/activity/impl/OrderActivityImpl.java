package com.rk.temporal.activity.impl;

import com.rk.temporal.activity.OrderActivity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @use : Activity is a class which hold the business logic
 */
@Service
@Slf4j
public class OrderActivityImpl implements OrderActivity {

    @Override
    public void placeOrder() {
        log.info("Order has been placed successfully");
    }

    @Override
    public void setOrderAccepted() {
        log.info("Restaurant has accepted your Order");
    }

    @Override
    public void setOrderPickedUp() {
        log.info("Order has been pickup by delivery executive");
    }

    @Override
    public void setOrderDelivered() {
        log.info("Order has been delivered successfully");
    }
}
