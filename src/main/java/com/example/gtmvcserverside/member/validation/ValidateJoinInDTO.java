package com.example.gtmvcserverside.member.validation;


import com.example.gtmvcserverside.member.dto.GTJoinInRequest;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GTJoinInValidator.class)
public @interface ValidateJoinInDTO {

    String message() default "You're DTO has problem";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
