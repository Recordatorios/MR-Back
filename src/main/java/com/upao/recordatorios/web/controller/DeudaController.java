package com.upao.recordatorios.web.controller;

import com.upao.recordatorios.models.entitys.Deuda;
import com.upao.recordatorios.models.entitys.User;
import com.upao.recordatorios.infra.repository.UserRepository;
import com.upao.recordatorios.services.DeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/deudas")
@CrossOrigin(origins = "*")
public class DeudaController {

    @Autowired
    private DeudaService deudaService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Deuda>> getAllDebts(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        return ResponseEntity.ok(deudaService.getAllDebts(userId));
    }

    @PostMapping
    public ResponseEntity<Deuda> addDebt(@RequestBody Deuda deuda, Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        return ResponseEntity.ok(deudaService.saveDebt(deuda, userId));
    }

    @PatchMapping("/{id}/mark-as-paid")
    public ResponseEntity<Void> markAsPaid(@PathVariable Long id) {
        deudaService.markAsPaid(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDebt(@PathVariable Long id) {
        deudaService.deleteDebt(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/month/{month}/year/{year}")
    public ResponseEntity<List<Deuda>> getDebtsByMonthAndYear(@PathVariable int month, @PathVariable int year, Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        return ResponseEntity.ok(deudaService.getDebtsByMonthAndYear(month, year, userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Deuda>> searchByNumeroDocumento(@RequestParam String numeroDocumento, Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        return ResponseEntity.ok(deudaService.getDebtsByNumeroDocumento(numeroDocumento, userId));
    }

    @GetMapping("/alertDueToday")
    public ResponseEntity<String> alertDueToday(Principal principal) {
        Long userId = getUserIdFromPrincipal(principal);
        List<Deuda> deudasHoy = deudaService.getDebtsDueToday(userId);
        if (deudasHoy.isEmpty()) {
            return ResponseEntity.ok("No tienes deudas que vencen hoy");
        } else {
            return ResponseEntity.ok("Tienes deudas que vencen hoy");
        }
    }

    private Long getUserIdFromPrincipal(Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return user.getId();
    }
}
