package com.assignment.core_service.service;

import com.assignment.core_service.dto.supplier.CreateSupplierDTO;
import com.assignment.core_service.dto.supplier.SupplierDTO;
import com.assignment.core_service.dto.supplier.UpdateSupplierDTO;
import com.assignment.core_service.entity.Supplier;
import com.assignment.core_service.mapper.SupplierMapper;
import com.assignment.core_service.repository.SupplierRepository;
import com.assignment.core_service.service.interfaces.PagingService;
import com.assignment.core_service.service.interfaces.SupplierService;
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
public class SupplierServiceImpl implements SupplierService {

    private final MessageSource messageSource;

    private final SupplierMapper supplierMapper;

    private final PagingService pagingService;

    private final SupplierRepository supplierRepository;

    @Override
    public boolean existsById(long id) {

        return supplierRepository.existsByIdAndStatus(id, true);
    }

    @Override
    public SupplierDTO findById(long id) {

        Optional<Supplier> Supplier = supplierRepository.findByIdAndStatus(id, true);

        return Supplier.map(supplierMapper::entityToDTO).orElse(null);
    }

    @Override
    public PagedModel<SupplierDTO> findAllByCondition(String search, String sort, int page, int limit) {

        if (page < 0) throw new InvalidParameterException(
                messageSource.getMessage(Constant.INVALID_PAGE_NUMBER, null, LocaleContextHolder.getLocale()));
        if (limit < 1) throw new InvalidParameterException(
                messageSource.getMessage(Constant.INVALID_PAGE_SIZE, null, LocaleContextHolder.getLocale()));

        List<Sort.Order> order = new ArrayList<>();

        Set<String> sourceFieldList = pagingService.getAllFields(Supplier.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertyPresent(sourceFieldList, subSort[0])) {

            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), subSort[0]));
        } else {

            throw new InvalidParameterException("{" + subSort[0] + "} " +
                    messageSource.getMessage(Constant.INVALID_PROPERTY, null, LocaleContextHolder.getLocale()));
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Supplier> pageResult = supplierRepository.findAllByCondition(true, search, pageable);

        return new PagedModel<>(pageResult.map(supplierMapper::entityToDTO));
    }

    @Override
    public void create(CreateSupplierDTO create) {

        try {

            supplierRepository.save(supplierMapper.createToEntity(create));
        } catch (Exception e) {

            throw new InvalidParameterException(
                    messageSource.getMessage(Constant.CREATE_FAIL, null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void update(UpdateSupplierDTO update, long id) {

        Optional<Supplier> supplier = supplierRepository.findByIdAndStatus(id, true);
        if (supplier.isPresent()) {

            try {

                supplierRepository.save(supplierMapper.updateToEntity(update, supplier.get()));
            } catch (Exception e) {

                throw new InvalidParameterException(
                        messageSource.getMessage(Constant.UPDATE_FAIL, null, LocaleContextHolder.getLocale()));
            }
        } else {

            throw new InvalidParameterException(
                    messageSource.getMessage(Constant.INVALID_SUPPLIER, null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void delete(long id) {

        Optional<Supplier> supplier = supplierRepository.findByIdAndStatus(id, true);
        if (supplier.isPresent()) {

            try {

                supplier.get().setStatus(false);
                supplierRepository.save(supplier.get());
            } catch (Exception e) {

                throw new InvalidParameterException(
                        messageSource.getMessage(Constant.DELETE_FAIL, null, LocaleContextHolder.getLocale()));
            }
        } else {

            throw new InvalidParameterException(
                    messageSource.getMessage(Constant.INVALID_SUPPLIER, null, LocaleContextHolder.getLocale()));
        }
    }
}
