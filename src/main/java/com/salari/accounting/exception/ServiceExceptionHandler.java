package com.salari.accounting.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salari.accounting.configuration.ApplicationContextHolder;
import com.salari.accounting.configuration.ApplicationProperties;
import com.salari.accounting.model.dto.BaseDTO;
import com.salari.accounting.model.dto.MetaDTO;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ControllerAdvice
public class ServiceExceptionHandler extends ApplicationContextHolder {

    // --> ServiceLevelValidation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {

        MetaDTO metaDTO = new MetaDTO();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {

            String errorMessage = ApplicationProperties.getProperty(error.getDefaultMessage());
            if (errorMessage == null)
                errorMessage = ApplicationProperties.getProperty("validationError.text");

            String fieldName = (ApplicationProperties.getProperty(error.getField()) != null)
                    ? ApplicationProperties.getProperty(error.getField())
                    : error.getField();

            metaDTO = MetaDTO.builder()
                    .message(String.format(errorMessage, fieldName))
                    .build();
            break;
        }
        BaseDTO baseDTO = BaseDTO.builder()
                .metaDTO(metaDTO)
                .data(null)
                .build();
        return new ResponseEntity<>(baseDTO, HttpStatus.BAD_REQUEST);
    }

    // --> ServiceLevelValidation
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<BaseDTO> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        MetaDTO metaDTO = new MetaDTO();

        for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
            String errorMessage = ApplicationProperties.getProperty(error.getMessage());
            if (errorMessage == null)
                errorMessage = ApplicationProperties.getProperty("validationError.text");

            String field = ((PathImpl) error.getPropertyPath()).getLeafNode().getName();
            String fieldName = (ApplicationProperties.getProperty(field) != null) ? ApplicationProperties.getProperty(field) : field;

            if (error.getConstraintDescriptor().getAnnotation().annotationType().equals(NotBlank.class))
                errorMessage = ApplicationProperties.getProperty("missingParameter.text");
            if (error.getConstraintDescriptor().getAnnotation().annotationType().equals(Min.class))
                errorMessage = ApplicationProperties.getProperty("invalidMinLength.text");
            if (error.getConstraintDescriptor().getAnnotation().annotationType().equals(Max.class))
                errorMessage = ApplicationProperties.getProperty("invalidMaxLength.text");
            if (error.getConstraintDescriptor().getAnnotation().annotationType().equals(Size.class))
                errorMessage = ApplicationProperties.getProperty("invalidLength.text");

            metaDTO = MetaDTO.builder()
                    .message(String.format(errorMessage, fieldName))
                    .build();
            break;
        }
        BaseDTO baseDTO = BaseDTO.builder()
                .metaDTO(metaDTO)
                .data(null)
                .build();

        return new ResponseEntity<>(baseDTO, HttpStatus.BAD_REQUEST);
    }

    // --> Custom exceptions
    @ExceptionHandler(ServiceException.class)
    public final ResponseEntity<BaseDTO> handleAllExceptions(ServiceException ex, HttpServletRequest request) {
        BaseDTO baseDTO = BaseDTO.builder()
                .metaDTO(MetaDTO.builder().message(ex.getMessage()).build())
                .data(null)
                .build();
        return new ResponseEntity<>(baseDTO, ex.getHttpStatus() == null ? HttpStatus.INTERNAL_SERVER_ERROR : ex.getHttpStatus()
        );
    }

    // --> Handler not found exceptions
    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<BaseDTO> handleAllExceptions(NoHandlerFoundException ex, HttpServletRequest request) {
        BaseDTO baseDTO = BaseDTO.builder()
                .metaDTO(MetaDTO.builder().message(ex.getMessage()).build())
                .data(null)
                .build();
        return new ResponseEntity<>(baseDTO, HttpStatus.NOT_FOUND);
    }


    // --> General exceptions
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<BaseDTO> handleAllExceptions(Exception ex, HttpServletRequest request) throws Exception {
        BaseDTO baseDTO = new ObjectMapper().readValue((((HttpServerErrorException) ex).getResponseBodyAsString()), BaseDTO.class);
        return new ResponseEntity<>(baseDTO, ((HttpServerErrorException) ex).getStatusCode()
        );

    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public final ResponseEntity<BaseDTO> handleMissingParameterException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        Object convertedFieldName = ApplicationProperties.getProperty(ex.getParameterName());
        BaseDTO baseDTO = new BaseDTO(new MetaDTO(
                String.format(
                        ApplicationProperties.getProperty("missingParameter.text"),
                        convertedFieldName == null ? ex.getParameterName() : convertedFieldName.toString()
                )
        ));
        return new ResponseEntity<>(baseDTO, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<BaseDTO> handleNotReadableExceptions(HttpMessageNotReadableException ex, HttpServletRequest request) {
        BaseDTO baseDTO = new BaseDTO(
                new MetaDTO(
                        ApplicationProperties.getProperty("requestNotReadable.text"))
        );
        return new ResponseEntity<>(baseDTO, HttpStatus.BAD_REQUEST);
    }


    // --> Runtime exceptions
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<BaseDTO> handleAllExceptions(RuntimeException ex, HttpServletRequest request) {
        BaseDTO baseDTO = BaseDTO.builder()
                .metaDTO(MetaDTO.builder().message(ApplicationProperties.getProperty("unknownError.text")).build())
                .data(null)
                .build();
        return new ResponseEntity<>(baseDTO, HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<BaseDTO> handleAccessExceptions(AccessDeniedException ex, HttpServletRequest request) {
        BaseDTO baseDTO = BaseDTO.builder()
                .metaDTO(MetaDTO.builder().message(ApplicationProperties.getProperty("accessDenied.text")).build())
                .data(null)
                .build();
        return new ResponseEntity<>(baseDTO, HttpStatus.FORBIDDEN);
    }
}
