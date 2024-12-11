package com.assignment.core_service.mapper;

import com.assignment.core_service.dto.account.AccountDTO;
import com.assignment.core_service.dto.account.CreateAccountDTO;
import com.assignment.core_service.dto.account.UpdateAccountDTO;
import com.assignment.core_service.entity.Account;
import com.assignment.core_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "userName", source = "username")
    AccountDTO entityToDTO(Account entity);

    @Mapping(target = "productCreatedList", ignore = true)
    @Mapping(target = "productUpdatedList", ignore = true)
    @Mapping(target = "role", expression = "java(mapRole())")
    @Mapping(target = "state", expression = "java(true)")
    @Mapping(target = "status", expression = "java(true)")
    Account createToEntity(CreateAccountDTO create);

    @Mapping(target = "productCreatedList", ignore = true)
    @Mapping(target = "productUpdatedList", ignore = true)
    Account updateToEntity(UpdateAccountDTO update, @MappingTarget Account entity);

    @Named("mapRole")
    default Role mapRole(){

        return Role.CUSTOMER;
    }
}
