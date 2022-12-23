package com.girindex.backend.persistence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDataEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String place;
    private String location;
    private Integer price;
    private LocalDateTime timestamp;

}
