package ru.practicum.ewm.participationRequest.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "requests")
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id", referencedColumnName = "user_id", nullable = false)
    private User requester;
    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

}


