package com.upao.recordatorios.services.impl;

import com.upao.recordatorios.models.entitys.CronogramaPago;
import com.upao.recordatorios.infra.repository.CronogramaPagoRepository;
import com.upao.recordatorios.services.CronogramaPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CronogramaPagoServiceImpl implements CronogramaPagoService {

    @Autowired
    private CronogramaPagoRepository cronogramaPagoRepository;

    @Override
    public List<CronogramaPago> getAllPaymentSchedules() {
        return cronogramaPagoRepository.findAll();
    }

    @Override
    public CronogramaPago savePaymentSchedule(CronogramaPago cronogramaPago) {
        return cronogramaPagoRepository.save(cronogramaPago);
    }

    @Override
    public void markAsPaid(Long paymentScheduleId) {
        CronogramaPago cronogramaPago = cronogramaPagoRepository.findById(paymentScheduleId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        cronogramaPago.setPagado(true);
        cronogramaPagoRepository.save(cronogramaPago);
    }
}
