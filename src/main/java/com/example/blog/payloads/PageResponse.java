package com.example.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PageResponse {

	
	public List<PostDto> content;
	public Integer pageNumber;
	public Integer pageSize;
	public Long totalElement;
	public Integer totalPage;
	public boolean lastPage;	
}
