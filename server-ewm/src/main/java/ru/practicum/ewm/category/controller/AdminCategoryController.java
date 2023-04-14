package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Create;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.sevice.CategoryService;

import javax.validation.constraints.Min;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;


    @PostMapping
    public ResponseEntity<CategoryDto> postCategory(@RequestBody @Validated(Create.class) NewCategoryDto newCategoryDto) {
        log.info("POST AdminCategoryController" + newCategoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(newCategoryDto));
    }

    @PatchMapping("{catId}")
    public ResponseEntity<CategoryDto> patchCategory(@PathVariable @Min(1) Long catId,
                                                     @RequestBody @Validated(Create.class) CategoryDto categoryDto) {
        log.info("PATCH AdminCategoryController id=" + catId + categoryDto);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.updateCategory(catId, categoryDto));
    }

    @DeleteMapping("{catId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable @Min(1) Long catId) {
        categoryService.deleteCategory(catId);
        log.info("DELETE AdminCategoryController id=" + catId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
