package com.laroz.services;

import com.laroz.dtos.user.CreateUser;
import com.laroz.dtos.user.UpdateUser;
import com.laroz.dtos.user.UserResponse;
import com.laroz.models.User;
import com.laroz.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<UserResponse> listAll() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .toList();
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