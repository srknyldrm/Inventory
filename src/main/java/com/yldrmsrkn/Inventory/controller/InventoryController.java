package com.yldrmsrkn.Inventory.controller;

import com.yldrmsrkn.Inventory.entity.Inventory;
import com.yldrmsrkn.Inventory.entity.InventoryHistory;
import com.yldrmsrkn.Inventory.enums.City;
import com.yldrmsrkn.Inventory.enums.Region;
import com.yldrmsrkn.Inventory.exception.InventoryNotFoundException;
import com.yldrmsrkn.Inventory.exception.ProductNotFoundException;
import com.yldrmsrkn.Inventory.repository.InventoryRepository;
import com.yldrmsrkn.Inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping("/")
    public List<Inventory> getAllInventories() {
        return inventoryService.getAllInventories();
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<Inventory>> getAllInventoriesByWarehouse(@PathVariable Long warehouseId) {
        List<Inventory> inventories = inventoryService.getAllInventoriesByWarehouse(warehouseId);
        return ResponseEntity.ok().body(inventories);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Inventory>> getAllInventoriesByCity(@PathVariable City city) {
        List<Inventory> inventories = inventoryService.getAllInventoriesByCity(city);
        return ResponseEntity.ok().body(inventories);
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<List<Inventory>> getAllInventoriesByRegion(@PathVariable Region region) {
        List<Inventory> inventories = inventoryService.getAllInventoriesByRegion(region);
        return ResponseEntity.ok().body(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable(value = "id") Long inventoryId) throws Exception, InventoryNotFoundException {
        Inventory inventory = inventoryService.getInventoryById(inventoryId);
        return ResponseEntity.ok().body(inventory);
    }

    @GetMapping("/inventorieshistory")
    public ResponseEntity<List<InventoryHistory>> getAllProductsHistory() {
        List<InventoryHistory> inventoryHistories = inventoryService.getAllInventoryHistories();
        return new ResponseEntity<>(inventoryHistories, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> createInventory(@RequestBody Inventory inventory) {
        try {
            inventoryService.createInventory(inventory);
            return ResponseEntity.ok("Inventory added successfully.");
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (InventoryNotFoundException e) {
            return ResponseEntity.badRequest().body("Inventory exists.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateInventory(@RequestBody Inventory inventoryDetails) {
        try {
            inventoryService.updateInventory(inventoryDetails);
            return ResponseEntity.ok("Inventory updated successfully.");
        } catch (InventoryNotFoundException e) {
            return ResponseEntity.badRequest().body("Inventory item not found");
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromInventory(@RequestBody Inventory inventoryDetails, @RequestParam Integer removeQuantity) {
        try {
            inventoryService.removeFromInventory(inventoryDetails, removeQuantity);
            return ResponseEntity.ok("Inventory removed successfully.");
        } catch (InventoryNotFoundException | ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable(value = "id") Long inventoryId) {
        try {
            inventoryService.deleteInventory(inventoryId);
            return ResponseEntity.ok("Inventory with id " + inventoryId + " has been successfully deleted.");
        } catch (InventoryNotFoundException e) {
            return ResponseEntity.badRequest().body("Inventory not exists.");
        }
    }
}