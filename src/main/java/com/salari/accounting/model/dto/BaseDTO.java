package com.salari.accounting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class BaseDTO<T> {

    private MetaDTO metaDTO;
    private T data;

    public BaseDTO(MetaDTO metaDTO) {
        this.metaDTO = metaDTO;
    }

    public BaseDTO(MetaDTO metaDTO, T data) {
        this.metaDTO = metaDTO;
        this.data = data;
    }
}
