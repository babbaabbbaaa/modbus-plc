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
    private String message;
    private T data;

    public static <T> Response<T> success(T data) {
        return new Response<>(0, "", data);
    }

    public static <T> Response<T> success() {
        return new Response<>(0, "OK", null);
    }

    public static <T> Response<T> fail(String message, T data) {
        return new Response<>(500, message, data);
    }

    public static <T> Response<T> fail(String message) {
        return new Response<>(500, message, null);
    }

    public static <T> Response<T> forbidden() {
        return new Response<>(403, "Forbidden", null);
    }
}
