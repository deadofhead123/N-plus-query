package com.sweet.n_plus_one_query.repository;

import com.sweet.n_plus_one_query.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long> {
}
