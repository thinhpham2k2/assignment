package com.assignment.core_service.service.interfaces;

import com.assignment.core_service.dto.product.CreateProductDTO;
import com.assignment.core_service.dto.product.ProductDTO;
import com.assignment.core_service.dto.product.UpdateProductDTO;
import org.springframework.data.web.PagedModel;

import java.util.List;

public interface ProductService {

    boolean existsById(long id);

    ProductDTO findById(long id);

    PagedModel<ProductDTO> findAllByCondition(List<Long> categoryIds, List<Long> supplierIds,
                                              String search, String sort, int page, int limit);

    void create(CreateProductDTO create);

    void update(UpdateProductDTO update, long id);

    void delete(long id);
}
