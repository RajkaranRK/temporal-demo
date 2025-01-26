package com.rk.temporal.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.Http;
import com.rk.temporal.config.AppProperties;
import com.rk.temporal.model.request.OrderWorkflowInput;
import com.rk.temporal.model.response.OrderWorkflowResponse;
import com.rk.temporal.pojo.Response;
import com.rk.temporal.utils.AppRestClientUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderHelperService {

    private final AppRestClientUtils restClientUtils;

    private final ObjectMapper objectMapper;

    private final AppProperties.OMSProperties omsProperties;

    public Response<OrderWorkflowResponse> createOrder(OrderWorkflowInput input) throws Exception {
        HttpHeaders requestHeader = populateHttpHeaders();
        String response = restClientUtils.callDownstream(HttpMethod.POST,omsProperties.getOrderBaseUrl(),requestHeader,input);
        return objectMapper.readValue(response, new TypeReference<Response<OrderWorkflowResponse>>() {});
    }


    public Response<OrderWorkflowResponse> updateOrder(OrderWorkflowInput input) throws  Exception {
        HttpHeaders requestHeader = populateHttpHeaders();
        String url = omsProperties.getOrderBaseUrl() + String.format("/%s",input.getOrderId());
        String response = restClientUtils.callDownstream(HttpMethod.PUT,url,requestHeader,input);
        return objectMapper.readValue(response, new TypeReference<Response<OrderWorkflowResponse>>() {});
    }


    private HttpHeaders populateHttpHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","application/json");
        return httpHeaders;
    }

}
