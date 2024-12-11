package com.assignment.core_service.dto.supplier;

import com.assignment.core_service.util.Constant;
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

    @NotNull(message = "{" + Constant.SUPPLIER_NAME_REQUIRE + "}")
    @Size(min = 2, max = 255, message = "{" + Constant.SUPPLIER_NAME_SIZE + "}")
    private String supplierName;

    @NotNull(message = "{" + Constant.DESCRIPTION_REQUIRE + "}")
    @Size(min = 5, max = 4000, message = "{" + Constant.DESCRIPTION_SIZE + "}")
    private String description;
}
