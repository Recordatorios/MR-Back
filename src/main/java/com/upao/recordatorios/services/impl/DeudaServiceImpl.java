package com.upao.recordatorios.services.impl;

import com.upao.recordatorios.models.entitys.Deuda;
import com.upao.recordatorios.models.entitys.User;
import com.upao.recordatorios.infra.repository.DeudaRepository;
import com.upao.recordatorios.infra.repository.UserRepository;
import com.upao.recordatorios.services.DeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
    public Map<String, Object> saveDebt(Deuda deuda, Long userId) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        deuda.setUser(user);

        // Verificar si ya existe una deuda con el mismo número de documento para este usuario
        Optional<Deuda> existingDebt = deudaRepository.findByNumeroDocumentoAndUserId(deuda.getNumeroDocumento(), userId);
        if (existingDebt.isPresent()) {
            response.put("error", "Ya existe una deuda con ese número de documento para este usuario.");
            return response;
        }

        LocalDate today = LocalDate.now();
        LocalDate dueDate = deuda.getFechaVencimiento();

        // Verificar si la fecha de vencimiento es anterior a la fecha actual
        if (dueDate.isBefore(today)) {
            response.put("error", "La fecha de vencimiento no puede ser anterior a la fecha actual.");
            return response;
        }

        // Configurar el estado de la deuda en función de la fecha de vencimiento
        if (dueDate.isEqual(today) || (dueDate.isAfter(today) && dueDate.isBefore(today.plusDays(7)))) {
            deuda.setEstado("proxima");
        } else {
            deuda.setEstado("pendiente");
        }

        // Guardar la deuda y devolver en la respuesta
        Deuda savedDebt = deudaRepository.save(deuda);
        response.put("deuda", savedDebt);
        return response;
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
        return deudaRepository.findByNumeroDocumentoAndUserId(numeroDocumento, userId)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Deuda> getDebtsDueToday(Long userId) {
        LocalDate today = LocalDate.now();
        return deudaRepository.findByFechaVencimientoToday(today, userId)
                .stream()
                .filter(deuda -> !deuda.getEstado().equals("pagada"))
                .collect(Collectors.toList());
    }
}
