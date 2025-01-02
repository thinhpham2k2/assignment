package com.assignment.auth_service.mapper;

import com.assignment.auth_service.dto.account.AccountDTO;
import com.assignment.auth_service.dto.account.CreateAccountDTO;
import com.assignment.auth_service.dto.account.UpdateAccountDTO;
import com.assignment.auth_service.entity.Account;
import com.assignment.auth_service.entity.Role;
import com.assignment.common_library.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "userName", source = "username")
    AccountDTO entityToDTO(Account entity);

    @Mapping(target = "role", expression = "java(mapRole())")
    @Mapping(target = "state", expression = "java(true)")
    @Mapping(target = "status", expression = "java(true)")
    Account createToEntity(CreateAccountDTO create);

    Account updateToEntity(UpdateAccountDTO update, @MappingTarget Account entity);

    @Mapping(target = "role", expression = "java(mapRole(map.get(\"role\")))")
    @Mapping(target = "phone", expression = "java((String) map.get(\"phone\"))")
    @Mapping(target = "email", expression = "java((String) map.get(\"email\"))")
    @Mapping(target = "state", expression = "java((Boolean) map.get(\"state\"))")
    UpdateAccountDTO objectToUpdate(Map<?, ?> map);

    @Mapping(target = "isExist", expression = "java(true)")
    AccountResponse entityToResponse(Account entity);

    @Named("mapRole")
    default Role mapRole() {

        return Role.CUSTOMER;
    }

    @Named("mapRole")
    default Role mapRole(Object object) {

        return Role.valueOf((String) object);
    }
}
