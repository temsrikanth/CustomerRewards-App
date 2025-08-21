package com.rewards.model;

public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.status = "SUCCESS";
        resp.message = message == null ? "OK" : message;
        resp.data = data;
        return resp;
    }

    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.status = "ERROR";
        resp.message = message;
        resp.data = null;
        return resp;
    }

    public String getStatus() { 
    	return status; 
    	}
    public void setStatus(String status) { 
    	this.status = status; 
    	}
    public String getMessage() { 
    	return message; 
    	}
    public void setMessage(String message) { 
    	this.message = message; 
    	}
    public T getData() { 
    	return data; 
    	}
    public void setData(T data) { 
    	this.data = data; 
    	}
}
