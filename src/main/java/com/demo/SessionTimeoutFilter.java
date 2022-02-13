package com.demo;

import com.demo.model.Response;
import com.demo.utility.JsonUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class SessionTimeoutFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (System.currentTimeMillis() - session.getCreationTime() > 1800000L) {
            session.invalidate();
            try (OutputStream out = response.getOutputStream();
                 PrintStream ps = new PrintStream(out)) {
                ps.println(JsonUtil.writeAsString(Response.notAuthenticated()));
            }
            return;
        }
        filterChain.doFilter(request, response);
    }
}
