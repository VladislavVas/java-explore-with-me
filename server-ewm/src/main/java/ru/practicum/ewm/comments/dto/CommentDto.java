package ru.practicum.ewm.comments.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    private String user;

    private String event;

    private String text;

    private String created;

}
