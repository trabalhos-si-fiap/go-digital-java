package com.laros.repositories;

import com.laros.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);

    Page<User> findByIsActiveTrue(Pageable page);

    Page<User> findByNameContainingIgnoreCase(Pageable page, String name);

    Page<User> findByEmailContainingIgnoreCase(Pageable page, String email);
}
