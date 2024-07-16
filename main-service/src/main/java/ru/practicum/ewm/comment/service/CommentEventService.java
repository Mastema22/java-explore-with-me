package ru.practicum.ewm.comment.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.comment.dto.response.CommentResponseDto;
import ru.practicum.ewm.comment.mapper.CommentMapper;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class CommentEventService {
    private final CommentRepository commentRepository;

    public List<CommentResponseDto> findCommentResponseDtos(Long eventId) {
        log.info("Search comments by event with id {}", eventId);
        List<CommentResponseDto> commentDtos = new ArrayList<>();
        List<Comment> foundComments = new ArrayList<>();
        if (eventId != null) {
            foundComments = commentRepository.findAllByEventId(eventId);

        } else {
            throw new RuntimeException("Список комментариев не найден");
        }
        commentDtos = CommentMapper.mapToCommentResponseDto(foundComments);
        return commentDtos;
    }
}