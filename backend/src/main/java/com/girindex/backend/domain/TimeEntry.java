package com.girindex.backend.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class TimeEntry {
    LocalDateTime timestamp;
    double avgPrice;

    public TimeEntry(LocalDateTime timestamp, double avgPrice) {
        this.timestamp = timestamp;
        this.avgPrice = avgPrice;
    }
}