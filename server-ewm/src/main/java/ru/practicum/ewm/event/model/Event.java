package ru.practicum.ewm.event.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.participationRequest.model.ParticipationRequest;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "title", length = 120)
    private String title;

    @Column(name = "annotation", length = 2000)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @Column(name = "description", length = 7000)
    private String description;

    @Future
    @Column(name = "event_date")
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime eventDate;

    @Column(name = "created")
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime createdOn = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "participant_limit")
    private int participantLimit;

    @Column(name = "published_on")
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    private LocalDateTime publishedOn;

    @Enumerated(EnumType.STRING)
    private State state = State.PENDING;

    @ManyToOne
    @JoinColumn(name = "initiator", referencedColumnName = "user_id")
    private User initiator;

    @OneToMany
    @JoinColumn(name = "request_id")
    private List<ParticipationRequest> participants = new ArrayList<>();

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Column(name = "confirmed_requests")
    private int confirmedRequests = 0;

    @Column
    private Long views = 0L;

}
