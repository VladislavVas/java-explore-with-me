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
import javax.validation.constraints.NotNull;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class CommentPrivateController {

    private final CommentService commentService;

    @PostMapping("{userId}/comments")
    public ResponseEntity<CommentDto> postComment(@PathVariable @Min(1) Long userId,
                                                  @RequestParam @Min(1) Long eventId,
                                                  @RequestParam @NotNull String text) {
        log.info("User id=" + userId + " post comment to event id=" + eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.postCommentByUser(userId, eventId, text));
    }

    @PatchMapping("{userId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable @Min(1) Long userId,
                                                    @RequestParam @Min(1) Long commentId,
                                                    @RequestParam @NotNull String text) {
        log.info("User id=" + userId + " update comment id=" + commentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.updateCommentByUser(userId, commentId, text));
    }



}
