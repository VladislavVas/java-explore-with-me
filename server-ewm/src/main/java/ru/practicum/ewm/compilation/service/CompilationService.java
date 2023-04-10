package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.CompilationNewDto;

import java.util.List;

public interface CompilationService {
    CompilationDto create(CompilationNewDto compilationDto);


    CompilationDto updateCompilation(Long compId, CompilationNewDto compilationDto);

    void delete(long compId);


    CompilationDto getCompilation(long compId);

    List<CompilationDto> getAllCompilations(Boolean pinned, int from, int size);
}
