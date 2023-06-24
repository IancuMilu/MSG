package com.msglearning.javabackend.services;

import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.errors.ErrorResponse;
import com.msglearning.javabackend.repositories.PurchaseRepository;
import com.msglearning.javabackend.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    public UserService(UserRepository userRepository, PurchaseRepository purchaseRepository) {

        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
            return userRepository.findAll();
    }

    public ResponseEntity<?> createUser(User user) {
        if (user.getEmail() == null || user.getPassword() == null || user.getFirstName() == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Required fields are missing."));
        }

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("User with the same email already exists."));
        }

        try {
            String hashedPassword = PasswordService.getSaltedHash(user.getPassword());
            user.setPassword(hashedPassword);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Error occurred during password hashing."));
        }

        User createdUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    public void deleteUser(Long id) {
        // Retrieve all purchases related to the user
        List<Purchase> purchases = purchaseRepository.findByUserId(id);

        // Delete all purchases related to the user
        for (Purchase purchase : purchases) {
            purchaseRepository.deleteById(purchase.getId());
        }

        // Delete the user
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}



