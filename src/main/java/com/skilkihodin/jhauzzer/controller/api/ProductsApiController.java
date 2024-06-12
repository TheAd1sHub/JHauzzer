package com.skilkihodin.jhauzzer.controller.api;

import com.skilkihodin.jhauzzer.controller.repo.ProductsRepo;
import com.skilkihodin.jhauzzer.model.warehouses.StoredProductEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/products")
public final class ProductsApiController {

    @Autowired
    private ProductsRepo productsRepo;

    @GetMapping("/")
    public String displayTitle() {
        return "Main products page.\nTry to specify your request with sub-address.";
    }

    @GetMapping("/find/{id}")
    public StoredProductEntry getProduct(@PathVariable("id") int id) {
        return productsRepo
                .findById(id)
                .orElse(null);
    }

    @PostMapping("/new")
    public String createProduct(@RequestBody StoredProductEntry productEntryData) {

        if (productsRepo.existsById(productEntryData.getId())) {
            throw new UnsupportedOperationException("An entry under such ID exists already.");
        }

        productsRepo.save(productEntryData);

        return "Product entry created successfully.";
    }

    @PutMapping("/update-info")
    public String updateProduct(@RequestBody StoredProductEntry productEntryData) {

        if (!productsRepo.existsById(productEntryData.getId())) {
            throw new UnsupportedOperationException("An entry under such ID does not exist.");
        }

        productsRepo.save(productEntryData);

        return "Product entry updated successfully.";
    }

    @DeleteMapping("/remove")
    public String deleteProduct(@RequestBody int id) {

        if (!productsRepo.existsById(id)) {
            throw new UnsupportedOperationException("An entry under such ID does not exist.");
        }

        productsRepo.delete(productsRepo.getReferenceById(id));

        return "Product entry deleted successfully.";
    }
}
