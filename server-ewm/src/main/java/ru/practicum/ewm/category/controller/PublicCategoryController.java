package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.sevice.CategoryService;

import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(@RequestParam(defaultValue = "0") Integer from,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET PublicCategoryController from" + from + " size " + size);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getListCategory(from, size));
    }

    @GetMapping("{catId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable @Min(1) Long catId) {
        log.info("GET PublicCategoryController id=" + catId);
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory(catId));
    }

}