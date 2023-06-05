package com.example.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.blog.entities.Category;
import com.example.blog.exceptions.ResourceNotFoundException;
import com.example.blog.payloads.CategoryDto;
import com.example.blog.repositories.CategoryRepo;
import com.example.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelmapper;

	@Override
	public CategoryDto createCatogory(CategoryDto categoryDto) {
		Category cat = this.modelmapper.map(categoryDto, Category.class);
		Category savedcat = this.categoryRepo.save(cat);

		return this.modelmapper.map(savedcat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCatogory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDes(categoryDto.getCategoryDes());

		Category updatedcat = this.categoryRepo.save(cat);
		return this.modelmapper.map(updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCatogory(Integer categoryId) {

		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));
		this.categoryRepo.delete(cat);

	}

	@Override
	public CategoryDto getCatogory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		return this.modelmapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCatogory() {
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> catDto = categories.stream().map((cat) -> this.modelmapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		return catDto;
	}

}
