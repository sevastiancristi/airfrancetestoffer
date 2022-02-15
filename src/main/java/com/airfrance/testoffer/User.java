package com.airfrance.testoffer;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import com.airfrance.testoffer.validators.AdultBirthDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

	public enum Gender {
		MALE, FEMALE
	};

	// chose this as primary key instead of name, because two users with the same
	// name is possible
	private @Id @GeneratedValue Long id;

	@NotBlank(message = "Name value must be present!")
	private String name;

	@NotNull(message = "Date value must be present!")
	@AdultBirthDate
	private @Temporal(TemporalType.DATE) Date birthDate;

	@NotBlank(message = "Residence country value must be present!")
	@Pattern(regexp = "france", flags = Flag.CASE_INSENSITIVE, message = "Registered user must be from France!")
	private String residenceCountry;

	@Pattern(regexp = "[+]?[0-9]*", message = "Phone number format is not accepted!")
	private String telephoneNumber;

	private @Enumerated(EnumType.STRING) Gender gender;

	public User(String name, Date date, String country, String telephoneNumber, Gender gender) {
		this.name = name;
		this.birthDate = date;
		this.residenceCountry = country;
		this.telephoneNumber = telephoneNumber;
		this.gender = gender;
	}

}
