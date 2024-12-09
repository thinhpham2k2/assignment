package com.assignment.core_service.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Serializable {

    private Long id;
    private String productName;
    private String categoryId;
    private String categoryName;
    private String supplierId;
    private String supplierName;
    private String creatorId;
    private String creatorName;
    private String updaterId;
    private String updaterName;
    private String image;
    private BigDecimal price;
    private BigDecimal weight;
    private Integer quantity;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private String description;
    private Boolean state;
    private Boolean status;
}
