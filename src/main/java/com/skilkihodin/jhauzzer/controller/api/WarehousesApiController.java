package com.skilkihodin.jhauzzer.controller.api;

import com.skilkihodin.jhauzzer.model.warehouses.Warehouse;
import com.skilkihodin.jhauzzer.controller.repo.WarehousesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/warehouses")
public final class WarehousesApiController {

    @Autowired private WarehousesRepo warehousesRepo;

    @GetMapping("/")
    public String displayTitle() {
        return "Main warehouses page.\nTry to specify your request with sub-address.";
    }

    @GetMapping("/find/{id}")
    public Warehouse getWarehouse(@PathVariable("id") int id) {
        return warehousesRepo
                .findById(id)
                .orElse(null);
    }

    @GetMapping("/find-with")
    public List<Warehouse> getWarehousesWith() {

        for (Warehouse warehouse : warehousesRepo.findAll()) {
            // TODO: Implement selection through iteration
            System.out.println(warehouse.hashCode());
        }

        return null;
    }

    @PostMapping("/new")
    public String createWarehouse(@RequestBody Warehouse warehouseData) {

        if (warehousesRepo.existsById(warehouseData.getId())) {
            throw new UnsupportedOperationException("A warehouse under such ID exists already.");
        }

        warehousesRepo.save(warehouseData);

        return "Warehouse page created successfully.";
    }

    @PutMapping("/update-info")
    public String updateWarehouse(@RequestBody Warehouse warehouseData) {

        if (!warehousesRepo.existsById(warehouseData.getId())) {
            throw new UnsupportedOperationException("A warehouse under such ID does not exist.");
        }

        warehousesRepo.save(warehouseData);

        return "Warehouse data updated successfully.";
    }

    @DeleteMapping("/remove")
    public String deleteWarehouse(@RequestBody int id) {

        if (!warehousesRepo.existsById(id)) {

            throw new UnsupportedOperationException("Such a warehouse does not exist.");
        }

        warehousesRepo.delete(warehousesRepo.getReferenceById(id));

        return "Warehouse data deleted successfully.";
    }
}
