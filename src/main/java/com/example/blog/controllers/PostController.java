package com.example.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.blog.config.AppConstants;
import com.example.blog.payloads.PageResponse;
import com.example.blog.payloads.PostDto;
import com.example.blog.services.FileService;
import com.example.blog.services.PostService;

@RestController
@RequestMapping("/api/v1")
public class PostController {

	@Autowired
	private PostService postservice;
	
	@Autowired
	private FileService fileservice;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userid}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(  Integer postid,
			@RequestBody PostDto postDto,
			@PathVariable Integer userid, 
			@PathVariable Integer categoryId) {

		PostDto createpost = this.postservice.createPost(postDto, userid, categoryId);

		return new ResponseEntity<PostDto>(createpost, HttpStatus.ACCEPTED);
	}

	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(
			@RequestBody PostDto postDto, 
			@PathVariable Integer postId) 
	{
		PostDto updatepost = this.postservice.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatepost, HttpStatus.OK);
	}

	// get by user

	@GetMapping("/user/{userid}/posts")
	public ResponseEntity<PageResponse> getPostsByUser(
			@PathVariable Integer userid,
			@RequestParam(value="pageNumber",defaultValue="0",required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="3",required=false) Integer pageSize,
			@RequestParam(value="sortby",defaultValue="postId",required=false) String sortby,
			@RequestParam(value="dir",defaultValue="asc",required=false) String dir) 
	{
		PageResponse pageResponse = this.postservice.getPostByUser(userid,pageNumber,pageSize,sortby,dir);
		return new ResponseEntity<PageResponse>(pageResponse, HttpStatus.OK);
	}

	// get by category

	@GetMapping("/category/{categoryid}/posts")
	public ResponseEntity<PageResponse> getPostsByCategory(
			@PathVariable Integer categoryid,
			@RequestParam(value="pageNumber",defaultValue="0",required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="3",required=false) Integer pageSize,
			@RequestParam(value="sortby",defaultValue="postId",required=false)String sortby,
			@RequestParam(value="dir",defaultValue="asc",required=false) String dir) 
			
	{
		PageResponse pageResponse = this.postservice.getPostByCategory(categoryid,pageNumber,pageSize,sortby,dir);
		return new ResponseEntity<PageResponse>(pageResponse, HttpStatus.OK);
	}

	// get post by id

	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(
			@PathVariable Integer postId) 
	{
		PostDto postdto = this.postservice.getPost(postId);
		return new ResponseEntity<PostDto>(postdto, HttpStatus.OK);
	}

	// get All post

	@GetMapping("/posts")
	public ResponseEntity<PageResponse> getAllPost(
		@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
		@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
		@RequestParam(value="sortby",defaultValue=AppConstants.SORT_BY,required=false) String sortby,
		@RequestParam(value="dir",defaultValue=AppConstants.SORT_DIR,required=false) String dir) 
	 {
		PageResponse pageResponse = this.postservice.getAllPost(pageNumber,pageSize,sortby,dir);
		return new ResponseEntity<PageResponse>(pageResponse, HttpStatus.OK);
	}
	
	// delete post
	// only by Admin
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<?> deletePost(
			@PathVariable Integer postId) 
	{
		this.postservice.deletePost(postId);
		return new ResponseEntity<>
		(Map.of("message", "Post deleted successfully"), HttpStatus.OK);
	}
	
	//search post
	@GetMapping("/posts/search/{element}")
	public ResponseEntity<List<PostDto>> searchPost(
			@PathVariable("element") String element

			)
	{
		List<PostDto> result=this.postservice.searchPosts(element);
		return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
	}
	
	//post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		
		PostDto postdto=this.postservice.getPost(postId);
		String filename=this.fileservice.uploadImage(path,image);
		
		postdto.setImageName(filename);
		PostDto updatepost=this.postservice.updatePost(postdto, postId);
		
		return new ResponseEntity<PostDto>(updatepost,HttpStatus.OK);
	}
	
	// method to serve file
		
	@GetMapping(value="/posts/image/{imagename}",produces= MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imagename") String imageName,
			HttpServletResponse response) throws IOException
	{
	InputStream resourse=this.fileservice.getResourse(path, imageName);
	response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	StreamUtils.copy(resourse,response.getOutputStream());
	}
			

}
