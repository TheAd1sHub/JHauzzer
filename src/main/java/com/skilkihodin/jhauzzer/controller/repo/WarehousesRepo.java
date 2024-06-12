package com.skilkihodin.jhauzzer.controller.repo;

import com.skilkihodin.jhauzzer.model.warehouses.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehousesRepo extends JpaRepository<Warehouse, Integer> {
}
