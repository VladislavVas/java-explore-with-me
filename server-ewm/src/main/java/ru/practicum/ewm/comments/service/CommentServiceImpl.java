package ru.practicum.ewm.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.mapper.CommentMapper;
import ru.practicum.ewm.comments.model.Comment;
import ru.practicum.ewm.comments.repository.CommentRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidateException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto postCommentByUser(Long userId, Long eventId, String text) {
        User user = getUserFromRepository(userId);
        Event event = getEventFromRepository(eventId);
        if (event.getState().equals(State.PUBLISHED)) {
            Comment comment = commentMapper.toComment(user, event, text);
            return commentMapper.toCommentDto(commentRepository.save(comment));
        } else {
            throw new ConflictException("You cannot comment on an unpublished event");
        }
    }

    @Override
    public CommentDto updateCommentByUser(Long userId, Long commentId, String text) {
        if (userRepository.existsById(userId)) {
            Comment comment = getCommentFromRepository(commentId);
            if (comment.getUser().getId() != userId) {
                throw new ValidateException("User have no root to update");
            }
            comment.setText(text);
            return commentMapper.toCommentDto(commentRepository.save(comment));
        } else {
            throw new NotFoundException("User with id=%" + userId + "was not found");
        }
    }

    @Override
    public void deleteCommentByAdmin(Long eventId) {
        commentRepository.deleteById(eventId);
    }

    @Override
    public CommentDto getComment(Long commentId) {
        return commentMapper.toCommentDto(getCommentFromRepository(commentId));
    }

    @Override
    public List<CommentDto> getCommentsForEvent(Long eventId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        List<Comment> comments = commentRepository.findAllByEventId(eventId, pageable);
        return commentMapper.toCommentDtoList(comments);
    }

    private User getUserFromRepository(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=%" + id + "was not found"));
    }

    private Event getEventFromRepository(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id=%" + id + "was not found"));
    }

    private Comment getCommentFromRepository(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Comment with id=%" + id + "was not found"));
    }

}
