package com.udea.service_platform.modules.users.repository;

import com.udea.service_platform.modules.users.model.User;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {

    private ClientSpecification() {
    }

    public static Specification<User> buildFilter(String searchTerm) {
        return (root, query, cb) -> {
            var predicates = new java.util.ArrayList<jakarta.persistence.criteria.Predicate>();

            predicates.add(cb.equal(root.get("role").get("nombre"), "Cliente"));

            if (searchTerm != null && !searchTerm.isBlank()) {
                String pattern = "%" + searchTerm.toLowerCase() + "%";
                var searchPredicate = cb.or(
                        cb.like(cb.lower(root.get("nombre")), pattern),
                        cb.like(cb.lower(root.get("apellido")), pattern),
                        cb.like(cb.lower(root.get("correo")), pattern),
                        cb.like(root.get("telefono"), pattern)
                );
                predicates.add(searchPredicate);
            }

            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };
    }
}
