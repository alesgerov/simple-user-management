package com.example.simpleuser.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Constraint(validatedBy = UniqueUsernameValidator.class)
@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueUsername {

    String message() default "Bu istifadəçi adı artıq mövcuddur.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}