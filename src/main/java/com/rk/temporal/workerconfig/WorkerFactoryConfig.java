package com.rk.temporal.workerconfig;

import io.temporal.worker.WorkerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class WorkerFactoryConfig {

    /**
     *
     * @param workerFactory
     * @param workerConfigurators
     */
    public WorkerFactoryConfig(WorkerFactory workerFactory, List<WorkerConfigurator> workerConfigurators){
        workerConfigurators.forEach(workerConfigurator -> workerConfigurator.configureWorker(workerFactory));
        workerFactory.start();
    }

}
