package com.rk.temporal.model.request;

import com.rk.temporal.enums.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderWorkflowInput implements Serializable {

    private Long id;

    private String orderId;

    private String mobileNumber;

    private String address;

    private Double price;

    private String userId;

    private OrderState orderState;


}
