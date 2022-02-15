package com.airfrance.testoffer.validators;

import java.util.Calendar;
import java.util.Date;
import javax.validation.*;

public class UserBirthDateValidator implements ConstraintValidator<AdultBirthDate, Date>{

	@SuppressWarnings("deprecation")
	@Override
	public boolean isValid(Date value, ConstraintValidatorContext context) {
		Date lDate = new Date();
		lDate.setYear(lDate.getYear()-18);
		return value.before(lDate);
	}

}
