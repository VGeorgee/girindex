package com.girindex.backend.persistence;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class FoodDataEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String place;
    private String location;
    private String price;
    private LocalDateTime timestamp;

}
