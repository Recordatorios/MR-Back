package com.upao.recordatorios.web.controller;

import com.upao.recordatorios.models.entitys.CronogramaPago;
import com.upao.recordatorios.services.CronogramaPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cronogramas-pago")
@CrossOrigin("*")// Habilitar CORS para este controlador
public class CronogramaPagoController {

    @Autowired
    private CronogramaPagoService cronogramaPagoService;

    @GetMapping
    public ResponseEntity<List<CronogramaPago>> getAllPaymentSchedules() {
        return ResponseEntity.ok(cronogramaPagoService.getAllPaymentSchedules());
    }

    @PostMapping
    public ResponseEntity<CronogramaPago> addPaymentSchedule(@RequestBody CronogramaPago cronogramaPago) {
        return ResponseEntity.ok(cronogramaPagoService.savePaymentSchedule(cronogramaPago));
    }

    @PatchMapping("/{id}/mark-as-paid")
    public ResponseEntity<Void> markAsPaid(@PathVariable Long id) {
        cronogramaPagoService.markAsPaid(id);
        return ResponseEntity.noContent().build();
    }

    // Otros métodos según sea necesario
}
