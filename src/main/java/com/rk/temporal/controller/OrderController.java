package com.rk.temporal.controller;

import com.rk.temporal.model.request.OrderStatusRequest;
import com.rk.temporal.model.request.OrderWorkflowInput;
import com.rk.temporal.model.response.OrderWorkflowResponse;
import com.rk.temporal.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping("/createOrder")
    public String createOrder(@RequestBody String workflowId) {
        orderService.placeOrder(workflowId);
        return "Order Created";
    }

    @PostMapping("/orderAccepted")
    public String orderAccepted(@PathVariable String orderId) {
        orderService.makeOrderAccepted(orderId);
        return "Order Pickup";
    }

    @PostMapping("/orderPickedUp")
    public String orderPickedUp(@RequestParam("id") String id) {
        orderService.makeOrderPickedUp(id);
        return "Order Pickup";
    }

    @PostMapping("/orderDelivered")
    public String orderDelivered(@RequestParam("id") String id) {
        orderService.makeOrderDelivered(id);
        return "Order Delivered";
    }
}
