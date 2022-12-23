package com.girindex.backend.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TimeEntry {
    LocalDateTime timestamp;
    double avgPrice;

    public TimeEntry(LocalDateTime timestamp, double avgPrice) {
        this.timestamp = timestamp;
        this.avgPrice = avgPrice;
    }
}