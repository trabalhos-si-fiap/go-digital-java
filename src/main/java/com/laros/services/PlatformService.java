package com.laros.services;

import com.laros.dtos.platform.CreatePlatform;
import com.laros.dtos.platform.UpdatePlatform;
import com.laros.models.Platform;
import com.laros.repositories.PlatformRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class PlatformService {
    @Autowired
    private PlatformRepository platformRepository;

    public Platform create(
            CreatePlatform createPlatafom
    ) {
        return platformRepository.save(
                        new Platform(createPlatafom)
        );
    }

    public Page<Platform> list(Pageable page, String name) {
        if (name != null) {
            return platformRepository.findByNameContainingIgnoreCase(page, name);
        }
        return platformRepository.findByIsActiveTrue(page);
    }


    public Platform update(
            Long id,
            UpdatePlatform updatePlatform
    ) {

        Platform platform = platformRepository.getReferenceById(id);
        platform.update(updatePlatform);

        return platformRepository.save(platform);
    }

    public void delete(Long id) {
        Platform platform = platformRepository.getReferenceById(id);
        platform.delete();
        platformRepository.save(platform);
    }

    public Platform getById(Long id) {
        return platformRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
