package com.girindex.backend.domain;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FoodData {

    private String name;
    private String place;
    private String location;
    private Integer price;
    private LocalDateTime timestamp;

}
