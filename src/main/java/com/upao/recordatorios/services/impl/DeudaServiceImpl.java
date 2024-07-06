package com.upao.recordatorios.services.impl;

import com.upao.recordatorios.models.entitys.Deuda;
import com.upao.recordatorios.infra.repository.DeudaRepository;
import com.upao.recordatorios.services.DeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeudaServiceImpl implements DeudaService {

    @Autowired
    private DeudaRepository deudaRepository;

    @Override
    public List<Deuda> getAllDebts() {
        return deudaRepository.findAll();
    }

    @Override
    public Deuda saveDebt(Deuda deuda) {
        return deudaRepository.save(deuda);
    }

    @Override
    public void markAsPaid(Long deudaId) {
        Deuda deuda = deudaRepository.findById(deudaId)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada"));
        deuda.setEstado("pagada");
        deudaRepository.save(deuda);
    }
}
