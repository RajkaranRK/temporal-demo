package com.rk.temporal.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Response<T> extends BaseRestResponse implements Serializable {


    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -5673300123858880646L;

    /** The data. */
    private T data;

    /**
     * Sets the success response.
     *
     * @param data the data
     * @param message the message
     */
    public void setSuccessResponse(T data, String message) {
        this.setData(data);
        super.setSuccessResponse(message);
    }

    /**
     * Sets the success response.
     *
     * @param data the data
     * @param code the code
     * @param message the message
     */
    public void setSuccessResponse(T data, String code, String message) {
        this.setData(data);
        super.setSuccessResponse(code, message);
    }
}
