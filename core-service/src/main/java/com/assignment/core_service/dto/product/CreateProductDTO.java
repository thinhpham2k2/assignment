package com.assignment.core_service.dto.product;

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
public class CreateProductDTO implements Serializable {

    @NotNull(message = "{message.product.name.require}")
    @Size(min = 2, max = 255, message = "{message.product.name.size}")
    private String productName;

    @CategoryConstraint
    @NotNull(message = "{message.category.id.require}")
    private String categoryId;

    @SupplierConstraint
    @NotNull(message = "{message.supplier.id.require}")
    private String supplierId;

    private String image;

    @Range(min = 1, max = 1000000000, message = "{message.invalid.price}")
    private BigDecimal price;

    @Range(min = 0, max = 100, message = "{message.invalid.weight}")
    private BigDecimal weight;

    @Range(min = 0, max = 100000000, message = "{message.invalid.quantity}")
    private Integer quantity;

    @NotNull(message = "{message.description.require}")
    @Size(min = 5, max = 255, message = "{message.product.description.size}")
    private String description;
}
