package com.skilkihodin.jhauzzer.service;

import com.skilkihodin.jhauzzer.controller.repo.WarehousesRepo;
import com.skilkihodin.jhauzzer.model.warehouses.Warehouse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class WarehousesService {

    @Autowired
    WarehousesRepo repository;

    public Warehouse get(int id) throws IllegalArgumentException {
        Warehouse warehouse = repository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return warehouse;
    }

    public List<Warehouse> getAll() {
        return repository.findAll();
    }

    public void add(Warehouse warehouse) throws IllegalArgumentException {
        if (existsById(warehouse.getId())) {
            throw new IllegalArgumentException("A warehouse under the given ID exists already!");
        }

        repository.save(warehouse);
    }

    public void update(Warehouse newData) throws IllegalArgumentException {
         if (!existsById(newData.getId())) {
             throw new IllegalArgumentException("A warehouse under the given ID was not found!");
         }

         repository.save(newData);
    }

    public void delete(int id) {
        if (!existsById(id)) {
            throw new IllegalArgumentException("A warehouse under the given ID was not found!");
        }
    }


    public boolean existsById(int id) {
        return repository.existsById(id);
    }
}
