package com.salari.accounting.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Component
public class TokenProvider {

    @Autowired
    private UsersDetails usersDetails;

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = usersDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        String temp = new String(Base64.getDecoder().decode(token));
        return temp.substring(0, temp.indexOf("|"));
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Basic ")) {
            return bearerToken.substring(6);
        }
        return null;
    }


}
