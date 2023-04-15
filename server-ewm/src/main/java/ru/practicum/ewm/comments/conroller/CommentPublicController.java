package ru.practicum.ewm.comments.conroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentPublicController {

    private final CommentService commentService;


    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllCommentsForEvent(
            @RequestParam @Positive Long eventId,
            @Valid @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Valid @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get all comments for event id= " + eventId);
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsForEvent(eventId, from, size));
    }

    @GetMapping("{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable @Positive Long commentId) {
        log.info("Get comment id= " + commentId);
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(commentId));
    }

}
