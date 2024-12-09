package com.assignment.core_service.service;

import com.assignment.core_service.repository.SupplierRepository;
import com.assignment.core_service.service.interfaces.SupplierService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public boolean existsById(long id) {

        return supplierRepository.existsByIdAndStatus(id, true);
    }
}
