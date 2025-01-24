package com.rk.temporal.controller.v2;

import com.rk.temporal.model.request.OrderStatusRequest;
import com.rk.temporal.model.request.OrderWorkflowInput;
import com.rk.temporal.model.response.OrderWorkflowResponse;
import com.rk.temporal.service.OrderServiceV2;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v2/orders")
@RestController
@AllArgsConstructor
public class OrderControllerV2 {


    private OrderServiceV2 orderService;


    @PostMapping("/initiate")
    public ResponseEntity<Object> createOrder(@RequestBody OrderWorkflowInput request) {
        OrderWorkflowResponse response = orderService.placeOrder(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/update/{orderId}")
    public ResponseEntity<Object> updateOrder(@PathVariable String orderId, @RequestBody OrderStatusRequest orderStatusRequest) {
        orderStatusRequest.setOrderId(orderId);
        OrderWorkflowResponse response = orderService.updateOrder(orderStatusRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
