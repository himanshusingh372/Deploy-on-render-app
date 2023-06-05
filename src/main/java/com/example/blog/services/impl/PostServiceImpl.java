package com.example.blog.services.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.blog.entities.Category;
import com.example.blog.entities.Post;
import com.example.blog.entities.User;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.PageResponse;
import com.example.blog.payloads.PostDto;
import com.example.blog.repositories.CategoryRepo;
import com.example.blog.repositories.PostRepo;
import com.example.blog.repositories.UserRepo;
import com.example.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelmapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userid, Integer categoryid) {
		User user = this.userRepo.findById(userid)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userid));

		Category category = this.categoryRepo.findById(categoryid)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryid));

		Post create = this.modelmapper.map(postDto, Post.class);

		create.setImageName("default.jpg");
		//create.setAddedDate(new Date());
		
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
		String formattedDate = dateTime.format(formatter);
		//System.out.println(formattedDate);
		
		//create.setAddedDate(LocalDateTime.now());
		create.setAddedDate(formattedDate);
		create.setUser(user);
		create.setCategory(category);
		// create.setPostId(postDto.getPostId());

		Post created = this.postRepo.save(create);
		return this.modelmapper.map(created, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post update = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		Category category =this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();
		update.setTitle(postDto.getTitle());
		update.setContent(postDto.getContent());
		update.setImageName(postDto.getImageName());
		update.setCategory(category);
		Post updated = this.postRepo.save(update);

		return this.modelmapper.map(updated, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostDto getPost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));

		return this.modelmapper.map(post, PostDto.class);
	}

	@Override
	public PageResponse getAllPost(Integer pagenumber, Integer pagesize, String sortby, String dir) {
		Sort sort=(dir.equalsIgnoreCase("asc"))?Sort.by(sortby).ascending():Sort.by(sortby).descending();
		Pageable p=PageRequest.of(pagenumber, pagesize, sort);
		Page<Post> pagepost = this.postRepo.findAll(p);
		List<Post> allpost =pagepost.getContent();
		
		List<PostDto> postdto = allpost.stream()
				.map((item) -> this.modelmapper.map(item, PostDto.class))
				.collect(Collectors.toList());
		PageResponse pageResponse=new PageResponse();
		pageResponse.setContent(postdto);
		pageResponse.setPageNumber(pagepost.getNumber());
		pageResponse.setPageSize(pagepost.getSize());
		pageResponse.setTotalElement(pagepost.getTotalElements());
		pageResponse.setTotalPage(pagepost.getTotalPages());
		pageResponse.setLastPage(pagepost.isLast());
		
		return pageResponse;
	}

	@Override
	public PageResponse getPostByCategory(Integer categoryId, 
			Integer pagenumber, Integer pagesize,String sortby, String dir) 
	{
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> 
				new ResourceNotFoundException("Category", "CategoryId", categoryId));
		
		Sort sort=(dir.equalsIgnoreCase("asc"))?Sort.by(sortby).ascending():Sort.by(sortby).descending();
		Pageable p=PageRequest.of(pagenumber, pagesize,sort);
		Page<Post> pagepost = this.postRepo.findByCategory(cat,p);
		List<Post> allpost =pagepost.getContent();

		List<PostDto> postdto = allpost.stream().map((item) -> this.modelmapper.map(item, PostDto.class))
				.collect(Collectors.toList());
		
		PageResponse pageResponse=new PageResponse();
		pageResponse.setContent(postdto);
		pageResponse.setPageNumber(pagepost.getNumber());
		pageResponse.setPageSize(pagepost.getSize());
		pageResponse.setTotalElement(pagepost.getTotalElements());
		pageResponse.setTotalPage(pagepost.getTotalPages());
		pageResponse.setLastPage(pagepost.isLast());
		return pageResponse;
	}

	@Override
	public PageResponse getPostByUser(Integer userId,Integer pagenumber, Integer pagesize,String sortby,String dir) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		
		Sort sort=(dir.equalsIgnoreCase("asc"))?Sort.by(sortby).ascending():Sort.by(sortby).descending();
		Pageable p=PageRequest.of(pagenumber, pagesize, sort);
		Page<Post> pagepost=this.postRepo.findByUser(user, p);
		List<Post> allpost=pagepost.getContent();
		List<PostDto> postdtos = allpost.stream().map((item) -> this.modelmapper.map(item, PostDto.class))
				.collect(Collectors.toList());

		PageResponse pageResponse=new PageResponse();
		pageResponse.setContent(postdtos);
		pageResponse.setPageNumber(pagepost.getNumber());
		pageResponse.setPageSize(pagepost.getSize());
		pageResponse.setTotalElement(pagepost.getTotalElements());
		pageResponse.setTotalPage(pagepost.getTotalPages());
		pageResponse.setLastPage(pagepost.isLast());
		
		return pageResponse;
	}

	@Override
	public List<PostDto> searchPosts(String Keyword) {

//		Sort sort=(dir.equalsIgnoreCase("asc"))?Sort.by(sortby).ascending():Sort.by(sortby).descending();
//		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		
		List<Post> allpost=this.postRepo.findByTitleContaining(Keyword);
		//List<Post> allpost=pagepost.getContent();
		List<PostDto> postdtos = allpost.stream().map((item)-> this.modelmapper.map(item, PostDto.class)).collect(Collectors.toList());
		
//		PageResponse pageResponse=new PageResponse();
//		pageResponse.setContent(postdtos);
//		pageResponse.setPageNumber(pagepost.getNumber());
//		pageResponse.setPageSize(pagepost.getSize());
//		pageResponse.setTotalElement(pagepost.getTotalElements());
//		pageResponse.setTotalPage(pagepost.getTotalPages());
//		pageResponse.setLastPage(pagepost.isLast());
		
		return postdtos;
	}

}
