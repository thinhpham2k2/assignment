package com.assignment.core_service.mapper;

import com.assignment.core_service.dto.supplier.CreateSupplierDTO;
import com.assignment.core_service.dto.supplier.SupplierDTO;
import com.assignment.core_service.dto.supplier.UpdateSupplierDTO;
import com.assignment.core_service.entity.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    SupplierDTO entityToDTO(Supplier entity);

    @Mapping(target = "productList", ignore = true)
    @Mapping(target = "state", expression = "java(true)")
    @Mapping(target = "status", expression = "java(true)")
    Supplier createToEntity(CreateSupplierDTO create);

    @Mapping(target = "productList", ignore = true)
    Supplier updateToEntity(UpdateSupplierDTO update, @MappingTarget Supplier entity);
}
