package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.UserInDto;
import ru.practicum.ewm.user.dto.UserOutDto;
import ru.practicum.ewm.user.model.User;

import java.util.List;

public interface UserService {

    List<UserOutDto> findUsers(List<Long> ids, Integer from, Integer size);

    UserOutDto addUser(UserInDto inDto);

    void deleteUser(Long userId);

    User findUserById(Long userId);

}
