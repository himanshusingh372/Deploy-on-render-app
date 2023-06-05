package com.example.blog.payloads;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	private Integer PostId;
	private String Title;
	private String Content;
	private String ImageName;
	//private LocalDateTime addedDate;
	private String addedDate;
	private CategoryDto category;
	private UserDto user;
	private Set<CommentDto> comment=new HashSet<>();


}
