package ru.practicum.ewm.request.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.EventState;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.DataConflictException;
import ru.practicum.ewm.exception.EventNotFoundException;
import ru.practicum.ewm.exception.RequestNotFoundException;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.mapper.RequestMapper;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.request.service.RequestService;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserService userService;
    private final EventRepository eventRepository;

    @Override
    public List<ParticipationRequestDto> findUserRequests(Long userId) {
        User requester = userService.findUserById(userId);
        List<Request> requests = requestRepository.findAllByRequesterId(userId);
        log.info("Выполнен поиск запросов на участие пользователя {}", userId);
        return RequestMapper.toDtos(requests);
    }

    @Override
    @Transactional
    public ParticipationRequestDto addRequest(Long userId, Long eventId) {
        Request request = new Request();
        request.setCreatedAt(LocalDateTime.now().plusHours(2));

        User requester = userService.findUserById(userId);

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Событие, указанное для запроса, не найдено"));

        List<Request> requests = requestRepository.findAllByRequesterIdAndEventId(userId, eventId);

        if (!requests.isEmpty()) {
            throw new DataConflictException("Заявка на участие пользователя с id " + userId + " в событии с id " +
                    eventId + " уже существует");
        }

        if (userId.equals(event.getInitiator().getId())) {
            throw new DataConflictException("Создатель события не может быть его участником");
        }

        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new DataConflictException("Заявку на участие можно создать только для опубликованного события");
        }

        if (event.getParticipantLimit() != 0 && event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new DataConflictException("Невозможно добавить новую заявку на участие, " +
                    "достигнуто максимальное количество");
        }
        request = RequestMapper.toRequest(requester, event);
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            request.setStatus(RequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }
        request = requestRepository.save(request);
        log.info("Добавлен новый запрос на участие в событии {} пользователя {}", event.getId(), userId);
        return RequestMapper.toRequestDto(request);
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectRequest(Long userId, Long requestId) {
        User requester = userService.findUserById(userId);
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Запрос для отклонения не найден"));
        request.setStatus(RequestStatus.CANCELED);
        request = requestRepository.save(request);
        log.info("Запрос на участие с id {} был отменён", requestId);
        return RequestMapper.toRequestDto(request);
    }
}