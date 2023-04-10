package ru.practicum.ewm.participationRequest.repository;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.participationRequest.model.ParticipationRequest;
import ru.practicum.ewm.user.model.User;

import java.util.List;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findAllByRequester(User user);

    List<ParticipationRequest> findAllByEvent(Event event);

//    @Query(value = "SELECT * FROM requests WHERE requester_id = ?1 AND event_id = ?2 ORDER BY request_id", nativeQuery = true)
    List<ParticipationRequest> findByRequesterIdAndEventId(Long userId, Long eventId);
}