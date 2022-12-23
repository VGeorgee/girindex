package com.girindex.backend.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;


class TimeEntry {
    LocalDateTime timestamp;
    double avgPrice;

    public TimeEntry(LocalDateTime timestamp, double avgPrice) {
        this.timestamp = timestamp;
        this.avgPrice = avgPrice;
    }
}