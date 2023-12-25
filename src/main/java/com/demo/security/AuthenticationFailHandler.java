package com.demo.security;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.demo.model.Response;
import com.demo.utility.JsonUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class AuthenticationFailHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        try (OutputStream out = response.getOutputStream();
             PrintStream ps = new PrintStream(out)) {
            response.setHeader(HttpHeaders.CONTENT_TYPE, new MediaType("application", "json", StandardCharsets.UTF_8).toString());
            ps.println(JsonUtil.writeAsString(Response.notAuthenticated()));
        }
    }
}
