package com.rk.temporal.service;

import com.rk.temporal.config.AppProperties;
import com.rk.temporal.model.request.OrderWorkflowInput;
import com.rk.temporal.workflows.OrderWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {

    private final WorkflowServiceStubs workflowServiceStubs;

    private final WorkflowClient workflowClient;

    private final AppProperties.WorkflowProperties workflowProperties;

    public void placeOrder(String workflowId) {
        OrderWorkflow workflow = createWorkFlowConnection(workflowId);
        WorkflowClient.start(workflow::startOrderWorkflow);
    }

    public void makeOrderAccepted(String workflowId) {
        OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class, "Order_" + workflowId);
        workflow.signalOrderAccepted();
    }

    public void makeOrderPickedUp(String workflowId) {
        OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class, "Order_" + workflowId);
        workflow.signalOrderPickup();
    }

    public void makeOrderDelivered(String workflowId) {
        OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class, "Order_" + workflowId);
        workflow.signalOrderDelivered();
    }

    public OrderWorkflow createWorkFlowConnection(String workflowId) {
        String orderId = "Order_"+workflowId;
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(workflowProperties.getOrderQueueName())
                .setWorkflowId(orderId).build();
        return workflowClient.newWorkflowStub(OrderWorkflow.class, options);
    }
}
