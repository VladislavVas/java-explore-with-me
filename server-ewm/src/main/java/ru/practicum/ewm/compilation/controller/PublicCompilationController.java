package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Validated
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping("{compId}")
    public ResponseEntity<CompilationDto> getCompilation(@PathVariable @Min(1) Long compId) {
        log.info("GET PublicCompilationController id= " + compId);
        return ResponseEntity.status(HttpStatus.OK).body(compilationService.getCompilation(compId));
    }

    @GetMapping
    public ResponseEntity<List<CompilationDto>> getAllCompilations(
            @RequestParam(required = false) Boolean pinned,
            @Valid @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Valid @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET PublicCompilationController ALL by size= " + size + " from= " + from + " pinned = " + pinned);
        return ResponseEntity.status(HttpStatus.OK)
                .body(compilationService.getAllCompilations(pinned, from, size));
    }

}
