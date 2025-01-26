package com.rk.temporal.workflows.impl;

import com.rk.temporal.activity.OrderActivity;
import com.rk.temporal.model.request.OrderWorkflowInput;
import com.rk.temporal.model.response.OrderWorkflowResponse;
import com.rk.temporal.workflows.OrderWorkflow;
import com.rk.temporal.workflows.OrderWorkflowV2;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class OrderWorkflowImplV2 implements OrderWorkflowV2 {

    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(5))
            .setMaximumInterval(Duration.ofSeconds(100)).setBackoffCoefficient(2).setMaximumAttempts(5).build();
    private final ActivityOptions options = ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(30))
            .setRetryOptions(retryoptions).build();

    private final OrderActivity orderActivity = Workflow.newActivityStub(OrderActivity.class, options);

    private boolean isWorkflowCompleted = false;


    @Override
    public void startOrderWorkflow(OrderWorkflowInput input) {
        log.info("Waiting for workflow to complete");
        Workflow.await(()->isWorkflowCompleted);
        log.info("Workflow completed");

    }

    @Override
    public void complete(OrderWorkflowInput input) {
        log.info("making workflow complete");
        this.isWorkflowCompleted = true;
    }

    @Override
    public OrderWorkflowResponse placeOrder(OrderWorkflowInput input) {
        OrderWorkflowResponse response = orderActivity.placeOrder(input);
        return response;
    }
}
