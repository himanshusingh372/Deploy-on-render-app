package com.example.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private Integer categoryId;
	@NotEmpty
	@NotBlank
	@Size(min = 4, max = 20, message = "length must atleast 4 or atmost 20 characters")
	private String categoryTitle;
	@NotEmpty
	@NotBlank
	@Size(min = 10, max = 100, message = "atlest 10 and atmost 100 characters")
	private String categoryDes;

}
