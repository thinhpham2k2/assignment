package com.assignment.core_service.dto.product;

import com.assignment.core_service.util.Constant;
import com.assignment.core_service.validation.interfaces.CategoryConstraint;
import com.assignment.core_service.validation.interfaces.SupplierConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDTO implements Serializable {

    @NotNull(message = "{" + Constant.PRODUCT_NAME_REQUIRE + "}")
    @Size(min = 2, max = 255, message = "{" + Constant.PRODUCT_NAME_SIZE + "}")
    private String productName;

    @CategoryConstraint
    @NotNull(message = "{" + Constant.CATEGORY_ID_REQUIRE + "}")
    private String categoryId;

    @SupplierConstraint
    @NotNull(message = "{" + Constant.SUPPLIER_ID_REQUIRE + "}")
    private String supplierId;

    private String image;

    @Range(min = 1, max = 1000000000, message = "{" + Constant.INVALID_PRICE + "}")
    private BigDecimal price;

    @Range(min = 0, max = 100, message = "{" + Constant.INVALID_WEIGHT + "}")
    private BigDecimal weight;

    @Range(min = 0, max = 100000000, message = "{" + Constant.INVALID_QUANTITY + "}")
    private Integer quantity;

    @NotNull(message = "{" + Constant.DESCRIPTION_REQUIRE + "}")
    @Size(min = 5, max = 255, message = "{" + Constant.PRODUCT_DESCRIPTION_SIZE + "}")
    private String description;

    private Boolean state;
}
