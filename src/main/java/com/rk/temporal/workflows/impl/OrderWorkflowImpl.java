package com.rk.temporal.workflows.impl;

import com.rk.temporal.activity.OrderActivity;
import com.rk.temporal.workflows.OrderWorkflow;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class OrderWorkflowImpl implements OrderWorkflow {

    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100)).setBackoffCoefficient(2).setMaximumAttempts(50000).build();
    private final ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(retryoptions).build();

    private final OrderActivity orderActivity = Workflow.newActivityStub(OrderActivity.class, options);

    private boolean isOrderConfirmed = false;

    private boolean isOrderPickedUp = false;

    private boolean isOrderDelivered = false;


    @Override
    public void startOrderWorkflow() {
        orderActivity.placeOrder();

        log.info("Waiting for restaurant to confirm your order");
        Workflow.await(()->isOrderConfirmed);

        log.info("Waiting for delivery executive to pick your order");
        Workflow.await(()->isOrderPickedUp);

        log.info("Order is on the way to deliver");
        Workflow.await(() -> isOrderDelivered);

    }

    @Override
    public void signalOrderAccepted() {
        orderActivity.setOrderAccepted();
        isOrderConfirmed = true;
    }

    @Override
    public void signalOrderPickup() {
        orderActivity.setOrderPickedUp();
        isOrderPickedUp = true;
    }

    @Override
    public void signalOrderDelivered() {
        orderActivity.setOrderDelivered();
        isOrderDelivered = true;
    }
}
