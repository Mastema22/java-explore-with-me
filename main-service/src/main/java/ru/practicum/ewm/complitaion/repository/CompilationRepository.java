package ru.practicum.ewm.complitaion.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.complitaion.model.Compilation;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    List<Compilation> findAllByIsPinned(Boolean isPinned, Pageable pageable);
}