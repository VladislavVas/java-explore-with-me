package ru.practicum.ewm.category.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidateException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category category = categoryMapper.toCategory(newCategoryDto);
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto updateCategory(long id, CategoryDto categoryDto) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Category with id=%" + categoryDto.getId() + "was not found");
        }
        Category updatingCategory = getCategoryFromRepository(id);
        updatingCategory.setName(categoryDto.getName());
        categoryRepository.save(updatingCategory);
        return categoryMapper.toCategoryDto(updatingCategory);
    }

    @Override
    public CategoryDto getCategory(long id) {
        Category category = getCategoryFromRepository(id);
        return categoryMapper.toCategoryDto(category);
    }

    @Override
    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> getListCategory(int from, int size) {
        int page = getPage(from, size);
        List<Category> categories = categoryRepository.findAll(PageRequest.of(page, size)).getContent();
        return categoryMapper.toCategoryDtoList(categories);
    }

    private Category getCategoryFromRepository(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with id=%" + id + "was not found"));
    }

    private int getPage(int from, int size) {
        if (from < 0 || size <= 0) {
            throw new ValidateException("Invalid page or size parameters");
        } else {
            return from / size;
        }
    }
}
