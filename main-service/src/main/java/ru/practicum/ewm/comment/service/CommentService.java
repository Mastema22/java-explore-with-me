package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.request.CommentRequestDto;
import ru.practicum.ewm.comment.dto.response.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto createCommentByUserId(Long userId, Long eventId, CommentRequestDto commentRequestDto);
    List<CommentResponseDto> findCommentResponseDtos(Long eventId, Integer from, Integer size);
    CommentResponseDto findCommentResponseDtoById(Long commentId);
    CommentResponseDto updateCommentByUserId(Long userId, Long commentId, CommentRequestDto commentRequestDto);
    void deleteCommentByAdmin(Long commentId);

}