package com.assignment.core_service.service;

import com.assignment.core_service.dto.product.CreateProductDTO;
import com.assignment.core_service.dto.product.ProductDTO;
import com.assignment.core_service.dto.product.UpdateProductDTO;
import com.assignment.core_service.entity.Product;
import com.assignment.core_service.mapper.ProductMapper;
import com.assignment.core_service.repository.ProductRepository;
import com.assignment.core_service.service.interfaces.PagingService;
import com.assignment.core_service.service.interfaces.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private final MessageSource messageSource;

    private final ProductMapper productMapper;

    private final PagingService pagingService;

    private final ProductRepository productRepository;

    @Override
    public boolean existsById(long id) {

        return productRepository.existsByIdAndStatus(id, true);
    }

    @Override
    public ProductDTO findById(long id) {

        Optional<Product> product = productRepository.findByIdAndStatus(id, true);

        return product.map(productMapper::entityToDTO).orElse(null);
    }

    @Override
    public PagedModel<ProductDTO> findAllByCondition
            (List<Long> categoryIds, List<Long> supplierIds, String search, String sort, int page, int limit) {

        if (page < 0) throw new InvalidParameterException(
                messageSource.getMessage(Constant.INVALID_PAGE_NUMBER, null, LocaleContextHolder.getLocale()));
        if (limit < 1) throw new InvalidParameterException(
                messageSource.getMessage(Constant.INVALID_PAGE_SIZE, null, LocaleContextHolder.getLocale()));

        List<Sort.Order> order = new ArrayList<>();

        Set<String> sourceFieldList = pagingService.getAllFields(Product.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertyPresent(sourceFieldList, subSort[0])) {

            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), transferProperty(subSort[0])));
        } else {

            throw new InvalidParameterException("{" + subSort[0] + "} " +
                    messageSource.getMessage(Constant.INVALID_PROPERTY, null, LocaleContextHolder.getLocale()));
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Product> pageResult = productRepository.findAllByCondition(
                true, categoryIds, supplierIds, search, pageable);

        return new PagedModel<>(pageResult.map(productMapper::entityToDTO));
    }

    @Override
    public void create(CreateProductDTO create) {

        try {

            Product product = productMapper.createToEntity(create);
            productRepository.save(product);
        } catch (Exception e) {

            throw new InvalidParameterException(
                    messageSource.getMessage(Constant.CREATE_FAIL, null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void update(UpdateProductDTO update, long id) {

        Optional<Product> product = productRepository.findByIdAndStatus(id, true);
        if (product.isPresent()) {

            try {

                productRepository.save(productMapper.updateToEntity(update, product.get()));
            } catch (Exception e) {

                throw new InvalidParameterException(
                        messageSource.getMessage(Constant.UPDATE_FAIL, null, LocaleContextHolder.getLocale()));
            }
        } else {

            throw new InvalidParameterException(
                    messageSource.getMessage(Constant.INVALID_PRODUCT, null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void delete(long id) {

        Optional<Product> product = productRepository.findByIdAndStatus(id, true);
        if (product.isPresent()) {

            try {

                product.get().setStatus(false);
                productRepository.save(product.get());
            } catch (Exception e) {

                throw new InvalidParameterException(
                        messageSource.getMessage(Constant.DELETE_FAIL, null, LocaleContextHolder.getLocale()));
            }
        } else {

            throw new InvalidParameterException(
                    messageSource.getMessage(Constant.INVALID_PRODUCT, null, LocaleContextHolder.getLocale()));
        }
    }

    private String transferProperty(String property) {

        return switch (property) {
            case "category" -> "category.category";
            case "supplier" -> "supplier.supplierName";
            default -> property;
        };
    }
}
