package com.upao.recordatorios.infra.repository;

import com.upao.recordatorios.models.entitys.CronogramaPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CronogramaPagoRepository extends JpaRepository<CronogramaPago, Long> {
}
