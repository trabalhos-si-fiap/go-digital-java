package com.laroz.repositories;

import com.laroz.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findAllByIsActiveTrue(Pageable page);
}
