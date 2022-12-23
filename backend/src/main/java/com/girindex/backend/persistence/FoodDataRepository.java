package com.girindex.backend.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class FoodDataRepository implements PanacheRepository<FoodDataEntity> {

    @PersistenceContext
    EntityManager entityManager;

    public Optional<FoodDataEntity> getMostRecentForPlace(String place) {
        TypedQuery<FoodDataEntity> query = entityManager.createQuery("SELECT fd FROM FoodDataEntity fd WHERE fd.place = :place ORDER BY fd.timestamp DESC", FoodDataEntity.class);
        query.setParameter("place", place);
        query.setMaxResults(1);
        List<FoodDataEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(resultList.get(0));
        }
    }

    public Map<String, FoodDataEntity> getLatestForPlacesUntil(LocalDateTime until) {
        TypedQuery<Object[]> query = entityManager.createQuery("SELECT fd.place, MAX(fd.timestamp) FROM FoodDataEntity fd WHERE fd.timestamp <= :until GROUP BY fd.place", Object[].class);
        query.setParameter("until", until);
        List<Object[]> resultList = query.getResultList();
        Map<String, FoodDataEntity> latestForPlaces = new HashMap<>();
        resultList.forEach(arr -> {
            String place = (String) arr[0];
            LocalDateTime timestamp = (LocalDateTime) arr[1];
            TypedQuery<FoodDataEntity> innerQuery = entityManager.createQuery("SELECT fd FROM FoodDataEntity fd WHERE fd.place = :place AND fd.timestamp = :timestamp", FoodDataEntity.class);
            innerQuery.setParameter("place", place);
            innerQuery.setParameter("timestamp", timestamp);
            latestForPlaces.put(place, innerQuery.getSingleResult());
        });
        return latestForPlaces;
    }
}
