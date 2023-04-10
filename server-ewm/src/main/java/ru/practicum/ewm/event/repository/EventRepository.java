package ru.practicum.ewm.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.model.Event;

import java.util.List;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventCustomRepository {

    @Query(value = "SELECT * FROM events WHERE initiator IN (?1) ORDER BY initiator DESC LIMIT ?2 OFFSET ?3",
            nativeQuery = true)
    List<Event> findAllByInitiatorId(Long id, Integer size, Integer from);

    Event findByIdAndInitiatorId(Long eventId, Long initiatorId);

    Set<Event> findAllByIdIn(List<Long> eventIds);
//    Set<Event> findAllByEventIdIn(Set<Long> eventIds);
}
