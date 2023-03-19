package com.example.gtmvcserverside.member.validation;

import com.example.gtmvcserverside.member.dto.GTJoinInRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class GTJoinInValidator implements ConstraintValidator<ValidateJoinInDTO, GTJoinInRequest> {

    @Override
    public boolean isValid(GTJoinInRequest value, ConstraintValidatorContext context) {
        return false;
    }

    @Override
    public void initialize(ValidateJoinInDTO constraintAnnotation) {
        // initialization logic, if needed
    }

}
