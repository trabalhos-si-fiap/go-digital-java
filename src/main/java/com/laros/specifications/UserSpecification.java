package com.laros.specifications;

import com.laros.models.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    // Filtro por nome (contém a substring ignorando maiúsculas e minúsculas)
    public static Specification<User> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    // Filtro por e-mail (contém a substring ignorando maiúsculas e minúsculas)
    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    // Filtro para verificar se o usuário está ativo
    public static Specification<User> isActive() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("isActive"));
    }
}