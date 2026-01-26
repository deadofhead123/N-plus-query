package com.sweet.n_plus_one_query.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "audit_log")
@AttributeOverride(name = "id", column = @Column(nullable = false))
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogEntity extends BaseEntity {
    @Lob
    @Column(name = "message")
    private String message;
}