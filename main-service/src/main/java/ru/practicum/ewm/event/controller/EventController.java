package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class EventController {

    private final EventService eventService;
    private final StatisticClient statisticClient;

    //Private endpoints
    @PostMapping(value = "/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEvent(@PathVariable Long userId, @Valid @RequestBody NewEventDto newEventDto) {
        return eventService.addEvent(userId, newEventDto);
    }

    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> findEventsOfUser(@PathVariable Long userId,
                                                @RequestParam(required = false, defaultValue = "0") Integer from,
                                                @RequestParam(required = false, defaultValue = "10") Integer size) {
        return eventService.findEventsOfUser(userId, from, size);
    }

    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto findUserEventById(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.findUserEventById(userId, eventId);
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}")
    public EventFullDto userUpdateEvent(@PathVariable Long userId,
                                        @PathVariable Long eventId,
                                        @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return eventService.userUpdateEvent(userId, eventId, updateEventUserRequest);
    }

    //Admin endpoints
    @GetMapping("/admin/events")
    public List<EventFullDto> findEventsByAdmin(@RequestParam(required = false) List<Long> users,
                                                @RequestParam(required = false) List<String> states,
                                                @RequestParam(required = false) List<Long> categories,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = TIME_FORMAT) LocalDateTime rangeStart,
                                                @RequestParam(required = false) @DateTimeFormat(pattern = TIME_FORMAT) LocalDateTime rangeEnd,
                                                @RequestParam(required = false, defaultValue = "0") Integer from,
                                                @RequestParam(required = false, defaultValue = "10") Integer size) {
        EventAdminParam eventAdminParam = new EventAdminParam(users, states, categories, rangeStart, rangeEnd, from, size);
        return eventService.findEventsByAdmin(eventAdminParam);
    }

    @PatchMapping(value = "/admin/events/{eventId}")
    public EventFullDto adminUpdateEvent(@PathVariable Long eventId,
                                         @Valid @RequestBody UpdateEventAdminRequest updateRequest) {
        return eventService.adminUpdateEvent(eventId, updateRequest);
    }

    //Public endpoints
    @GetMapping("/events")
    public List<EventShortDto> findEventsByPublic(@RequestParam(required = false) String text,
                                                  @RequestParam(required = false) List<Long> categories,
                                                  @RequestParam(required = false) Boolean paid,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = TIME_FORMAT) LocalDateTime rangeStart,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = TIME_FORMAT) LocalDateTime rangeEnd,
                                                  @RequestParam(required = false) Boolean onlyAvailable,
                                                  @RequestParam(required = false) String sort,
                                                  @RequestParam(required = false, defaultValue = "0") Integer from,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                                  HttpServletRequest request) {
        EventUserParam eventUserParam = new EventUserParam(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size);
        StatisticInDto statisticInDto = new StatisticInDto(SERVICE_ID, request.getRequestURI(), request.getRemoteAddr(),
                LocalDateTime.now());
        statisticClient.postHit(statisticInDto);
        return eventService.findEventsByPublic(eventUserParam, request);
    }

    @GetMapping("/events/{id}")
    public EventFullDto findPublishedEventById(@PathVariable Long id,
                                               HttpServletRequest request) {
        StatisticInDto statisticInDto = new StatisticInDto(SERVICE_ID, request.getRequestURI(), request.getRemoteAddr(),
                LocalDateTime.now());
        statisticClient.postHit(statisticInDto);
        return eventService.findPublishedEventById(id, request);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> findUserEventRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        return eventService.findUserEventRequests(userId, eventId);
    }

    @PatchMapping(value = "/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult changeEventRequestsStatus(@PathVariable Long userId,
                                                                    @PathVariable Long eventId,
                                                                    @Valid @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        return eventService.changeEventRequestsStatus(userId, eventId, updateRequest);
    }
}
