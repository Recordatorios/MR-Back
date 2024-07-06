package com.upao.recordatorios.services;

import com.upao.recordatorios.models.entitys.Deuda;
import java.util.List;

public interface DeudaService {
    List<Deuda> getAllDebts();
    Deuda saveDebt(Deuda deuda);
    void markAsPaid(Long deudaId);
}
