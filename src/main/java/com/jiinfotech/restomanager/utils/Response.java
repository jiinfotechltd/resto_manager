package com.jiinfotech.restomanager.utils;

public class Response<T> {
    private String message;
    private T data;
    private int status;

    public Response() {
    }

    public Response(String message, T data, int status) {
        this.message = message;
        this.data = data;
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

    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }
}
