package dev.arhimedes.utils.validators;

import dev.arhimedes.global.enums.Role;
import dev.arhimedes.utils.validators.annotations.ValidRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {
    @Override
    public void initialize(ValidRole constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String role, ConstraintValidatorContext constraintValidatorContext) {
        for(Role roleType: Role.values()){
            if (roleType.name().equals(role.toUpperCase())){
                return true;
            }
        }
        return false;
    }
}
