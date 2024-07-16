package ru.practicum.ewm.comment.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.comment.dto.request.CommentRequestDto;
import ru.practicum.ewm.comment.dto.response.CommentResponseDto;
import ru.practicum.ewm.comment.mapper.CommentMapper;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.comment.service.CommentService;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.exception.CommentConflictException;
import ru.practicum.ewm.exception.CommentNotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.ewm.constant.Constant.PAGE_SIZE;
import static ru.practicum.ewm.constant.Constant.SORT_BY_ID_ASC;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    @Transactional
    public CommentResponseDto createCommentByUserId(Long userId, Long eventId, CommentRequestDto commentRequestDto) {
        log.info("Create comment by user id {} for event id {}", userId, eventId);
        User userById = userService.findUserById(userId);
        Event eventById = eventService.findEventById(eventId);
        LocalDateTime created = LocalDateTime.now();
        String text = commentRequestDto.getText();

        Comment comment = new Comment();
        comment.setAuthor(userById);
        comment.setEvent(eventById);
        comment.setCreated(created);
        comment.setText(text);

        Comment savedComment = commentRepository.save(comment);

        CommentResponseDto commentResponseDto = CommentMapper.mapToCommentResponseDto(savedComment);

        return commentResponseDto;
    }

    @Override
    public List<CommentResponseDto> findCommentResponseDtos(Long eventId, Integer from, Integer size) {
        log.info("Search comments by event with id {}", eventId);


        Pageable page = getPage(from, size, SORT_BY_ID_ASC);

        List<CommentResponseDto> commentDtos = new ArrayList<>();
        List<Comment> foundComments = new ArrayList<>();
        if (eventId != null) {
            eventService.findEventById(eventId);
            foundComments = commentRepository.findAllByEventId(eventId, page);

        } else {
            Page<Comment> comments = commentRepository.findAll(page);
            foundComments = mapToList(comments);
        }

        commentDtos = CommentMapper.mapToCommentResponseDto(foundComments);
        return commentDtos;
    }

    @Override
    public CommentResponseDto findCommentResponseDtoById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Комментарий не найден"));
        CommentResponseDto commentResponseDto = CommentMapper.mapToCommentResponseDto(comment);
        return commentResponseDto;
    }

    @Override
    @Transactional
    public CommentResponseDto updateCommentByUserId(Long userId, Long commentId, CommentRequestDto commentRequestDto) {
        User user = userService.findUserById(userId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Комментарий не найден"));

        if (!user.getId().equals(comment.getAuthor().getId())) {
            throw new CommentConflictException("Автор не может комментировать");
        }

        if (!commentRequestDto.getText().isBlank()) {
            comment.setText(commentRequestDto.getText());
        }

        Comment savedComment = commentRepository.save(comment);
        CommentResponseDto updatedDto = CommentMapper.mapToCommentResponseDto(savedComment);

        return updatedDto;
    }

    @Override
    @Transactional
    public void deleteCommentByAdmin(Long commentId) {
        log.info("Удален комментарий с id {}", commentId);
        commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Комментарий не найден"));
        commentRepository.deleteById(commentId);
    }

    private static Pageable getPage(Integer from, Integer size, Sort sort) {
        Pageable page = PageRequest.of(0, PAGE_SIZE, sort);
        if (from != null && size != null) {

            if (from < 0 || size <= 0) {
                throw new IllegalStateException("Не корректные параметры страницы");
            }
            page = PageRequest.of(from > 0 ? from / size : 0, size, sort);
        }
        return page;
    }

    private static <T> List<T> mapToList(Iterable<T> obgs) {
        List<T> list = new ArrayList<>();
        for (T obg : obgs) {
            list.add(obg);
        }
        return list;
    }
}
