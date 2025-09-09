package com.laros.repositories;

import com.laros.models.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    Page<Platform> findByIsActiveTrue(Pageable page);
    Page<Platform> findByNameContainingIgnoreCase(Pageable page, String name);
}
