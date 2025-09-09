package com.laros.services;

import com.laros.dtos.user.CreateUser;
import com.laros.dtos.user.UpdateUser;
import com.laros.dtos.user.UserResponse;
import com.laros.models.User;
import com.laros.repositories.UserRepository;
import com.laros.specifications.UserSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse create(CreateUser data) {
        User user = new User(data);
        user.setPassword(passwordEncoder.encode(data.password()));

        return new UserResponse(userRepository.save(user));
    }

    public List<UserResponse> createBatch(){
        return new ArrayList<>();
    }

    public Page<UserResponse> list(Pageable page, String name, String email) {
        Specification<User> spec = Specification.where(UserSpecification.isActive());

        if (name != null && !name.isEmpty()) {
            spec = spec.and(UserSpecification.hasName(name));
        }

        if (email != null && !email.isEmpty()) {
            spec = spec.and(UserSpecification.hasEmail(email));
        }

        return userRepository.findAll(spec, page).map(UserResponse::new);
    }

    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return new UserResponse(user);
    }

    public UserResponse update(Long id, UpdateUser data) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.update(data);

        return new UserResponse(userRepository.save(user));
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        var user = userRepository.getReferenceById(id);
        user.delete();
        userRepository.save(user);
    }
}