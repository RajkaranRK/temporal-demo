package com.rk.temporal.workflows;

import com.rk.temporal.activity.OrderActivity;
import com.rk.temporal.config.AppProperties;
import com.rk.temporal.workerconfig.WorkerConfigurator;
import com.rk.temporal.workflows.impl.OrderWorkflowImpl;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderWorkflowConfiguration implements WorkerConfigurator {

    private AppProperties.WorkflowProperties workflowProperties;

    private OrderActivity orderActivity;

    @Override
    public void configureWorker(WorkerFactory workerFactory){
        Worker worker = workerFactory.newWorker(workflowProperties.getOrderQueueNameV2());
        worker.registerWorkflowImplementationTypes(OrderWorkflowImpl.class); //i can register multiple flow to a same worker
        worker.registerActivitiesImplementations(orderActivity);// it cal also accept the multiple activity for a single worker
    }
}