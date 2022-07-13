package com.example.tracker.validations.validator;

import com.example.tracker.validations.Enum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class EnumValidator implements ConstraintValidator<Enum, java.lang.Enum<?>> {
    private List<String> acceptedValues;


    @Override
    public void initialize(Enum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(java.lang.Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(java.lang.Enum<?> value, ConstraintValidatorContext context) {
        return isBlank(value.name()) || acceptedValues.contains(value.name());
    }
}
