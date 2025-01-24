package com.rk.temporal.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface OrderActivity {

    @ActivityMethod
    void placeOrder();

    @ActivityMethod
    void setOrderAccepted();

    @ActivityMethod
    void setOrderPickedUp();

    @ActivityMethod
    void setOrderDelivered();

}
