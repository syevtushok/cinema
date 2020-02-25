package mate.academy.cinema.security.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import mate.academy.cinema.annotations.PasswordEqualsValidate;
import mate.academy.cinema.dto.request.UserRequestDto;

public class PasswordEqualsValidater implements ConstraintValidator<PasswordEqualsValidate,
        UserRequestDto> {
    @Override
    public void initialize(PasswordEqualsValidate constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserRequestDto user,
                           ConstraintValidatorContext constraintValidatorContext) {
        return user.getPassword().equals(user.getRepeatedPassword());
    }
}
