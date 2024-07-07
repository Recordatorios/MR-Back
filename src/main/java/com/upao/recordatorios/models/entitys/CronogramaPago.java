package com.upao.recordatorios.models.entitys;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class CronogramaPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fechaPago;
    private Double monto;
    private Boolean pagado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deuda_id")
    private Deuda deuda;
}
