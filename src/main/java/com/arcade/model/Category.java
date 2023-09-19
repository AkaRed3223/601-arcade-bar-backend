package com.arcade.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity(name = "Category")
@Table(name = "category")
public class Category {

    @Id
    @SequenceGenerator(name = "category_generator", sequenceName = "category_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
    @Column(name = "id", updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "position", unique = true, nullable = false)
    private Integer position = 0;

    public Category(String name, Integer position) {
        this.name = name;
        this.position = position;
    }
}