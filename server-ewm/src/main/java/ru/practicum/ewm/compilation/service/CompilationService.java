package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.CompilationNewDto;

import java.util.List;

public interface CompilationService {

    CompilationDto create(CompilationNewDto compilationDto);

    CompilationDto updateCompilation(Long compId, CompilationNewDto compilationDto);

    void delete(Long compId);

    CompilationDto getCompilation(Long compId);

    List<CompilationDto> getAllCompilations(Boolean pinned, Integer from, Integer size);

}
