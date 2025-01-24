package com.rk.temporal.model.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderWorkflowResponse {

    private Long id;

    private String orderId;

    private String mobileNumber;

    private String address;

    private Double price;

    private String userId;

    private String orderState;
}
