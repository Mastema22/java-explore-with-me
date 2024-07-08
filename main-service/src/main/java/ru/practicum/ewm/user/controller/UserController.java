package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.UserInDto;
import ru.practicum.ewm.user.dto.UserOutDto;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    //Admin endpoints
    @GetMapping("/admin/users")
    public List<UserOutDto> findUsers(@RequestParam(required = false) List<Long> ids,
                                      @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                      @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        return userService.findUsers(ids, from, size);
    }

    @DeleteMapping(value = "/admin/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@NotNull @PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @PostMapping(value = "/admin/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutDto addUser(@Valid @RequestBody UserInDto inDto) {
        return userService.addUser(inDto);
    }
}