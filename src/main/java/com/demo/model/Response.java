package com.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {


    private int code;
    private String msg;
    private String message;
    private T data;

    public static <T> Response<T> success(T data) {
        return new Response<>(0, "", "", data);
    }

    public static <T> Response<T> success() {
        return new Response<>(0, "OK", "OK", null);
    }

    public static <T> Response<T> fail(String message, T data) {
        return new Response<>(500, message, message, data);
    }

    public static <T> Response<T> fail(String message) {
        return new Response<>(500, message, message, null);
    }

    public static <T> Response<T> forbidden() {
        return new Response<>(403, "Forbidden", "Forbidden", null);
    }

    public static <T> Response<T> notAuthenticated() {
        return new Response<>(401, "Not Authenticated", "Not Authenticated", null);
    }
}
