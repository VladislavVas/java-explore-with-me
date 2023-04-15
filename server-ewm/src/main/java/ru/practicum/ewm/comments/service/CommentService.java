package ru.practicum.ewm.comments.service;

import ru.practicum.ewm.comments.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto postCommentByUser(Long userId, Long eventId, String text);

    CommentDto updateCommentByUser(Long userId, Long commentId, String text);

    void deleteCommentByAdmin(Long eventId);

    CommentDto getComment(Long commentId);

    List<CommentDto> getCommentsForEvent(Long eventId, Integer from, Integer size);

}
