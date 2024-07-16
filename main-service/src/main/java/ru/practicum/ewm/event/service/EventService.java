package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.EventWithCommentsFullDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.admin.EventAdminParam;
import ru.practicum.ewm.event.dto.admin.UpdateEventAdminRequest;
import ru.practicum.ewm.event.dto.user.EventUserParam;
import ru.practicum.ewm.event.dto.user.UpdateEventUserRequest;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {

    List<EventShortDto> findEventsOfUser(Long userId, Integer from, Integer size);

    EventFullDto addEvent(Long userId, NewEventDto newEventDto);

    EventFullDto findUserEventById(Long userId, Long eventId);

    EventFullDto userUpdateEvent(Long userId, Long eventId, UpdateEventUserRequest eventUpdate);

    EventFullDto adminUpdateEvent(Long eventId, UpdateEventAdminRequest eventUpdate);

    List<EventFullDto> findEventsByAdmin(EventAdminParam eventAdminParam);

    List<EventShortDto> findEventsByPublic(EventUserParam eventUserParam, HttpServletRequest request);

    EventWithCommentsFullDto findEventByIdWithComment(Long eventId, HttpServletRequest request);

    List<ParticipationRequestDto> findUserEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult changeEventRequestsStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest request);

    List<Event> findAllByIds(List<Long> ids);

    Event findEventById(Long eventId);
}
