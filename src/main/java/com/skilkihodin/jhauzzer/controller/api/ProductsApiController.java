package com.skilkihodin.jhauzzer.controller.api;

import com.skilkihodin.jhauzzer.dto.RawStorageEntryAnswer;
import com.skilkihodin.jhauzzer.dto.RawStorageEntryPost;
import com.skilkihodin.jhauzzer.exceptions.purchase.InsufficientGoodsException;
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
    public RawStorageEntryAnswer getProduct(@PathVariable("id") int id) {
        StorageEntry result = service
                                .get(id)
                                .orElse(null);

        if (result == null) {
            return null;
        }

        return result.extractAnswerData();
    }

    @PostMapping("/register")
    public String createProduct(@RequestBody RawStorageEntryPost productEntryData) {
        service.add(StorageEntry.fromRawStorageEntryPost(productEntryData));

        return "Product entry created successfully.";
    }

    @PostMapping("/buy/{id}/{quantity}")
    public RawStorageEntryAnswer purchase(@PathVariable("id") int id, @PathVariable("quantity") int quantity) {

        try {
            return service.purchase(id, quantity).extractAnswerData();
        } catch (InsufficientGoodsException | NullPointerException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @PostMapping("/replenish/{id}/{quantity}")
    public String replenishStock(@PathVariable("id") int id, @PathVariable("quantity") int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Cannot add negative amount of items");
        }

        service.changeProductCountBy(id ,quantity);

        return "Stock increased successfully";
    }

    @PutMapping("/update-info")
    public String updateProduct(@RequestBody RawStorageEntryPost productEntryData) {
        service.update(StorageEntry.fromRawStorageEntryPost(productEntryData));

        return "Product entry updated successfully.";
    }

    @DeleteMapping("/remove")
    public String deleteProduct(@RequestBody int id) {
        service.delete(id);

        return "Product entry deleted successfully.";
    }

    @GetMapping("/get-all")
    public List<RawStorageEntryAnswer> getAllProducts() {
        return service.getAll()
                .stream()
                .map(StorageEntry::extractAnswerData)
                .toList();
    }

    @GetMapping("/get-like")
    public List<RawStorageEntryAnswer> getLike(@RequestBody RawStorageEntryPost example) {

        return service.getLike(StorageEntry.fromRawStorageEntryPost(example))
                .stream()
                .map(StorageEntry::extractAnswerData)
                .toList();

        //return service.getLike(Example.of(StorageEntry.fromRawStorageEntryPost(example)))
        //        .stream()
        //        .map(StorageEntry::extractAnswerData)
        //        .toList();
    }
}
