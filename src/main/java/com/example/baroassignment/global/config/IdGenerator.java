package com.example.baroassignment.global.config;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
    private long nextId = 1L;
    public synchronized long generateId() {
        return nextId++;
    }
}
