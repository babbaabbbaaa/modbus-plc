package com.demo.security;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.model.Response;
import com.demo.utility.JsonUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try (OutputStream out = response.getOutputStream();
             PrintStream ps = new PrintStream(out)) {
            request.getSession().setMaxInactiveInterval(15);
            response.setHeader(HttpHeaders.CONTENT_TYPE, new MediaType("application", "json", StandardCharsets.UTF_8).toString());
            ps.println(JsonUtil.writeAsString(Response.success(
                    new UserModel(authentication.getName(),
                            authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
            )));
        }
    }
}
