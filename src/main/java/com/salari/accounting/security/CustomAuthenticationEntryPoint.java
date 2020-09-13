package com.salari.accounting.security;

import com.google.gson.Gson;
import com.salari.accounting.configuration.ApplicationContextHolder;
import com.salari.accounting.configuration.ApplicationProperties;
import com.salari.accounting.model.dto.BaseDTO;
import com.salari.accounting.model.dto.MetaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint extends ApplicationContextHolder implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().write(
                new Gson().toJson(BaseDTO.builder()
                        .metaDTO(MetaDTO.builder().message(ApplicationProperties.getProperty("accessDenied.text")).build())
                        .data(null)
                        .build())
                );
    }
}
