package com.girindex.backend.resource;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class FoodDataCreationRequest {

    @NotBlank
    private String place;

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotNull
    @Positive
    private Double price;
}
