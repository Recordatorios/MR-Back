package com.upao.recordatorios.services;

import com.upao.recordatorios.models.entitys.Deuda;
import java.util.List;
import java.util.Optional;

public interface DeudaService {
    List<Deuda> getAllDebts(Long userId);
    Deuda saveDebt(Deuda deuda, Long userId);
    void markAsPaid(Long deudaId);
    void deleteDebt(Long deudaId);
    List<Deuda> getDebtsByMonthAndYear(int month, int year, Long userId);
    List<Deuda> getDebtsByNumeroDocumento(String numeroDocumento, Long userId);
    List<Deuda> getDebtsDueToday(Long userId);
}
