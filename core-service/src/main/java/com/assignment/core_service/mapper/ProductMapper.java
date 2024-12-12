package com.assignment.core_service.mapper;

import com.assignment.core_service.dto.product.CreateProductDTO;
import com.assignment.core_service.dto.product.ProductDTO;
import com.assignment.core_service.dto.product.UpdateProductDTO;
import com.assignment.core_service.entity.Category;
import com.assignment.core_service.entity.Product;
import com.assignment.core_service.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.categoryName")
    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "supplierName", source = "supplier.supplierName")
    @Mapping(target = "creatorId", source = "creator.id")
    @Mapping(target = "creatorEmail", source = "creator.email")
    @Mapping(target = "updaterId", source = "updater.id")
    @Mapping(target = "updaterEmail", source = "updater.email")
    ProductDTO entityToDTO(Product entity);

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategory")
    @Mapping(target = "supplier", source = "supplierId", qualifiedByName = "mapSupplier")
    @Mapping(target = "state", expression = "java(true)")
    @Mapping(target = "status", expression = "java(true)")
    Product createToEntity(CreateProductDTO create);

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategory")
    @Mapping(target = "supplier", source = "supplierId", qualifiedByName = "mapSupplier")
    @Mapping(target = "state", expression = "java(true)")
    @Mapping(target = "status", expression = "java(true)")
    Product updateToEntity(UpdateProductDTO update, @MappingTarget Product entity);

    @Named("mapCategory")
    default Category mapCategory(Long id) {

        Category category = new Category();
        category.setId(id);
        return category;
    }

    @Named("mapSupplier")
    default Supplier mapSupplier(Long id) {

        Supplier supplier = new Supplier();
        supplier.setId(id);
        return supplier;
    }
}
