package com.assignment.core_service.enitity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Account")
@Table(name = "tbl_account", uniqueConstraints = {@UniqueConstraint(name = "user_name_unique", columnNames = "user_name")})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "role")
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    @Column(name = "user_name", updatable = false, length = 50)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 320)
    private String email;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    @Column(name = "state")
    private Boolean state;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Product> productCreatedList;

    @OneToMany(mappedBy = "updater", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Product> productUpdatedList;
}