package com.upao.recordatorios.models.entitys;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
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
    private String fechaVencimiento;
    private String estado; // 'pendiente', 'pagada', 'vencida'

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "deuda")
    private List<CronogramaPago> cronogramaPagos;
}
