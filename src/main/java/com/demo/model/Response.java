package com.demo.model;

public record Response<T>(
        int code,
        String msg,
        String message,
        T data) {

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
