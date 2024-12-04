package com.assignment.core_service.enitity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Product")
@Table(name = "tbl_product",
        indexes = {
                @Index(name = "idx_product_name_description", columnList = "product_name, description")
        })
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Nationalized
    @Column(name = "product_name")
    private String productName;

    @Lob
    @Nationalized
    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "weight")
    private BigDecimal weight;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    @Nationalized
    @Column(name = "description")
    private String description;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "created_by")
    private Account creator;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "updated_by")
    private Account updater;
}
