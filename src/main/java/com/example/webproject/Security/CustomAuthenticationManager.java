package com.example.webproject.Security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        if(username.equals("foo") && password.equals("foo")){
            return new UsernamePasswordAuthenticationToken("foo", "foo", new ArrayList<>());
        } else {
            throw new BadCredentialsException("Incorrect username or password");
        }

    }

}
