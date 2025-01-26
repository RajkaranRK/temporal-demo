package com.rk.temporal.controller;

import com.rk.temporal.model.request.OrderStatusRequest;
import com.rk.temporal.model.request.OrderWorkflowInput;
import com.rk.temporal.model.response.OrderWorkflowResponse;
import com.rk.temporal.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/orders")
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class OrderController {


    private OrderService orderService;


    @PostMapping("/initiate")
    public ResponseEntity<Object> createOrder(@RequestBody OrderWorkflowInput request) {
        OrderWorkflowResponse response = orderService.placeOrder(request);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    public String updateOrder(@PathVariable String orderId,@RequestBody OrderStatusRequest request) {
        request.setOrderId(orderId);
        orderService.updateOrder(request);
        return request.getOrderState().name();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderWorkflowResponse> orderStatus(@PathVariable("orderId") String orderId){
        OrderWorkflowResponse response = orderService.orderStatus(orderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/initiate-get")
    public ResponseEntity<Object> createOrderAndGet(@RequestBody OrderWorkflowInput request) {
        OrderWorkflowResponse response = orderService.createAndGet(request);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/markComplete")
    public ResponseEntity<Object> markComplete(@RequestBody OrderWorkflowInput request) {
        orderService.markComplete(request);
        return new ResponseEntity<>("COMPLETED",HttpStatus.OK);
    }
}
