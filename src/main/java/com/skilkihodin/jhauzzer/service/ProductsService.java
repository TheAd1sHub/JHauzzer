package com.skilkihodin.jhauzzer.service;

import com.skilkihodin.jhauzzer.controller.repo.ProductsRepo;
import com.skilkihodin.jhauzzer.exceptions.DataNotFoundException;
import com.skilkihodin.jhauzzer.exceptions.purchase.InsufficientGoodsException;
import com.skilkihodin.jhauzzer.model.warehouses.StorageEntry;
import com.skilkihodin.jhauzzer.model.warehouses.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
        return repository.findAll(sample);
    }

    public boolean areCompatible(StorageEntry entry, Warehouse warehouse) {
        return entry.getType() == warehouse.getProductsType();
    }

}
