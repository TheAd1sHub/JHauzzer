package com.skilkihodin.jhauzzer.controller.repo;

import com.skilkihodin.jhauzzer.model.warehouses.StoredProductEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepo extends JpaRepository<StoredProductEntry, Integer> {
}
