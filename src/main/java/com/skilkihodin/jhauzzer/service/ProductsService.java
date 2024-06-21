package com.skilkihodin.jhauzzer.service;

import com.skilkihodin.jhauzzer.controller.repo.ProductsRepo;
import com.skilkihodin.jhauzzer.exceptions.DataNotFoundException;
import com.skilkihodin.jhauzzer.exceptions.purchase.InsufficientGoodsException;
import com.skilkihodin.jhauzzer.model.warehouses.ProductGroup;
import com.skilkihodin.jhauzzer.model.warehouses.ProductQuality;
import com.skilkihodin.jhauzzer.model.warehouses.StorageEntry;
import com.skilkihodin.jhauzzer.model.warehouses.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public final class ProductsService {

    @Autowired
    private ProductsRepo repository;

    @Autowired
    private WarehousesService warehousesService;


    public Optional<StorageEntry> get(int id) {
        return repository.findById(id);
    }

    public List<StorageEntry> getAll() {
        return repository.findAll();
    }

    public void add(StorageEntry entry) {
        if (repository.existsById(entry.getId())) {
            throw new UnsupportedOperationException("Good with such id is already registered.");
        }

        if (!warehousesService.existsById(entry.getWarehouseId())) {
            throw new IllegalArgumentException("The warehouse stated in the given product data does not exist");
        }

        if (!areCompatible(entry, warehousesService.get(entry.getWarehouseId()).get())) {
            throw new IllegalArgumentException("This product cannot be stored in the chosen warehouse.");
        }

        repository.save(entry);
    }

    public void update(StorageEntry entry) {
        if (!repository.existsById(entry.getId())) {
            throw new DataNotFoundException(entry.getName() + " not found");
        }

        if (!warehousesService.existsById(entry.getWarehouseId())) {
            throw new IllegalArgumentException("The warehouse stated in the given product data does not exist");
        }

        if (!areCompatible(entry, warehousesService.get(entry.getWarehouseId()).get())) {
            throw new IllegalArgumentException("This product cannot be stored in the chosen warehouse.");
        }

        repository.save(entry);
    }

    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new DataNotFoundException(id + " not found");
        }

        repository.deleteById(id);
    }

    public StorageEntry purchase(int id, int quantity) throws InsufficientGoodsException {
        StorageEntry product = get(id).orElse(null);

        if (product == null) {
            return null;
        }

        if (product.getQuantity() < quantity) {
            throw new InsufficientGoodsException();
        }

        product.setQuantity(product.getQuantity() - quantity);
        update(product);

        StorageEntry purchaseData = product.clone();
        purchaseData.setQuantity(quantity);

        return purchaseData;
    }

    public void changeProductCountBy(int id, int quantity) {
        StorageEntry product = get(id).orElse(null);

        if (product == null) {
            throw new DataNotFoundException();
        }

        product.setQuantity(product.getQuantity() + quantity);
        repository.save(product);
    }

    public List<StorageEntry> getLike(Example<StorageEntry> sample) {
        //return repository.findAll();

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase();

        return repository.findAll(sample);
    }

    public List<StorageEntry> getLike(StorageEntry sample) {
        return repository.findAll()
                .stream()
                .filter(entry -> sample.getId() == null
                        || sample.getId().equals(entry.getId()))
                .filter(entry -> sample.getName() == null
                        || entry.getName().toUpperCase().contains(sample.getName().toUpperCase()))
                .filter(entry -> sample.getType() == null
                        || sample.getType() == ProductGroup.NONE
                        || sample.getType() == entry.getType())
                .filter(entry -> sample.getQuality() == null
                        || sample.getQuality() == ProductQuality.NONE
                        || sample.getQuality() == entry.getQuality())
                .filter(entry -> sample.getWarehouseId() == null
                        || sample.getWarehouseId().equals(entry.getWarehouseId()))
                .filter(entry -> sample.getQuantity() == null
                        || sample.getQuantity() <= entry.getQuantity())
                .filter(entry -> sample.getPrice() == null
                        || sample.getPrice() >= entry.getPrice())
                .filter(entry -> sample.getDiscount() == null
                        || sample.getDiscount() <= entry.getDiscount())
                .filter(entry -> sample.getVipDiscount() == null
                        || sample.getVipDiscount() <= entry.getVipDiscount())
                .filter(entry -> sample.getProductionDate() == null
                        || sample.getProductionDate().equals(entry.getProductionDate())
                        || sample.getProductionDate().before(entry.getProductionDate()))
                .filter(entry -> sample.getLifetimeDays() == null
                        || sample.getLifetimeDays() <= entry.getLifetimeDays())
                .toList();

    }

    public boolean areCompatible(StorageEntry entry, Warehouse warehouse) {
        return entry.getType() == warehouse.getProductsType();
    }

}
