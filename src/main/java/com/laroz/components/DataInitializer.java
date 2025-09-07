package com.laroz.components;

import com.laroz.enums.UserRole;
import com.laroz.infra.security.CryptService;
import com.laroz.models.User;
import com.laroz.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CryptService cryptService;
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {  // Se não houver nenhum usuário, cria o administrador
            User admin = new User();
            admin.setName("Administrador");
            admin.setEmail("admin@email.com");
            admin.setPassword(
                    cryptService.encode("Admin3200*")
            );
            admin.setRole(
                    UserRole.ADM
            );
            admin.setActive(true);

            userRepository.save(admin);
            System.out.println("Usuário ADMIN criado com sucesso.");
        } else {
            System.out.println("Usuário ADMIN já existe, nenhuma ação foi tomada.");
        }
    }
}
