package ru.practicum.ewm.complitaion.service;

import ru.practicum.ewm.complitaion.dto.CompilationDto;
import ru.practicum.ewm.complitaion.dto.NewCompilationDto;
import ru.practicum.ewm.complitaion.dto.UpdateCompilationRequest;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> findCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto findCompilationById(Long compId);

    CompilationDto addCompilation(NewCompilationDto compilationDto);

    void deleteCompilation(Long compId);

    CompilationDto updateCompilation(Long compId, UpdateCompilationRequest update);
}
