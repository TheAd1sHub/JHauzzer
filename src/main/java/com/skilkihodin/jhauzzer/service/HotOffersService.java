package com.skilkihodin.jhauzzer.service;

import com.skilkihodin.jhauzzer.model.warehouses.StorageEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

// @Service
public final class HotOffersService {

    @Autowired
    private ProductsService productsService;

    //region 15% mandatory discount for all users every Friday, 18:00 - 22:00
    private final Float fridayDiscountPerCent = 15.0f;

    @Scheduled(cron = "0 18 * * 5 *")
    public void startOffer() {
        
    }

    @Scheduled(cron = "0 22 * * 5 *")
    public void endOffer() {
        // offer ends
    }
    //endregion

    private void applyDiscount(StorageEntry product, float discountPercents) {

    }

    private void deapplyDiscount(StorageEntry product, float discountPercents) {

    }

}
