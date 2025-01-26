package com.rk.temporal.workflows;

import com.rk.temporal.model.request.OrderWorkflowInput;
import com.rk.temporal.model.response.OrderWorkflowResponse;
import io.temporal.workflow.*;

@WorkflowInterface
public interface OrderWorkflowV2 {

    @WorkflowMethod
    void startOrderWorkflow(OrderWorkflowInput input);

    @SignalMethod
    void complete(OrderWorkflowInput input);

    @UpdateMethod
    OrderWorkflowResponse placeOrder(OrderWorkflowInput input);

}
