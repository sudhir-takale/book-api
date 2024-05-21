package com.amaap.book.repository;

import com.amaap.book.entity.BookAudit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuditLogRepository extends MongoRepository<BookAudit, Integer> {
}
