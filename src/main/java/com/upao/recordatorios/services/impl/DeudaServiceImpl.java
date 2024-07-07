package com.upao.recordatorios.services.impl;

import com.upao.recordatorios.models.entitys.Deuda;
import com.upao.recordatorios.models.entitys.User;
import com.upao.recordatorios.infra.repository.DeudaRepository;
import com.upao.recordatorios.infra.repository.UserRepository;
import com.upao.recordatorios.services.DeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeudaServiceImpl implements DeudaService {

    @Autowired
    private DeudaRepository deudaRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Deuda> getAllDebts(Long userId) {
        return deudaRepository.findByUserId(userId);
    }

    @Override
    public Deuda saveDebt(Deuda deuda, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        deuda.setUser(user);

        LocalDate today = LocalDate.now();
        LocalDate dueDate = deuda.getFechaVencimiento();

        if (dueDate.isEqual(today) || (dueDate.isAfter(today) && dueDate.isBefore(today.plusDays(7)))) {
            deuda.setEstado("proxima");
        } else if (dueDate.isBefore(today)) {
            deuda.setEstado("vencida");
        } else {
            deuda.setEstado("pendiente");
        }

        return deudaRepository.save(deuda);
    }

    @Override
    public void markAsPaid(Long deudaId) {
        Deuda deuda = deudaRepository.findById(deudaId)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada"));
        deuda.setEstado("pagada");
        deudaRepository.save(deuda);
    }

    @Override
    public void deleteDebt(Long deudaId) {
        Deuda deuda = deudaRepository.findById(deudaId)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada"));
        deudaRepository.delete(deuda);
    }

    @Override
    public List<Deuda> getDebtsByMonthAndYear(int month, int year, Long userId) {
        return deudaRepository.findByFechaVencimientoMonthAndYearAndUserId(month, year, userId);
    }

    @Override
    public List<Deuda> getDebtsByNumeroDocumento(String numeroDocumento, Long userId) {
        return deudaRepository.findByNumeroDocumentoAndUserId(numeroDocumento, userId);
    }
}
