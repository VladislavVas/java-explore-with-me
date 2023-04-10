package ru.practicum.ewm.event.repository;

import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;

import java.beans.BeanProperty;
import java.time.LocalDateTime;
import java.util.List;

public interface EventCustomRepository {
    List<Event> getEventsByAdmin(List<Long> users, List<State> states, List<Long> categories,
                                 LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    List<Event> getEventsByPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                  LocalDateTime rangeEnd, Integer from, Integer size);
}
