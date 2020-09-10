package com.salari.accounting.model.dto;

import com.salari.accounting.configuration.ApplicationProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaDTO {

    private String message;

    public static MetaDTO getInstance() {
        return MetaDTO.builder()
                .message(ApplicationProperties.getProperty("application.message.success"))
                .build();
    }
}
