package com.sweet.n_plus_one_query.service.impl;

import com.sweet.n_plus_one_query.entity.AuditLogEntity;
import com.sweet.n_plus_one_query.repository.AuditLogRepository;
import com.sweet.n_plus_one_query.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {
    private final AuditLogRepository auditLogRepository;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void createAudit(String message) {
        auditLogRepository.save(new AuditLogEntity(message));
    }
}
