package com.upao.recordatorios.infra.repository;

import com.upao.recordatorios.models.entitys.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeudaRepository extends JpaRepository<Deuda, Long> {

    @Query("SELECT d FROM Deuda d WHERE FUNCTION('MONTH', d.fechaVencimiento) = :month AND FUNCTION('YEAR', d.fechaVencimiento) = :year AND d.user.id = :userId ORDER BY d.fechaVencimiento ASC")
    List<Deuda> findByFechaVencimientoMonthAndYearAndUserId(int month, int year, Long userId);

    @Query("SELECT d FROM Deuda d WHERE d.user.id = :userId ORDER BY d.fechaVencimiento ASC")
    List<Deuda> findByUserId(Long userId);

    @Query("SELECT d FROM Deuda d WHERE d.user.id = :userId AND d.numeroDocumento = :numeroDocumento ORDER BY d.fechaVencimiento ASC")
    List<Deuda> findByNumeroDocumentoAndUserId(String numeroDocumento, Long userId);

    // Nuevo método para encontrar deuda por número de documento
    Optional<Deuda> findByNumeroDocumento(String numeroDocumento);
}
