package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.client.StatClient;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.CompilationNewDto;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;
    private final StatClient statClient;


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
        List<Long> newEventsIds = compilationDto.getEvents();
        Set<Event> findEvents = eventRepository.findAllByIdIn(compilationDto.getEvents());
        for (Long eventId : newEventsIds) {
            findEvents.add(getEventFromRepository(eventId));
        }
        compilation.setEvents(findEvents);
        compilation = compilationMapper.mapToCompilation(compilationDto, compilation);
        return compilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public void delete(long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto getCompilation(long compId) {
        Compilation compilation = getCompilationFromRepository(compId);
        Set<Event> events = compilation.getEvents();
        events.forEach(event -> event.setViews(statClient.getViews(event.getId())));
        return compilationMapper.toCompilationDto(compilation);
    }

    @Override
    public List<CompilationDto> getAllCompilations(Boolean pinned, int from, int size) {

        Pageable pageable = PageRequest.of(from / size, size);
        List<Compilation> compilations;
        if (pinned == null) {
            compilations = compilationRepository.findAll(pageable).toList();
        } else {
            compilations = compilationRepository.findAllByPinned(pinned, pageable);
        }
        List<CompilationDto> result = new ArrayList<>();
        for (Compilation compilation : compilations) {
            Set<Event> events = compilation.getEvents();
            events.forEach(event -> event.setViews(statClient.getViews(event.getId())));
            result.add(compilationMapper.toCompilationDto(compilation));
        }
        return result;

    }

    private Event getEventFromRepository(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id=%" + id + "was not found"));
    }

    private Compilation getCompilationFromRepository(long id) {
        return compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compilation with id=%" + id + "was not found"));
    }
}
