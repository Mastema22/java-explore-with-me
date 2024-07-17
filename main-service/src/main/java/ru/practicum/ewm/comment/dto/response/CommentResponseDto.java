package ru.practicum.ewm.comment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static ru.practicum.ewm.constant.Constant.TIME_FORMAT;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    @NotNull
    private Long id;
    @NotBlank
    @Length(max = 250, min = 1)
    private String text;
    @NotNull
    private String author;
    @NotNull
    @FutureOrPresent
    @DateTimeFormat(pattern = TIME_FORMAT)
    private String created;
}