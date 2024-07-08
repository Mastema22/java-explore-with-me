package ru.practicum.ewm.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.category.service.CategoryService;
import ru.practicum.ewm.exception.CategoryNotFoundException;
import ru.practicum.ewm.exception.DataValidationException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        log.info("Добавление новой категории.");
        Category category = CategoryMapper.toCategory(newCategoryDto);
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void removeCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new DataValidationException("Не существует категории с выбранным id");
        }
        categoryRepository.deleteById(categoryId);
        log.info("Категория с id {} удалена", categoryId);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Категории с выбранным id не существует"));
        category.setName(categoryDto.getName());
        log.info("Обновление категории с id {}", categoryId);
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> findCategories(Integer from, Integer size) {
        log.info("Выполняется запрос на получение категорий");
        Pageable pageable = PageRequest.of(from / size, size);
        List<Category> categories = categoryRepository.findAll(pageable).getContent();
        return CategoryMapper.toDtos(categories);
    }

    @Override
    public CategoryDto findCategoryById(Long categoryId) {
        log.info("Выполняется поиск категории по id {}", categoryId);
        Category category = findCategory(categoryId);
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public Category findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Категории с выбранным id не существует"));
    }
}
