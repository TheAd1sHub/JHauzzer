package com.skilkihodin.jhauzzer.service;

import com.skilkihodin.jhauzzer.controller.repo.ProductsRepo;
import com.skilkihodin.jhauzzer.exceptions.DataNotFoundException;
import com.skilkihodin.jhauzzer.model.accounts.Account;
import com.skilkihodin.jhauzzer.model.warehouses.StorageEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public final class ProductsService {

    @Autowired
    private ProductsRepo repository;


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

        repository.save(entry);
    }

    public void update(StorageEntry entry) {
        if (!repository.existsById(entry.getId())) {
            throw new DataNotFoundException(entry.getName() + " not found");
        }

        repository.save(entry);
    }

    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new DataNotFoundException(id + " not found");
        }

        repository.deleteById(id);
    }

}
