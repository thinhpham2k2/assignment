package com.assignment.core_service.service.interfaces;

import com.assignment.core_service.dto.response.PagedDTO;
import com.assignment.core_service.dto.supplier.CreateSupplierDTO;
import com.assignment.core_service.dto.supplier.SupplierDTO;
import com.assignment.core_service.dto.supplier.UpdateSupplierDTO;

public interface SupplierService {

    boolean existsById(long id);

    SupplierDTO findById(long id);

    PagedDTO<SupplierDTO> findAllByCondition(String search, String sort, int page, int limit);

    void create(CreateSupplierDTO create);

    void update(UpdateSupplierDTO update, long id);

    void delete(long id);
}
