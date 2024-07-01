package com.skilkihodin.jhauzzer.controller.repo;

import com.skilkihodin.jhauzzer.model.warehouses.StorageEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepo extends JpaRepository<StorageEntry, Integer> {
}
