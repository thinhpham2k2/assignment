package com.assignment.core_service.service;

import com.assignment.core_service.dto.category.CategoryDTO;
import com.assignment.core_service.dto.category.CreateCategoryDTO;
import com.assignment.core_service.dto.category.UpdateCategoryDTO;
import com.assignment.core_service.entity.Category;
import com.assignment.core_service.mapper.CategoryMapper;
import com.assignment.core_service.repository.CategoryRepository;
import com.assignment.core_service.service.interfaces.CategoryService;
import com.assignment.core_service.service.interfaces.PagingService;
import com.assignment.core_service.util.Constant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final MessageSource messageSource;

    private final CategoryMapper categoryMapper;

    private final PagingService pagingService;

    private final CategoryRepository categoryRepository;

    @Override
    public boolean existsById(long id) {

        return categoryRepository.existsByIdAndStatus(id, true);
    }

    @Override
    public CategoryDTO findById(long id) {

        Optional<Category> category = categoryRepository.findByIdAndStatus(id, true);

        return category.map(categoryMapper::entityToDTO).orElse(null);
    }

    @Override
    public PagedModel<CategoryDTO> findAllByCondition(String search, String sort, int page, int limit) {

        if (page < 0) throw new InvalidParameterException(
                messageSource.getMessage(Constant.INVALID_PAGE_NUMBER, null, LocaleContextHolder.getLocale()));
        if (limit < 1) throw new InvalidParameterException(
                messageSource.getMessage(Constant.INVALID_PAGE_SIZE, null, LocaleContextHolder.getLocale()));

        List<Sort.Order> order = new ArrayList<>();

        Set<String> sourceFieldList = pagingService.getAllFields(Category.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertyPresent(sourceFieldList, subSort[0])) {

            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), subSort[0]));
        } else {

            throw new InvalidParameterException("{" + subSort[0] + "} " +
                    messageSource.getMessage(Constant.INVALID_PROPERTY, null, LocaleContextHolder.getLocale()));
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Category> pageResult = categoryRepository.findAllByCondition(true, search, pageable);

        return new PagedModel<>(pageResult.map(categoryMapper::entityToDTO));
    }

    @Override
    public void create(CreateCategoryDTO create) {

        try {

            categoryRepository.save(categoryMapper.createToEntity(create));
        } catch (Exception e) {

            throw new InvalidParameterException(
                    messageSource.getMessage(Constant.CREATE_FAIL, null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void update(UpdateCategoryDTO update, long id) {

        Optional<Category> category = categoryRepository.findByIdAndStatus(id, true);
        if (category.isPresent()) {

            try {

                categoryRepository.save(categoryMapper.updateToEntity(update, category.get()));
            } catch (Exception e) {

                throw new InvalidParameterException(
                        messageSource.getMessage(Constant.UPDATE_FAIL, null, LocaleContextHolder.getLocale()));
            }
        } else {

            throw new InvalidParameterException(
                    messageSource.getMessage(Constant.INVALID_CATEGORY, null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void delete(long id) {

        Optional<Category> category = categoryRepository.findByIdAndStatus(id, true);
        if (category.isPresent()) {

            try {

                category.get().setStatus(false);
                categoryRepository.save(category.get());
            } catch (Exception e) {

                throw new InvalidParameterException(
                        messageSource.getMessage(Constant.DELETE_FAIL, null, LocaleContextHolder.getLocale()));
            }
        } else {

            throw new InvalidParameterException(
                    messageSource.getMessage(Constant.INVALID_CATEGORY, null, LocaleContextHolder.getLocale()));
        }
    }
}
