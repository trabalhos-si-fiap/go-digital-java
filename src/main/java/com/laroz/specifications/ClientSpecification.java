package com.laroz.specifications;

import com.laroz.models.Client;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {

    // Filtro por nome
    public static Specification<Client> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    // Filtro por e-mail
    public static Specification<Client> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
    }

    // Filtro por Instagram
    public static Specification<Client> hasInstagram(String instagram) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("instagram")), "%" + instagram.toLowerCase() + "%");
    }

    // Filtro por telefone
    public static Specification<Client> hasPhone(String phone) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("phone")), "%" + phone.toLowerCase() + "%");
    }

    // Filtro para clientes ativos
    public static Specification<Client> isActive() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("isActive"));
    }
}
