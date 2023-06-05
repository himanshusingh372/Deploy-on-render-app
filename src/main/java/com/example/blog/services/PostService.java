package com.example.blog.services;

import java.util.List;

import com.example.blog.payloads.PageResponse;
import com.example.blog.payloads.PostDto;

public interface PostService {

	// create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// update
	PostDto updatePost(PostDto postDto, Integer postId);

	// delete
	void deletePost(Integer postId);

	// get
	PostDto getPost(Integer postId);

	// getAll
	PageResponse getAllPost(Integer pagenumber, Integer pagesize, String sortby,String dir);

	// getAllPostByCategory
	PageResponse getPostByCategory(Integer categoryId,Integer pagenumber, Integer pagesize,String sortby,String dir);

	// getAllPostByUser
	PageResponse getPostByUser(Integer userId,Integer pagenumber, Integer pagesize, String sortby, String dir);

	// searchPosts
	//List<PostDto> searchPosts(String Keyword,Integer pagenumber, Integer pagesize,String sortby, String dir);
	List<PostDto> searchPosts(String keyword);

}
