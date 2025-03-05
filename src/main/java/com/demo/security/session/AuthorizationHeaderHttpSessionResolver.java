package com.demo.security.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.session.web.http.HttpSessionIdResolver;

import java.util.Collections;
import java.util.List;

public class AuthorizationHeaderHttpSessionResolver implements HttpSessionIdResolver {

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        return (token != null) ? Collections.singletonList(token.replaceAll("[B|b]earer\\s*", "")) : Collections.emptyList();
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        response.setHeader(HttpHeaders.AUTHORIZATION, sessionId);
    }

    @Override
    public void expireSession(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(HttpHeaders.AUTHORIZATION, "");
    }

}
