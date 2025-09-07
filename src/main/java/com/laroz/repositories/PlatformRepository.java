package com.laroz.repositories;

import com.laroz.models.Platform;
import com.laroz.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlatformRepository extends JpaRepository<Platform, Long> {
    Page<Platform> findByIsActiveTrue(Pageable page);
    Page<Platform> findByNameContainingIgnoreCase(Pageable page, String name);
}
