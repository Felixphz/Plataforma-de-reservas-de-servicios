package com.udea.service_platform.modules.services.repository;

import com.udea.service_platform.modules.services.model.Service;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ServiceSpecification {

    private ServiceSpecification() {
    }

    public static Specification<Service> buildFilter(Long providerId, String category, Double maxPrice) {
        return (root, query, cb) -> {
            var predicates = new java.util.ArrayList<jakarta.persistence.criteria.Predicate>();

            predicates.add(cb.equal(root.get("active"), true));

            if (providerId != null) {
                predicates.add(cb.equal(root.get("idProveedor"), providerId));
            }

            if (category != null && !category.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("categoria")), category.toLowerCase()));
            }

            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("precio"), BigDecimal.valueOf(maxPrice)));
            }

            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}
