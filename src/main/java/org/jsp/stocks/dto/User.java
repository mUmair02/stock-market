package org.jsp.stocks.dto;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@Size(min=2,max=15, message = "*it should be between 3 to 15 characters")
	private String name;
	
	@Email(message = "*It should be a valid email")
	@NotEmpty(message = "*It is required")
	private String email;
	
	
	@DecimalMin(value = "6000000000", message = "*It should be proper Mobile Number")
	@DecimalMax(value = "9999999999", message = "*It should be proper Mobile Number")
	private long mobile;
	
	@Past(message = "*Enter proper DOB")
	@NotNull(message = "*It is required")
	private LocalDate dob;
	
	
	@Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",message = "*  It should be One Uppercase, One Lowercase, One Number, One Special Chrecter,Minium 8 charecters")
	private String password;
	
	
	@Pattern(regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",message = "*  It should be One Uppercase, One Lowercase, One Number, One Special Chrecter,Minium 8 charecters")
	private String confirmPassword;
	
	private int otp;
	private boolean verified;
}

