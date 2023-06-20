package com.msglearning.javabackend.controllers;

import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.services.PurchaseService;
import com.msglearning.javabackend.to.PurchaseTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ControllerConstants.API_PATH_PURCHASE)
public class PurchaseController {
    private static final String ID_PATH = "/{id}";
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/user/{userId}")
    public List<PurchaseTO> getPurchasesByUser(@PathVariable Long userId) {
        return purchaseService.getPurchasesByUser(userId);
    }

    @GetMapping(ID_PATH)
    public ResponseEntity<PurchaseTO> getPurchaseById(@PathVariable Long id) {
        return purchaseService.getPurchaseById(id);
    }

    @PostMapping
    public ResponseEntity<PurchaseTO> createPurchase(@RequestBody PurchaseTO purchaseTO) {
        return purchaseService.savePurchase(purchaseTO);
    }

    @PutMapping(ID_PATH)
    public ResponseEntity<PurchaseTO> updatePurchase(@PathVariable Long id, @RequestBody PurchaseTO purchaseTO) {
        return purchaseService.updatePurchase(id, purchaseTO);
    }

    @DeleteMapping(ID_PATH)
    public void deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
    }
}

