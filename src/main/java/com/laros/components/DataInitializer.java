package com.laros.components;

import com.laros.enums.UserRole;
import com.laros.infra.security.CryptService;
import com.laros.models.User;
import com.laros.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CryptService cryptService;
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setName("ADMIN");
            admin.setEmail("admin@laros.com");
            admin.setPhone("21999999999");
            admin.setPassword(
                    cryptService.encode("Laros1234*")
            );
            admin.setRole(
                    UserRole.ADM
            );
            admin.setActive(true);

            userRepository.save(admin);
            log.debug("Usuário ADMIN criado com sucesso.");
        } else {
            log.debug("Usuário ADMIN já existe, nenhuma ação foi tomada.");
        }
    }
}
