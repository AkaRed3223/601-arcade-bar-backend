package com.arcade.model;

import com.arcade.model.request.PaymentRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@Entity(name = "Payment")
@Table(name = "payment")
public class Payment {

    @Id
    @SequenceGenerator(name = "payment_generator", sequenceName = "payment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_generator")
    @Column(name = "id", updatable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "tab_id", nullable = false)
    private Long tabId;

    @Column(name = "tab_external_id", nullable = false)
    private Long tabExternalId;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "time", nullable = false)
    @Setter(AccessLevel.NONE)
    private String time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    public Payment(Tab tab, PaymentRequest request) {
        this.tabId = tab.getId();
        this.tabExternalId = tab.getExternalId();
        this.value = request.getValue();
        this.name = request.getName();
        this.details = request.getDetails();
    }
}