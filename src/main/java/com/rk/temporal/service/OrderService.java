package com.rk.temporal.service;

import com.rk.temporal.config.AppProperties;
import com.rk.temporal.model.request.OrderStatusRequest;
import com.rk.temporal.model.request.OrderWorkflowInput;
import com.rk.temporal.model.response.OrderWorkflowResponse;
import com.rk.temporal.workflows.OrderWorkflow;
import com.rk.temporal.workflows.OrderWorkflowV2;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Function;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final WorkflowClient workflowClient;

    private final AppProperties.WorkflowProperties workflowProperties;

    public OrderWorkflowResponse placeOrder(OrderWorkflowInput order) {
        OrderWorkflow workflow = createWorkFlowConnection(order);
        WorkflowClient.start(workflow::startOrderWorkflow,order);
        return null;
    }


    public OrderWorkflowResponse orderStatus(String orderId) {
        OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class, orderId);
        return workflow.checkOrder(orderId);
    }


    public OrderWorkflow createWorkFlowConnection(OrderWorkflowInput order) {
        String orderId = "Order_"+ UUID.randomUUID();
        order.setOrderId(orderId);
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(workflowProperties.getOrderQueueNameV2())
                .setWorkflowId(orderId).build();
        return workflowClient.newWorkflowStub(OrderWorkflow.class, options);
    }

    public OrderWorkflowV2 createWorkFlowConnectionV2(OrderWorkflowInput order) {
        String orderId = "Order_"+ UUID.randomUUID();
        order.setOrderId(orderId);
        WorkflowOptions options = WorkflowOptions.newBuilder().setTaskQueue(workflowProperties.getOrderQueueName())
                .setWorkflowId(orderId).build();
        return workflowClient.newWorkflowStub(OrderWorkflowV2.class, options);
    }

    public OrderWorkflowResponse updateOrder(OrderStatusRequest orderStatusRequest){
        OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class,orderStatusRequest.getOrderId());
        OrderWorkflowResponse existingOrder = workflow.checkOrder(orderStatusRequest.getOrderId());
        OrderWorkflowInput input = OrderWorkflowInput.builder().orderState(orderStatusRequest.getOrderState())
                .orderId(orderStatusRequest.getOrderId())
                .id(existingOrder.getId())
                .price(existingOrder.getPrice())
                .mobileNumber(existingOrder.getMobileNumber())
                .address(existingOrder.getAddress())
                .userId(existingOrder.getUserId())
                .build();
        switch (orderStatusRequest.getOrderState()){
            case PICKUP -> workflow.signalOrderPickup(input);
            case ACCEPTED -> workflow.signalOrderAccepted(input);
            case DELIVERED -> workflow.signalOrderDelivered(input);
            case INITIATED -> Function.identity();
            default -> throw new RuntimeException("Order state is  not registered");
        }
        return null;
    }



    public OrderWorkflowResponse createAndGet(OrderWorkflowInput order) {
        OrderWorkflowV2 workflow = createWorkFlowConnectionV2(order);
        WorkflowClient.start(workflow::startOrderWorkflow,order);
        return workflow.placeOrder(order);
    }


    public void markComplete(OrderWorkflowInput request){
        OrderWorkflowV2 workflow = workflowClient.newWorkflowStub(OrderWorkflowV2.class, request.getOrderId());
        workflow.complete(request);
    }


}
