package com.exigency.exigencycare.Model;

import java.io.Serializable;

/**
 * Created by sez1 on 14/10/15.
 */
public class HealthProviderModel implements Serializable {

    private String status;
    private int statusCode;
    public ResponseData responseData;

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}

