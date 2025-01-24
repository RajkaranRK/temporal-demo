package com.rk.temporal.workflows;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderWorkflow {
    String QUEUE_NAME = "CUSTOMER_ORDER_QUEUE";

    @WorkflowMethod
    public void startOrderWorkflow();

    @SignalMethod
    public void signalOrderAccepted();

    @SignalMethod
    void signalOrderPickup();

    @SignalMethod
    void signalOrderDelivered();

}
