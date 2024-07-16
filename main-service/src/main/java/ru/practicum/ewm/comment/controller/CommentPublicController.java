package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.response.CommentResponseDto;
import ru.practicum.ewm.comment.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comments")
public class CommentPublicController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentResponseDto> findCommentsByPublic(
            @RequestParam(name = "eventId", required = false) Long eventId,
            @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        List<CommentResponseDto> commentDto = commentService.findCommentResponseDtos(eventId, from, size);
        return commentDto;
    }

    @GetMapping(value = "/{commentId}")
    public CommentResponseDto findCommentByIdByPublic(@PathVariable(name = "commentId") Long commentId) {
        CommentResponseDto commentDto = commentService.findCommentResponseDtoById(commentId);
        return commentDto;
    }

}
