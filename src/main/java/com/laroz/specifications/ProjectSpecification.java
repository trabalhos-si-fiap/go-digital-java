package com.laroz.specifications;

import com.laroz.models.Client;
import com.laroz.models.Project;
import com.laroz.models.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ProjectSpecification {

    // Filtro por nome: LIKE (busca parcial, ignorando maiúsculas)
    public static Specification<Project> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    // Filtro por descrição: LIKE (busca parcial, ignorando maiúsculas)
    public static Specification<Project> hasDescription(String description) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }

    // Filtro por lista de IDs de clientes
    public static Specification<Project> hasClients(List<Long> clientsIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Project, Client> clientsJoin = root.join("clients");
            return clientsJoin.get("id").in(clientsIds);
        };
    }

    // Filtro por lista de IDs de membros da equipe
    public static Specification<Project> hasTeamMembers(List<Long> membersIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Project, User> membersJoin = root.join("teamMembers");
            return membersJoin.get("id").in(membersIds);
        };
    }

    // Filtro para Gerente (Manager)
    public static Specification<Project> hasManager(Long managerId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("manager").get("id"), managerId);
    }

    // Filtro por data de início
    public static Specification<Project> hasStartDate(LocalDateTime startDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDate);
    }

    // Filtro por data de fim
    public static Specification<Project> hasEndDate(LocalDateTime endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDate);
    }

    // Filtro por status ativo
    public static Specification<Project> isActive() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isTrue(root.get("isActive"));
    }
}