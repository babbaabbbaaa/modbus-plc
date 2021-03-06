package com.demo.security;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.model.Response;
import com.demo.utility.JsonUtil;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class AuthenticationFailHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        try (OutputStream out = response.getOutputStream();
             PrintStream ps = new PrintStream(out)) {
            ps.println(JsonUtil.writeAsString(Response.notAuthenticated()));
        }
    }
}
