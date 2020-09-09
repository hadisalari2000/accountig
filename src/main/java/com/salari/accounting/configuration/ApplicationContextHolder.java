package com.salari.accounting.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder {

    protected ApplicationProperties applicationProperties;

    @Autowired
    public void context(ApplicationContext context) {
        this.applicationProperties = context.getBean(ApplicationProperties.class);
    }
}
