package com.upao.recordatorios.models.entitys;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroDocumento;
    private String empresa;
    private Double montoTotal;
    private LocalDate fechaVencimiento;
    private LocalDate fechaCreacion;
    private String estado; // 'pendiente', 'pagada', 'vencida'

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deuda")
    private List<CronogramaPago> cronogramaPagos;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDate.now();
    }
}