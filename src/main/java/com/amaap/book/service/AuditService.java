package com.amaap.book.service;

import com.amaap.book.entity.Book;
import com.amaap.book.entity.BookAudit;
import com.amaap.book.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AuditService {

    @Autowired
    AuditLogRepository auditLogRepository;

    public void auditLog(Book book, String name, String operation) {
        BookAudit bookAudit = new BookAudit();
        bookAudit.setBook(book);
        bookAudit.setBookName(name);
        bookAudit.setOperation(operation);
        auditLogRepository.save(bookAudit);
    }
}
