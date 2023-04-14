package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.CompilationNewDto;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto create(CompilationNewDto compilationNewDto) {
        Set<Event> events = eventRepository.findAllByIdIn(compilationNewDto.getEvents());
        Compilation compilation = compilationMapper.toCompilation(compilationNewDto);
        compilation.setEvents(events);
        return compilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public CompilationDto updateCompilation(Long compId, CompilationNewDto compilationDto) {
        Compilation compilation = getCompilationFromRepository(compId);
        Set<Event> findEvents = eventRepository.findAllByIdIn(compilationDto.getEvents());
        compilation.setEvents(findEvents);
        compilation = compilationMapper.mapToCompilation(compilationDto, compilation);
        return compilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto getCompilation(Long compId) {
        Compilation compilation = getCompilationFromRepository(compId);
        return compilationMapper.toCompilationDto(compilation);
    }

    @Override
    public List<CompilationDto> getAllCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Compilation> compilations;
        if (pinned == null) {
            compilations = compilationRepository.findAll(pageable).toList();
        } else {
            compilations = compilationRepository.findAllByPinned(pinned, pageable);
        }
        return compilationMapper.toCompilationDtoList(compilations);
    }


    private Compilation getCompilationFromRepository(long id) {
        return compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compilation with id=%" + id + "was not found"));
    }

}
