package com.rk.temporal.workflows;

import com.rk.temporal.activity.OrderActivity;
import com.rk.temporal.config.AppProperties;
import com.rk.temporal.workerconfig.WorkerConfigurator;
import com.rk.temporal.workflows.impl.OrderWorkflowImplV2;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderWorkflowConfigurationV2 implements WorkerConfigurator {

    private AppProperties.WorkflowProperties workflowProperties;

    private OrderActivity orderActivity;

    @Override
    public void configureWorker(WorkerFactory workerFactory){
        Worker worker = workerFactory.newWorker(workflowProperties.getOrderQueueName());
        worker.registerWorkflowImplementationTypes(OrderWorkflowImplV2.class); //i can register multiple flow to a same worker
        worker.registerActivitiesImplementations(orderActivity);// it cal also accept the multiple activity for a single worker
    }
}