package com.arcade.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "Operation")
@Table(name = "operation")
public class Operation {

    @Id
    @SequenceGenerator(name = "operation_generator", sequenceName = "operation_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_generator")
    @Column(name = "id", updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "operation_start_date", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime startDate = LocalDateTime.now();

    @Column(name = "operation_end_date")
    private LocalDateTime endDate;

    @Column(name = "is_open", nullable = false)
    private Boolean isOpen = true;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Tab> tabs = new ArrayList<>();

    public void closeOperation() {
        this.isOpen = false;
        this.endDate = LocalDateTime.now();
    }
}