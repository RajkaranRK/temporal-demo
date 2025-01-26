package com.rk.temporal.activity;

import com.rk.temporal.model.request.OrderWorkflowInput;
import com.rk.temporal.model.response.OrderWorkflowResponse;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface OrderActivity {

    @ActivityMethod
    OrderWorkflowResponse placeOrder(OrderWorkflowInput input);

    @ActivityMethod
    OrderWorkflowResponse updateOrder(OrderWorkflowInput input);

}
