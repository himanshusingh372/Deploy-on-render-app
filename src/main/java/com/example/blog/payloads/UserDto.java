package com.example.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	private Integer id;

	@NotEmpty
	@Size(min = 4, message = "username must have atleast 4 character !!")
	private String name;
	
	
	@NotBlank
	@Email(message = "Email adress is not valid")
	@Column(unique = true)
	private String email;

	@NotEmpty
	@Size(min = 4, max = 10, message = "Password atleast have 4 and atmost 10 characters")
	private String password;

	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles=new HashSet<>();
	
	@JsonProperty
	public String setPassword(String pass) {
		return this.password=pass;}
		
	@JsonIgnore
	public String getPassword() {
		return this.password;
	
	
	}
}
