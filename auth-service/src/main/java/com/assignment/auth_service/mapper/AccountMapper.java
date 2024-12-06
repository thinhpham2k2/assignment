package com.assignment.auth_service.mapper;

import com.assignment.auth_service.dto.account.AccountDTO;
import com.assignment.auth_service.dto.account.CreateAccountDTO;
import com.assignment.auth_service.entity.Account;
import com.assignment.auth_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDTO entityToDTO(Account entity);

    @Mapping(target = "role", expression = "java(mapRole())")
    @Mapping(target = "state", expression = "java(true)")
    @Mapping(target = "status", expression = "java(true)")
    Account createToEntity(CreateAccountDTO create);

    @Named("mapRole")
    default Role mapRole(){

        return Role.CUSTOMER;
    }
}
