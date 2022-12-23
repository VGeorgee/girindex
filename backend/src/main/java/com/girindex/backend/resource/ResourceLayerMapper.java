package com.girindex.backend.resource;

import com.girindex.backend.domain.FoodData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ResourceLayerMapper {
    FoodData toDomain(FoodDataCreationRequest resource);
    FoodDataCreationRequest toResource(FoodData domain);
}