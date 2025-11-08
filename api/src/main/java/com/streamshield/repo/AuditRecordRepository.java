package com.streamshield.repo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRecordRepository extends JpaRepository<AuditRecordEntity, Long> {}
