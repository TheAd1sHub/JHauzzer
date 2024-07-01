package com.skilkihodin.jhauzzer.controller.repo;

import com.skilkihodin.jhauzzer.model.warehouses.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehousesRepo extends JpaRepository<Warehouse, Integer> {

}
