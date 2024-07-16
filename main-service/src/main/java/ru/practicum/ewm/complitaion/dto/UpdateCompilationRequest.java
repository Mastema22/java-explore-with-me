package ru.practicum.ewm.complitaion.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompilationRequest {

    private List<Long> events;
    private Boolean pinned;
    @Size(min = 1, max = 50, message = "Длина должна быть от 1 до 50 символов")
    private String title;
}