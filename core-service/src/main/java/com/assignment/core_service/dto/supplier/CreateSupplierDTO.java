package com.assignment.core_service.dto.supplier;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSupplierDTO implements Serializable {

    @NotNull(message = "{message.supplier.name.require}")
    @Size(min = 2, max = 255, message = "{message.supplier.name.size}")
    private String supplierName;

    @NotNull(message = "{message.description.require}")
    @Size(min = 5, max = 4000, message = "{message.description.size}")
    private String description;
}
