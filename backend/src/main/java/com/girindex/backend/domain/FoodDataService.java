package com.girindex.backend.domain;

import com.girindex.backend.persistence.FoodDataEntity;
import com.girindex.backend.persistence.FoodDataRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

    @Transactional
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


    public Map<String, FoodData> getLatestForPlacesUntil(LocalDateTime until) {
        Map<String, FoodDataEntity> latestForPlaces = repository.getLatestForPlacesUntil(until);
        return latestForPlaces.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> mapper.toDomain(e.getValue())));
    }

    @Transactional
    public List<TimeEntry> getTimeSeries() {
        List<FoodData> data = this.getAll();

        return data.stream()
                .collect(Collectors.groupingBy(FoodData::getTimestamp))
                .entrySet().stream()
                .map(entry -> {
                    double avgPrice = entry.getValue().stream()
                            .mapToInt(FoodData::getPrice)
                            .average()
                            .orElse(0);
                    return new TimeEntry(entry.getKey(), avgPrice);
                })
                .sorted((e1, e2) -> e1.timestamp.compareTo(e2.timestamp))
                .collect(Collectors.toList());
    }

}
