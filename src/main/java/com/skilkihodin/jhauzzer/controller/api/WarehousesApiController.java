package com.skilkihodin.jhauzzer.controller.api;

import com.skilkihodin.jhauzzer.exceptions.DataNotFoundException;
import com.skilkihodin.jhauzzer.model.warehouses.Warehouse;
import com.skilkihodin.jhauzzer.controller.repo.WarehousesRepo;
import com.skilkihodin.jhauzzer.service.WarehousesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/warehouses")
public final class WarehousesApiController {

    //@Autowired private WarehousesRepo warehousesRepo;
    @Autowired
    private WarehousesService service;

    @GetMapping("/")
    public String displayTitle() {
        return "Main warehouses page.\nTry to specify your request with sub-address.";
    }

    @GetMapping("/find/{id}")
    public Warehouse getWarehouse(@PathVariable("id") int id) throws DataNotFoundException {
        return service.get(id)
                .orElseThrow(DataNotFoundException::new);
        //return warehousesRepo
        //        .findById(id)
        //        .orElse(null);
    }

    //@GetMapping("/find-with")
    //public List<Warehouse> getWarehousesWith() {
    //
    //    for (Warehouse warehouse : warehousesRepo.findAll()) {
    //        // TODO: Implement selection through iteration
    //        System.out.println(warehouse.hashCode());
    //    }
    //
    //    return null;
    //}

    @PostMapping("/register")
    public String createWarehouse(@RequestBody Warehouse warehouseData) {
        service.add(warehouseData);

        return "Warehouse page created successfully.";
    }

    @PutMapping("/update-info")
    public String updateWarehouse(@RequestBody Warehouse warehouseData) {
        service.update(warehouseData);

        return "Warehouse data updated successfully.";
    }

    @DeleteMapping("/remove")
    public String deleteWarehouse(@RequestBody int id) {
        service.delete(id);

        return "Warehouse data deleted successfully.";
    }
}
