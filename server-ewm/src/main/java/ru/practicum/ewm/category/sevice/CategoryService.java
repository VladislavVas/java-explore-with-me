package ru.practicum.ewm.category.sevice;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    CategoryDto updateCategory(long catId, CategoryDto categoryDto);

    CategoryDto getCategory(long id);

    void deleteCategory(long id);

    List<CategoryDto> getListCategory(int from, int size);
}
