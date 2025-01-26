package com.rk.temporal.activity.impl;

import com.rk.temporal.activity.OrderActivity;
import com.rk.temporal.helper.OrderHelperService;
import com.rk.temporal.model.request.OrderWorkflowInput;
import com.rk.temporal.model.response.OrderWorkflowResponse;
import com.rk.temporal.pojo.Response;
import io.temporal.activity.Activity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OrderActivityImpl implements OrderActivity {

    private final OrderHelperService orderHelperService;

    @Override
    public OrderWorkflowResponse placeOrder(OrderWorkflowInput orderRequest) {
        try{
            Response<OrderWorkflowResponse> response = orderHelperService.createOrder(orderRequest);
            log.info("Order has been placed successfully with OrderId : {}",orderRequest.getOrderId());
            if(response.isSuccess()){
                return response.getData();
            }
            throw Activity.wrap(new RuntimeException("Unable to create the order"));
        }catch (Exception ex){
            throw Activity.wrap(ex);
        }
    }

    @Override
    public OrderWorkflowResponse updateOrder(OrderWorkflowInput orderRequest) {
        try{
            Response<OrderWorkflowResponse> response = orderHelperService.updateOrder(orderRequest);
            if(response.isSuccess()){
                return response.getData();
            }
            throw Activity.wrap(new RuntimeException("Unable to update the order"));
        }catch (Exception ex){
            throw Activity.wrap(ex);
        }
    }

}
