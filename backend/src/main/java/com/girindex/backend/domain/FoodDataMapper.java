package com.girindex.backend.domain;

import com.girindex.backend.persistence.FoodDataEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "cdi")
public interface FoodDataMapper {

    FoodDataEntity toEntity(FoodData domain);

    FoodData toDomain(FoodDataEntity entity);

}
