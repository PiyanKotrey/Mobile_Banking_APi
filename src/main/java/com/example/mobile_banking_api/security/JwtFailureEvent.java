package com.example.mobile_banking_api.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtFailureEvent {
    private final HttpServletResponse httpServletResponse;
    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent badCredentialsEvent){
        if (badCredentialsEvent.getAuthentication() instanceof BearerTokenAuthentication){
            System.out.println(badCredentialsEvent.getException().getMessage());

        }
    }
}
