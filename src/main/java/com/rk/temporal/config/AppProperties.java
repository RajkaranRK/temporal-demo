package com.rk.temporal.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Data
public class AppProperties {

    @Data
    @ConfigurationProperties(prefix = "workflow")
    @Component
    public static class WorkflowProperties {

        private String orderQueueName;

    }

    @Data
    @ConfigurationProperties(prefix = "temporal")
    @Component
    public static class TemporalProperties {

        private String address;

        private String namespace;

    }
}
