package com.upao.recordatorios.web.controller;

import com.upao.recordatorios.models.entitys.Deuda;
import com.upao.recordatorios.services.DeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deudas")
@CrossOrigin(origins = "*")
public class DeudaController {

    @Autowired
    private DeudaService deudaService;

    @GetMapping
    public ResponseEntity<List<Deuda>> getAllDebts() {
        return ResponseEntity.ok(deudaService.getAllDebts());
    }

    @PostMapping
    public ResponseEntity<Deuda> addDebt(@RequestBody Deuda deuda) {
        return ResponseEntity.ok(deudaService.saveDebt(deuda));
    }

    @PatchMapping("/{id}/mark-as-paid")
    public ResponseEntity<Void> markAsPaid(@PathVariable Long id) {
        deudaService.markAsPaid(id);
        return ResponseEntity.noContent().build();
    }

}
