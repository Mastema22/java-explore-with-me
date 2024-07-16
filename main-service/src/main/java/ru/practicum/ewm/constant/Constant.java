package ru.practicum.ewm.constant;

import org.springframework.data.domain.Sort;

import java.time.format.DateTimeFormatter;

public class Constant {
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    public static final String SERVICE_ID = "ewm-main-service";
    public static final String EVENT_URI = "/events/";
    public static final Integer PAGE_SIZE = 10;
    public static final Sort SORT_BY_ID_ASC = Sort.by(Sort.Direction.ASC, "id");
}
