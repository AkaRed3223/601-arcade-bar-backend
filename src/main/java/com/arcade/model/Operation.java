package com.arcade.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "Operation")
@Table(name = "operation")
@NoArgsConstructor
public class Operation {

    @Id
    @SequenceGenerator(name = "operation_sequence", sequenceName = "operation_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_sequence")
    @Column(name = "id", updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "operation_start_date", nullable = false)
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