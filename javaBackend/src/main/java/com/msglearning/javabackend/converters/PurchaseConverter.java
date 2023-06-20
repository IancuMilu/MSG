package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.Game;
import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.to.GameNoPriceTO;
import com.msglearning.javabackend.to.GameTO;
import com.msglearning.javabackend.to.PurchaseTO;
import lombok.Builder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Builder
public class PurchaseConverter {
    private final GameConverter gameConverter;

    public Purchase convertToEntity(PurchaseTO purchaseTO) {
        Purchase purchase = new Purchase();
        GameNoPriceTO gameTO = purchaseTO.getGame();
        Game game = gameConverter.convertNoPriceTOToEntity(gameTO);
        purchase.setGame(game);
        purchase.setPurchaseDate(purchaseTO.getPurchaseDate());
        return purchase;
    }

    public PurchaseTO convertToTO(Purchase purchase) {
        Game game = purchase.getGame();
        GameNoPriceTO gameNoPriceTO = gameConverter.convertToNoPriceTO(game);

        return PurchaseTO.builder()
                .game(gameNoPriceTO)
                .purchaseDate(purchase.getPurchaseDate())
                .build();
    }

    public List<PurchaseTO> convertToTOList(List<Purchase> purchases) {
        return purchases.stream()
                .map(this::convertToTO)
                .collect(Collectors.toList());
    }
}


