package com.assignment.core_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
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
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {

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

    @CreatedDate
    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(name = "date_updated", insertable = false)
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

    @CreatedBy
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private Account creator;

    @LastModifiedBy
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "updated_by", insertable = false)
    private Account updater;
}
