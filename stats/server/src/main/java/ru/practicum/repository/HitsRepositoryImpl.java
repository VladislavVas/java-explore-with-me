package ru.practicum.repository;

import lombok.AllArgsConstructor;
import ru.practicum.dto.ViewStats;
import ru.practicum.model.EndpointHit;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class HitsRepositoryImpl implements HitsRepository {

    private final EntityManager entityManager;

    @Override
    public List<ViewStats> getHits(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ViewStats> query = builder.createQuery(ViewStats.class);
        Root<EndpointHit> root = query.from(EndpointHit.class);
        List<Predicate> predicateList = new ArrayList<>();

        query.select(builder.construct(
                ViewStats.class,
                root.get("uri"),
                root.get("app"),
                unique
                        ? builder.countDistinct(root.get("ip"))
                        : builder.count(root.get("ip")))
        );
        query.groupBy(
                root.get("app"),
                root.get("uri"),
                root.get("ip")
        );
        query.orderBy(builder.desc(
                unique
                        ? builder.countDistinct(root.get("ip"))
                        : builder.count(root.get("ip")))
        );
        predicateList.add(builder.between(root.get("timestamp"), start, end));

        if (uris != null)
            predicateList.add(builder.and(root.get("uri").in(uris)));

        query.where(predicateList.toArray(Predicate[]::new));

        return entityManager.createQuery(query).getResultList();
    }

}
