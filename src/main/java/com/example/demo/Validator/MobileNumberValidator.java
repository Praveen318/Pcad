package com.example.demo.Validator;


//custom validator class for the MobileNumber annotation
import com.example.demo.annotations.MobileNumber;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileNumberValidator implements ConstraintValidator<MobileNumber, String> {
    @Override
    public boolean isValid(String mobileNumber, ConstraintValidatorContext context) {
        if (mobileNumber == null||mobileNumber.length()!=10) {
            return false;
        }        
        return mobileNumber.matches("\\d+");
    }
}
