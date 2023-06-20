package com.msglearning.javabackend.services;

import com.msglearning.javabackend.converters.PurchaseConverter;
import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.repositories.PurchaseRepository;
import com.msglearning.javabackend.to.PurchaseTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseConverter purchaseConverter;

    public PurchaseService(PurchaseRepository purchaseRepository, PurchaseConverter purchaseConverter) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseConverter = purchaseConverter;
    }

    public List<PurchaseTO> getPurchasesByUser(Long userId) {
        List<Purchase> purchases = purchaseRepository.findByUserId(userId);
        return purchaseConverter.convertToTOList(purchases);
    }

    public ResponseEntity<PurchaseTO> getPurchaseById(Long id) {
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(id);
        if (optionalPurchase.isPresent()) {
            Purchase purchase = optionalPurchase.get();
            PurchaseTO purchaseTO = purchaseConverter.convertToTO(purchase);
            return ResponseEntity.ok(purchaseTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<PurchaseTO> savePurchase(PurchaseTO purchaseTO) {
        Purchase purchase = purchaseConverter.convertToEntity(purchaseTO);
        Purchase savedPurchase = purchaseRepository.save(purchase);
        PurchaseTO savedPurchaseTO = purchaseConverter.convertToTO(savedPurchase);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPurchaseTO);
    }

    public ResponseEntity<PurchaseTO> updatePurchase(Long id, PurchaseTO purchaseTO) {
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(id);
        if (optionalPurchase.isPresent()) {
            Purchase existingPurchase = optionalPurchase.get();
            // Update purchase attributes
            existingPurchase.setPurchaseDate(purchaseTO.getPurchaseDate());
            Purchase updatedPurchase = purchaseRepository.save(existingPurchase);
            PurchaseTO updatedPurchaseTO = purchaseConverter.convertToTO(updatedPurchase);
            return ResponseEntity.ok(updatedPurchaseTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void deletePurchase(Long id) {
        purchaseRepository.deleteById(id);
    }
}
