package com.girindex.backend.domain;

import com.girindex.backend.persistence.FoodDataEntity;
import com.girindex.backend.persistence.FoodDataRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
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
        List<TimeEntry> timeSeries = new ArrayList<>();
        List<FoodData> allData = this.getAll();
        for (FoodData current : allData) {
            List<FoodData> otherEntries = this.findAllBeforeTimestamp(current.getTimestamp(), current.getPlace());
            int totalPrice = current.getPrice();
            for (FoodData other : otherEntries) {
                totalPrice += other.getPrice();
            }
            int averagePrice = totalPrice / (otherEntries.size() + 1);
            timeSeries.add(new TimeEntry(current.getTimestamp(), averagePrice));
        }
        return timeSeries;
    }

    public List<FoodData> findAllBeforeTimestamp(LocalDateTime timestamp, String place) {
        // Read all data from the database
        List<FoodData> allData = getAll();

        // Sort the data by timestamp in reverse order
        allData.sort((a, b) -> -a.getTimestamp().compareTo(b.getTimestamp()));

        // Create a set of distinct places from the data
        Set<String> places = allData.stream().map(FoodData::getPlace).collect(Collectors.toSet());

        // Initialize the result list
        List<FoodData> result = new ArrayList<>();

        // Iterate over the set of places
        for (String currentPlace : places) {
            // Skip the current place if it's the same as the parameter
            if (currentPlace.equals(place)) {
                continue;
            }

            // Find the first entry in the sorted list with the same place as the current place
            // and with a timestamp less than the parameter
            Optional<FoodData> entry = allData.stream()
                    .filter(data -> data.getPlace().equals(currentPlace))
                    .filter(data -> data.getTimestamp().isBefore(timestamp))
                    .findFirst();

            // If an entry was found, add it to the result list
            entry.ifPresent(result::add);
        }

        return result;
    }


}
