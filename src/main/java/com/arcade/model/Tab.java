package com.arcade.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "Tab")
@Table(name = "tab")
public class Tab {

    @Id
    @SequenceGenerator(name = "tab_sequence", sequenceName = "tab_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tab_sequence")
    @Column(name = "id", updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "external_id", nullable = false, unique = true)
    private Long externalId;

    @Column(name = "customer", nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @Column(name = "total", nullable = false)
    private Double total = 0.0;

    public Tab(Long externalId, @NonNull String name) {
        this.externalId = externalId;
        this.name = name;
    }
}
