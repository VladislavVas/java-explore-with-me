package ru.practicum.ewm.participationRequest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.participationRequest.model.ParticipationRequest;
import ru.practicum.ewm.user.model.User;

import java.util.List;

@Repository
public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findAllByRequester(User user);

    List<ParticipationRequest> findAllByEvent(Event event);

    List<ParticipationRequest> findByRequesterIdAndEventId(Long userId, Long eventId);

}