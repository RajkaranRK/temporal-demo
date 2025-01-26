package com.rk.temporal.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.rk.temporal.constants.AppConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.text.SimpleDateFormat;

@Configuration
public class AppConfiguration {

    /**
     * Gets the rest client.
     *
     * @return the rest client
     */
    @Bean
    @ConditionalOnMissingBean(RestClient.class)
    RestClient getRestClient() {
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory();
        factory.setReadTimeout(20000);
        return RestClient.builder().requestFactory(factory).build();
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();
        objectMapper.setDateFormat(new SimpleDateFormat(AppConstants.DATE_FORMAT));
        return objectMapper;
    }
}
