package com.upao.recordatorios.infra.repository;

import com.upao.recordatorios.models.entitys.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeudaRepository extends JpaRepository<Deuda, Long> {
}
