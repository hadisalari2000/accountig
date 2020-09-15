package com.salari.accounting.exception;

import com.google.gson.Gson;
import com.salari.accounting.configuration.ApplicationContextHolder;
import com.salari.accounting.configuration.ApplicationProperties;
import com.salari.accounting.model.dto.BaseDTO;
import com.salari.accounting.model.dto.MetaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint extends ApplicationContextHolder implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write(
                new Gson().toJson(BaseDTO.builder()
                        .metaDTO(MetaDTO.builder().message(ApplicationProperties.getProperty("accessDenied.text")).build())
                        .data("")
                        .build()
                )
        );
    }
}