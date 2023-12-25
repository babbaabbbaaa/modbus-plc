package com.demo.security;

import com.demo.model.Response;
import com.demo.utility.JsonUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try (ServletOutputStream out = response.getOutputStream()) {
            request.getSession().setMaxInactiveInterval(1800);
            response.setHeader(HttpHeaders.CONTENT_TYPE, new MediaType("application", "json", StandardCharsets.UTF_8).toString());
            String result = JsonUtil.writeAsString(Response.success(
                new UserModel(authentication.getName(),
                        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))));
            out.println(URLEncoder.encode(result, StandardCharsets.UTF_8.displayName()));
            out.flush();

        }
    }
}
