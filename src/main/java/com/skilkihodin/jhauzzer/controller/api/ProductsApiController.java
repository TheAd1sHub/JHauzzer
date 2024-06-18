package com.skilkihodin.jhauzzer.controller.api;

import com.skilkihodin.dto.RawStorageEntry;
import com.skilkihodin.jhauzzer.controller.repo.ProductsRepo;
import com.skilkihodin.jhauzzer.model.warehouses.StorageEntry;
import com.skilkihodin.jhauzzer.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public final class ProductsApiController {

    @Autowired
    private ProductsService service;

    @GetMapping("/")
    public String displayTitle() {
        return "Main products page.\nTry to specify your request with sub-address.";
    }

    @GetMapping("/get/{id}")
    public RawStorageEntry getProduct(@PathVariable("id") int id) {
        StorageEntry result = service
                                .get(id)
                                .orElse(null);

        if (result == null) {
            return null;
        }

        return result.extractRawData();
    }

    @PostMapping("/register")
    public String createProduct(@RequestBody StorageEntry productEntryData) {
        service.add(productEntryData);

        return "Product entry created successfully.";
    }

    @PutMapping("/update-info")
    public String updateProduct(@RequestBody StorageEntry productEntryData) {
        service.update(productEntryData);

        return "Product entry updated successfully.";
    }

    @DeleteMapping("/remove")
    public String deleteProduct(@RequestBody int id) {
        service.delete(id);

        return "Product entry deleted successfully.";
    }

    @GetMapping("/get-all")
    public List<RawStorageEntry> getAllProducts() {
        return service.getAll()
                .stream()
                .map(StorageEntry::extractRawData)
                .toList();
    }

    @GetMapping("/get-like")
    public List<RawStorageEntry> getBy(@RequestBody RawStorageEntry example) {

            StorageEntry exampleEntry = new StorageEntry();
            exampleEntry.setPrice(example.getPrice());
            exampleEntry.setPrice(example.getPrice());
            exampleEntry.setPrice(example.getPrice());

    }
}
