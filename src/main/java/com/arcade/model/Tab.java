package com.arcade.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "Tab")
@Table(name = "tab")
public class Tab {

    @Id
    @SequenceGenerator(name = "tab_generator", sequenceName = "tab_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tab_generator")
    @Column(name = "id", updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "external_id", nullable = false)
    private Long externalId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @Column(name = "total_due", nullable = false)
    private Double totalDue = 0.0;

    @Column(name = "total_paid", nullable = false)
    private Double totalPaid = 0.0;

    @Column(name = "is_open", nullable = false)
    private Boolean isOpen = true;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "operation_id", nullable = false)
    private Long operationId;

    @Column(name = "created_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private String createdAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    @Column(name = "updated_at")
    private String updatedAt = "";

    @OneToMany(cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    public Tab(Long externalId, @NonNull String name, @NonNull String phone, Long operationId) {
        this.externalId = externalId;
        this.name = name;
        this.phone = phone;
        this.operationId = operationId;
    }
}
