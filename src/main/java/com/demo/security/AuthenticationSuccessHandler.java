package com.demo.security;

import com.demo.model.Response;
import com.demo.utility.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.stream.Collectors;

@Slf4j
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try (OutputStream out = response.getOutputStream();
             PrintStream ps = new PrintStream(out)) {
            request.getSession().setMaxInactiveInterval(15);
            ps.println(JsonUtil.writeAsString(Response.success(
                    new UserModel(authentication.getName(),
                            authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
            )));
        }
    }
}
