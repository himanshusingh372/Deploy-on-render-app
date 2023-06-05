package com.example.blog.services;

import java.util.List;

import com.example.blog.payloads.CategoryDto;

public interface CategoryService {
// create
	CategoryDto createCatogory(CategoryDto categoryDto);

// update
	CategoryDto updateCatogory(CategoryDto categoryDto, Integer categoryId);

// delete
	void deleteCatogory(Integer categoryId);

// get
	CategoryDto getCatogory(Integer categoryId);

// getAll
	List<CategoryDto> getAllCatogory();

}
