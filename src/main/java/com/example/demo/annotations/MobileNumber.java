package com.example.demo.annotations;

//mannual annotation for validation of mobile number input
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = com.example.demo.Validator.MobileNumberValidator.class)
public @interface MobileNumber {
	String message() default "Enter valid 10 digit mobile number";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
