package com.girindex.backend.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
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

}
