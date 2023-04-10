package com.yldrmsrkn.Inventory.controller;

import com.yldrmsrkn.Inventory.entity.Warehouse;
import com.yldrmsrkn.Inventory.exception.WarehouseNotFoundException;
import com.yldrmsrkn.Inventory.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/")
    public List<Warehouse> getAllWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable(value = "id") Long warehouseId) throws Exception, WarehouseNotFoundException {
        Warehouse warehouse = warehouseService.getWarehouseById(warehouseId);
        return ResponseEntity.ok().body(warehouse);
    }

    @PostMapping("/")
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.addWarehouse(warehouse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable(value = "id") Long warehouseId, @RequestBody Warehouse warehouseDetails) throws Exception, WarehouseNotFoundException {
        Warehouse updatedWarehouse = warehouseService.updateWarehouse(warehouseId, warehouseDetails);
        return ResponseEntity.ok(updatedWarehouse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable(value = "id") Long warehouseId) throws Exception, WarehouseNotFoundException {
        warehouseService.deleteWarehouse(warehouseId);
        return ResponseEntity.ok().build();
    }
}