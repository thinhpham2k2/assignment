package com.assignment.core_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Supplier")
@Table(name = "tbl_supplier")
@EntityListeners(AuditingEntityListener.class)
public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Nationalized
    @Column(name = "supplier_name")
    private String supplierName;

    @CreatedDate
    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(name = "date_updated", insertable = false)
    private LocalDateTime dateUpdated;

    @Lob
    @Nationalized
    @Column(name = "description")
    private String description;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Product> productList;
}
