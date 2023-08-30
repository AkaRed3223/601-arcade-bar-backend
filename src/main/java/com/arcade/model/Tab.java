package com.arcade.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "externalId", nullable = false, unique = true)
    private Long externalId;

    @Column(name = "customer", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;

    @Column(name = "total", nullable = false)
    private Double total;

    public Tab(Long externalId, String name, List<Product> products) {
        this.externalId = externalId;
        this.name = name;
        this.products = products;
        this.total = products.stream().mapToDouble(Product::getPrice).sum();
    }
}
