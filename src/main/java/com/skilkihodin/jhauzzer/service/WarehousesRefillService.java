package com.skilkihodin.jhauzzer.service;

import com.skilkihodin.jhauzzer.model.warehouses.StorageEntry;
import org.intellij.lang.annotations.MagicConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

@Service
public final class WarehousesRefillService {

    @Autowired
    private ProductsService productsService;

    @MagicConstant
    private final int DEFAULT_REFILL_AMOUNT = 100;

    @Scheduled(cron = "0 */1 * * * *") // Every hour
    public void refillOutOfStockItems() {
        productsService.getAll()
                .stream()
                .filter(WarehousesRefillService::isOutOfStock)
                .mapToInt(StorageEntry::getId)
                .forEach(this::orderMore);
    }


    public void orderMore(int itemId) {
        orderMore(itemId, DEFAULT_REFILL_AMOUNT);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void orderMore(int itemId, int orderAmount) {
        StorageEntry item = productsService.get(itemId).get();

        StorageEntry restockedItemEntry = item.clone();
        restockedItemEntry.setProductionDate(Date.valueOf(LocalDate.now()));
        restockedItemEntry.setQuantity(orderAmount);

        productsService.delete(itemId);
        productsService.add(item);
    }

    public static boolean isOutOfStock(StorageEntry item) {
        return item.getQuantity() == 0;
    }
}
