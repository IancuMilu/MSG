package com.msglearning.javabackend.converters;

import com.msglearning.javabackend.entity.Purchase;
import com.msglearning.javabackend.to.PurchaseTO;

public class PurchaseConverter {

    public static PurchaseTO toPurchaseTO(Purchase purchase){

        return new PurchaseTO(purchase.getId(), purchase.getGame().getName(),
                purchase.getUser().getFullName(), purchase.getPurchaseDate());
    }
}
