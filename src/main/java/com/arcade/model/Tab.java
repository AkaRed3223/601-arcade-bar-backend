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

    @Column(name = "customer", nullable = false)
    private String customer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> products;

    @Column(name = "total", nullable = false)
    private double total;

    public Tab(String customer, List<Product> products) {
        this.customer = customer;
        this.products = products;
        this.total = products.stream().mapToDouble(Product::getPrice).sum();
    }
}
