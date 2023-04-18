package ru.practicum.ewm.comments.conroller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.comments.service.CommentService;

import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class CommentAdminConroller {

    private final CommentService commentService;

    @DeleteMapping("comments/{commentId}")
    public ResponseEntity<Void> deleteCommentAdmin(@PathVariable @Positive Long commentId) {
        commentService.deleteCommentByAdmin(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
