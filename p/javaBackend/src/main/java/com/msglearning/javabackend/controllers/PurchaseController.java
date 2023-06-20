package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.services.PurchaseService;
import com.msglearning.javabackend.services.TokenService;
import com.msglearning.javabackend.services.UserService;
import com.msglearning.javabackend.to.PurchaseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({ ControllerConstants.API_PATH_PURCHASE })
public class PurchaseController {
    private static final String ID_PATH = "/{id}";

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/all")
    public List<PurchaseTO> getAllPurchases(){
       return purchaseService.getAllPurchases();
    }

    @GetMapping("/user/{userId}")
    public List<PurchaseTO> getPurchasesByUser(@PathVariable Long userId) {
        return purchaseService.getPurchasesByUser(userId);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<Purchase> getPurchaseById(@PathVariable Long id) {
        return purchaseService.getPurchaseById(id);
    }

    @PostMapping
    public ResponseEntity<Purchase> createPurchase(@RequestBody Purchase purchase) {
        return purchaseService.savePurchase(purchase);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<Purchase> updatePurchase(@PathVariable Long id, @RequestBody Purchase purchase) {
        return purchaseService.updatePurchase(id, purchase);
    }

    @DeleteMapping(ID_PATH)
    public void deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
    }
}

