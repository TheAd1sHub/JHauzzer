package com.skilkihodin.jhauzzer.service;

import com.skilkihodin.jhauzzer.model.warehouses.StorageEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public final class ExpiredProductsDisposeService {

    @Autowired
    private ProductsService productsService;

    //@Scheduled(cron = "*/30 * * * * * ")
    public void disposeTest() {
        disposeExpiredProducts();
    }

    // Every day at 00:00
    @Scheduled(cron = "0 0 * * * *")
    public void disposeExpiredProducts() {
        productsService
                .getAll()
                .stream()
                .filter(ExpiredProductsDisposeService::isExpired)
                .forEach(product -> {
                    System.out.println("Deleted item #" + product.getId() + " due to its expiry"); // TODO: Change to normal logging
                    productsService.delete(product.getId());
                });

    }

    public static boolean isExpired(StorageEntry product) {
        if (product.getLifetimeDays() == null || product.getProductionDate() == null) {
            return false;
        }

        LocalDate expiryDate = product
                .getProductionDate()
                .toLocalDate()
                .plusDays(product.getLifetimeDays());

        return LocalDate.now().isAfter(expiryDate);
    }

}
