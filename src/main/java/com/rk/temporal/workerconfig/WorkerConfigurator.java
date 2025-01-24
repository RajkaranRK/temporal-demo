package com.rk.temporal.workerconfig;

import io.temporal.worker.WorkerFactory;

public interface WorkerConfigurator {

    void configureWorker(WorkerFactory workerFactory);
}
