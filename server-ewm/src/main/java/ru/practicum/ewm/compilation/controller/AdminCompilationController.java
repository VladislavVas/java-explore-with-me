package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Create;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.CompilationNewDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.constraints.Min;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Validated
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    public ResponseEntity<CompilationDto> createCompilation(@RequestBody @Validated(Create.class) CompilationNewDto compilation) {
        log.info("POST AdminCompilationController " + compilation);
        return ResponseEntity.status(HttpStatus.CREATED).body(compilationService.create(compilation));
    }

    @DeleteMapping("{compId}")
    public ResponseEntity<Void> deleteCompilation(@PathVariable @Min(1) Long compId) {
        compilationService.delete(compId);
        log.info("DELETE AdminCompilationController id=" + compId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("{compId}")
    public ResponseEntity<CompilationDto> updateCompilation(@PathVariable @Min(1) Long compId,
                                                            @RequestBody CompilationNewDto compilationDto) {
        log.info("PATH AdminCompilationController id= " + compId + compilationDto);
        return ResponseEntity.status(HttpStatus.OK).body(compilationService.updateCompilation(compId, compilationDto));
    }
}
