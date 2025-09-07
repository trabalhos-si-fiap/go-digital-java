package com.laroz.repositories;

import aj.org.objectweb.asm.commons.Remapper;
import com.laroz.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientRepository extends
        JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {}
