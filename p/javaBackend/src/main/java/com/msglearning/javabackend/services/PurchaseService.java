package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.PurchaseConverter;
import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.repositories.GameRepository;
import com.msglearning.javabackend.repositories.PurchaseRepository;
import com.msglearning.javabackend.repositories.UserRepository;
import com.msglearning.javabackend.to.PurchaseRequest;
import com.msglearning.javabackend.to.PurchaseTO;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final PurchaseRepository purchaseRepository;

    public PurchaseService(UserRepository userRepository, GameRepository gameRepository, PurchaseRepository purchaseRepository){
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public List<Purchase> findAllPurchases() {
        return purchaseRepository.findAll();
    }

    public List<Purchase> findPurchasesByUserId(Long userId) {
        return purchaseRepository.findByUserId(userId);
    }

    public List<Purchase> createPurchase(PurchaseRequest request) {
        Long userId = request.getUserId();
        List<Long> gameIds = request.getGameIds();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (gameIds == null || gameIds.isEmpty()) {
            throw new IllegalArgumentException("No game IDs provided.");
        }

        List<Game> games = gameRepository.findAllById(gameIds);

        List<Purchase> purchases = new ArrayList<>();
        // Create a new purchase for each game and save it to the Purchase repository
        games.forEach(game -> {
            Purchase purchase = Purchase.builder()
                    .user(user)
                    .game(game)
                    .purchaseDate(LocalDate.now())
                    .build();
            purchases.add(purchaseRepository.save(purchase));
        });

        // Returns the created purchases
        return purchases;
    }

    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }


    public void updatePurchase(Long id, Purchase purchase) throws NotFoundException {
        Purchase existingPurchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Purchase not found with ID: " + id));

        // Update the fields of the existing purchase with the new data from the request payload
        existingPurchase.setUser(purchase.getUser());
        existingPurchase.setGame(purchase.getGame());
        existingPurchase.setPurchaseDate(purchase.getPurchaseDate());
        // Update other fields as needed

        purchaseRepository.save(existingPurchase);
    }
}
