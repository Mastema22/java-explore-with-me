package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.request.CommentRequestDto;
import ru.practicum.ewm.comment.dto.response.CommentResponseDto;
import ru.practicum.ewm.comment.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/comments")
public class CommentPrivateController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@Valid @NotNull @PathVariable(name = "userId") Long userId,
                                            @Valid @NotNull @RequestParam(name = "eventId") Long eventId,
                                            @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.createCommentByUserId(userId, eventId, commentRequestDto);
    }

    @PatchMapping(value = "/{commentId}")
    public CommentResponseDto updateComment(
            @Valid @NotNull @PathVariable(name = "userId") Long userId,
            @Valid @NotNull @PathVariable(name = "commentId") Long commentId,
            @Valid @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.updateCommentByUserId(userId, commentId, commentRequestDto);
    }
}