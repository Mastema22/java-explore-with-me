package ru.practicum.ewm.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOutDto {

    private Long id;
    private String email;
    private String name;
}