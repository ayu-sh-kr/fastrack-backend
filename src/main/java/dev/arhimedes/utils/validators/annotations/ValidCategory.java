package dev.arhimedes.utils.validators.annotations;

import dev.arhimedes.utils.validators.CategoryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
public @interface ValidCategory {
    String message() default "Not a valid product category";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
