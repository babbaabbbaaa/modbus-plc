package com.demo;

import com.demo.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

@Component
public class LoginFilter implements Filter {

    private static final Pattern PATTERN = Pattern.compile(".*/secure/.*");
    private static final Pattern LOGIN_PATTERN = Pattern.compile(".*/secure/login.*");

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String uri = ((HttpServletRequest) request).getRequestURI();
        HttpSession session = ((HttpServletRequest) request).getSession();
        if (!LOGIN_PATTERN.matcher(uri).matches() && PATTERN.matcher(uri).matches()) {
            session.setMaxInactiveInterval(1800);
            if (!StringUtils.hasText((String) session.getAttribute("username"))) {
                ((HttpServletResponse) response).setStatus(HttpStatus.FORBIDDEN.value());
                try (OutputStream out = response.getOutputStream();
                     PrintStream ps = new PrintStream(out)) {
                    ps.println(new ObjectMapper().writeValueAsString(Response.forbidden()));
                }
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
