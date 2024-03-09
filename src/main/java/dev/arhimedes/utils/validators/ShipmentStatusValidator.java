package dev.arhimedes.utils.validators;

import dev.arhimedes.shipment.enums.ShipmentStatus;
import dev.arhimedes.utils.validators.annotations.ValidShipmentStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ShipmentStatusValidator implements ConstraintValidator<ValidShipmentStatus, String> {

    @Override
    public void initialize(ValidShipmentStatus constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String status, ConstraintValidatorContext constraintValidatorContext) {

        for(ShipmentStatus shipmentStatus: ShipmentStatus.values()){
            if(shipmentStatus.name().equals(status.toUpperCase())) return true;
        }
        return false;

    }

}
