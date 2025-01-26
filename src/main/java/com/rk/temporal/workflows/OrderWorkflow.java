package com.rk.temporal.workflows;

import com.rk.temporal.model.request.OrderWorkflowInput;
import com.rk.temporal.model.response.OrderWorkflowResponse;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface OrderWorkflow {

    @WorkflowMethod
    public void startOrderWorkflow(OrderWorkflowInput input);

    @SignalMethod
    public void signalOrderAccepted(OrderWorkflowInput input);

    @SignalMethod
    void signalOrderPickup(OrderWorkflowInput input);

    @SignalMethod
    void signalOrderDelivered(OrderWorkflowInput input);

    @QueryMethod
    OrderWorkflowResponse checkOrder(String orderId);

}
