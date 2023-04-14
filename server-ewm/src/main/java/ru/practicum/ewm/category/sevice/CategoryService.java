package ru.practicum.ewm.category.sevice;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(Long catId, CategoryDto categoryDto);

    CategoryDto getCategory(Long id);

    void deleteCategory(Long id);

    List<CategoryDto> getListCategory(Integer from, Integer size);

}
