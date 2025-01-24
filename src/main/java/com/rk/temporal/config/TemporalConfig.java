package com.rk.temporal.config;


import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.WorkerFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class TemporalConfig {

    private AppProperties.TemporalProperties temporalProperties;

    @Bean
    public WorkflowServiceStubs workflowServiceStubs(){
        return WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions.newBuilder().setTarget(temporalProperties.getAddress()).build());
    }

    @Bean
    public WorkflowClient workflowClient(WorkflowServiceStubs workflowServiceStubs){
        return WorkflowClient.newInstance(workflowServiceStubs, WorkflowClientOptions.newBuilder().setNamespace(temporalProperties.getNamespace()).build());
    }

    @Bean
    public WorkerFactory workerFactory(WorkflowClient workflowClient){
        return WorkerFactory.newInstance(workflowClient);
    }

}
