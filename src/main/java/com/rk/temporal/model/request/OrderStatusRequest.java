package com.rk.temporal.model.request;

import com.rk.temporal.enums.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusRequest {

    private String orderId;

    private OrderState orderState;

}
