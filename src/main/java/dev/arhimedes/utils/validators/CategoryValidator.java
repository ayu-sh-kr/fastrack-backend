package dev.arhimedes.utils.validators;

import dev.arhimedes.product.enums.Category;
import dev.arhimedes.utils.validators.annotations.ValidCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<ValidCategory, String> {
    @Override
    public void initialize(ValidCategory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String category, ConstraintValidatorContext constraintValidatorContext) {

        for(Category category1: Category.values()){
            if(category1.name().equals(category.toUpperCase())) return true;
        }

        return false;
    }
}
