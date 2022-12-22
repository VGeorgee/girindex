package com.girindex.backend.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class FoodDataRepository implements PanacheRepository<FoodDataEntity> {

    @PersistenceContext
    EntityManager entityManager;

    public FoodDataEntity findMostRecentByPlace(String place) {
        TypedQuery<FoodDataEntity> query = entityManager.createQuery(
                "SELECT fd FROM FoodDataEntity fd WHERE fd.place = :place ORDER BY fd.timestamp DESC", FoodDataEntity.class);
        query.setParameter("place", place);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

}
