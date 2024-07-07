package com.upao.recordatorios.services;

import com.upao.recordatorios.models.entitys.CronogramaPago;
import java.util.List;

public interface CronogramaPagoService {
    List<CronogramaPago> getAllPaymentSchedules();
    CronogramaPago savePaymentSchedule(CronogramaPago cronogramaPago);
    void markAsPaid(Long paymentScheduleId);
}
