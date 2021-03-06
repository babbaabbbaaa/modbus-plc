package com.demo.security;

import com.demo.model.Response;
import com.demo.utility.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Http401AuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        try (OutputStream out = response.getOutputStream();
             PrintStream ps = new PrintStream(out)) {
            ps.println(JsonUtil.writeAsString(Response.notAuthenticated()));
        }
    }
}
