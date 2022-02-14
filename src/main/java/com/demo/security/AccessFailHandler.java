package com.demo.security;

import com.demo.model.Response;
import com.demo.utility.JsonUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class AccessFailHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            try (OutputStream out = response.getOutputStream();
                 PrintStream ps = new PrintStream(out)) {
                ps.println(JsonUtil.writeAsString(Response.forbidden()));
            }
        } else {
            try (OutputStream out = response.getOutputStream();
                 PrintStream ps = new PrintStream(out)) {
                ps.println(JsonUtil.writeAsString(Response.notAuthenticated()));
            }
        }
    }
}
