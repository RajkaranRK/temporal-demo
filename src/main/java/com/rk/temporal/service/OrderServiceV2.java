package com.rk.temporal.service;

import com.rk.temporal.config.AppProperties;
import com.rk.temporal.model.request.OrderStatusRequest;
import com.rk.temporal.model.request.OrderWorkflowInput;
import com.rk.temporal.model.response.OrderWorkflowResponse;
import com.rk.temporal.workflows.OrderWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceV2 {

    private final WorkflowServiceStubs workflowServiceStubs;

    private final WorkflowClient workflowClient;

    private final AppProperties.WorkflowProperties workflowProperties;

    public OrderWorkflowResponse placeOrder(OrderWorkflowInput order) {
        OrderWorkflow workflow = createWorkFlowConnection(order);
        WorkflowClient.start(workflow::startOrderWorkflow);
        return null;
    }

    public OrderWorkflowResponse updateOrder(OrderStatusRequest orderStatusRequest) {
        OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class, orderStatusRequest.getOrderId());
        workflow.signalOrderAccepted();
        return null;
    }

    public OrderWorkflow createWorkFlowConnection(OrderWorkflowInput order) {
        String orderId = "Order_"+ UUID.randomUUID();
        order.setOrderId(orderId);
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(workflowProperties.getOrderQueueName())
                .setWorkflowId(orderId).build();
        return workflowClient.newWorkflowStub(OrderWorkflow.class, options);
    }
}
