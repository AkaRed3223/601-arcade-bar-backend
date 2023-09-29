package com.arcade.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @Column(name = "customer", nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @Column(name = "total", nullable = false)
    private Double total = 0.0;

    @Column(name = "is_open", nullable = false)
    private Boolean isOpen = true;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "operation_id", nullable = false)
    private Long operationId;

    @Column(name = "created_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Tab(Long externalId, @NonNull String name, Long operationId) {
        this.operationId = operationId;
        this.externalId = externalId;
        this.name = name;
    }
}
