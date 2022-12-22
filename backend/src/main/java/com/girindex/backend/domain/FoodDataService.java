package com.girindex.backend.domain;

import com.girindex.backend.persistence.FoodDataEntity;
import com.girindex.backend.persistence.FoodDataRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class FoodDataService {

    @Inject
    FoodDataRepository repository;

    @Inject
    FoodDataMapper mapper;

    public List<FoodData> getAll() {
        return repository.listAll(Sort.by("timestamp").descending())
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    public FoodData getById(Long id) {
        return mapper.toDomain(repository.findById(id));
    }

    public FoodData create(FoodData foodData) {
        FoodDataEntity entity = mapper.toEntity(foodData);
        repository.persist(entity);
        return mapper.toDomain(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }


    public Optional<FoodData> getMostRecentForPlace(String place) {
        return repository.getMostRecentForPlace(place).map(mapper::toDomain);
    }

}
