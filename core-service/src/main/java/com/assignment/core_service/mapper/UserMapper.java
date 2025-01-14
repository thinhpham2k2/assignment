package com.assignment.core_service.mapper;

import com.assignment.common_library.AccountResponse;
import com.assignment.core_service.dto.user.CustomUserDetails;
import com.assignment.core_service.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", source = "role", qualifiedByName = "mapRole")
    CustomUserDetails responseToUserDetail(AccountResponse response);

    @Named("mapRole")
    default Role mapRole(Object object) {

        return Role.valueOf((String) object);
    }
}
