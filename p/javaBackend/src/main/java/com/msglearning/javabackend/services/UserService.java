package com.msglearning.javabackend.services;

import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.entity.Rating;
import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.errors.ErrorResponse;
import com.msglearning.javabackend.repositories.PurchaseRepository;
import com.msglearning.javabackend.repositories.RatingRepository;
import com.msglearning.javabackend.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PurchaseRepository purchaseRepository;

    private final RatingRepository ratingRepository;

    public UserService(UserRepository userRepository, PurchaseRepository purchaseRepository, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
        this.ratingRepository = ratingRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
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

    public String deleteUser(Long id) {
        // Check if the user exists
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        // Retrieve all purchases related to the user
        List<Purchase> purchases = purchaseRepository.findByUserId(id);
        List<Rating> ratings = ratingRepository.findByUserId(id);

        // Delete all purchases related to the user
        for (Purchase purchase : purchases) {
            purchaseRepository.deleteById(purchase.getId());
        }

        // Delete all ratings related to the user
        for (Rating rating : ratings) {
            ratingRepository.deleteById(rating.getId());
        }

        // Delete the user
        userRepository.deleteById(id);

        // Return success message
        return "User deleted successfully.";
    }

    public ResponseEntity<User> updateUser(Long id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            if(updatedUser.getFirstName() != null)
                existingUser.setFirstName(updatedUser.getFirstName());
            if(updatedUser.getLastName() != null)
                existingUser.setLastName(updatedUser.getLastName());
            if(updatedUser.getEmail() != null)
                existingUser.setEmail(updatedUser.getEmail());

            // Checking if updatedUserPassword is not null and different from userPassword
            if (updatedUser.getPassword() != null && !existingUser.getPassword().equals(updatedUser.getPassword())) {
                try {
                    String hashedPassword = PasswordService.getSaltedHash(updatedUser.getPassword());
                    existingUser.setPassword(hashedPassword);
                } catch (Exception e) {
                    throw new RuntimeException("Error occurred during password hashing.", e);
                }
            }

            if(updatedUser.getGender() != null)
                existingUser.setGender(updatedUser.getGender());
            if(updatedUser.isAdmin() != existingUser.isAdmin())
                existingUser.setAdmin(updatedUser.isAdmin());

            User savedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //TODO
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}



