package com.msglearning.javabackend.services;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.entity.User;
import com.msglearning.javabackend.repositories.GameRepository;
import com.msglearning.javabackend.repositories.PurchaseRepository;
import com.msglearning.javabackend.repositories.UserRepository;
import com.msglearning.javabackend.to.PurchaseRequest;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        if (purchase.getUser() != null)
            existingPurchase.setUser(purchase.getUser());
        if (purchase.getGame() != null)
            existingPurchase.setGame(purchase.getGame());
        if (purchase.getPurchaseDate() != null)
            existingPurchase.setPurchaseDate(purchase.getPurchaseDate());

        purchaseRepository.save(existingPurchase);
    }
}
