package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.services.PurchaseService;
import com.msglearning.javabackend.to.PurchaseRequest;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({ ControllerConstants.API_PATH_PURCHASE })
public class PurchaseController {
    private static final String ID_PATH = "/{id}";
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService){
        this.purchaseService = purchaseService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Purchase>> findAllPurchases() {
        return ResponseEntity.ok(purchaseService.findAllPurchases());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Purchase>> findPurchasesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(purchaseService.findPurchasesByUserId(userId));
    }

    @PostMapping()
    public ResponseEntity<List<Purchase>> createPurchase(@RequestBody PurchaseRequest request) {
        return ResponseEntity.ok(purchaseService.createPurchase(request));
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity<String> deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.ok("Purchase with ID " + id + " has been deleted.");
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<String> updatePurchase(@PathVariable Long id, @RequestBody Purchase purchase) throws NotFoundException {
        purchaseService.updatePurchase(id, purchase);
        return ResponseEntity.ok("Purchase with ID " + id + " has been updated.");
    }
}

