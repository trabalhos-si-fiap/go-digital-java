package com.laroz.services;

import com.laroz.dtos.platform.CreatePlatform;
import com.laroz.dtos.platform.UpdatePlatform;
import com.laroz.models.Platform;
import com.laroz.repositories.PlatformRepository;
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
            CreatePlatform createPlatafom,
            Authentication authentication
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
            UpdatePlatform updatePlatform,
            Authentication authentication
    ) {

        Platform platform = platformRepository.getReferenceById(updatePlatform.id());
        platform.update(updatePlatform);

        return platformRepository.save(platform);
    }

    public void delete(Long id) {
        Platform platform = platformRepository.getReferenceById(id);
        platform.delete();
        platformRepository.save(platform);
    }
}
