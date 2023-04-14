package ru.practicum.ewm.comments.conroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comments.dto.CommentDto;
import ru.practicum.ewm.comments.service.CommentService;

import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentPublicController {

    private final CommentService commentService;


    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllCommentsForEvent(@RequestParam @Min(1) Long eventId) {
        log.info("Get all comments for event id= " + eventId);
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsForEvent(eventId));
    }

    @GetMapping("{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable @Min(1) Long commentId) {
        log.info("Get comment id= " + commentId);
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(commentId));
    }

}
