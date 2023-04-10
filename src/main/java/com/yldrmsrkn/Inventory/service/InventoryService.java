package com.yldrmsrkn.Inventory.service;

import com.yldrmsrkn.Inventory.entity.Inventory;
import com.yldrmsrkn.Inventory.entity.InventoryHistory;
import com.yldrmsrkn.Inventory.enums.City;
import com.yldrmsrkn.Inventory.enums.OperationType;
import com.yldrmsrkn.Inventory.enums.Region;
import com.yldrmsrkn.Inventory.exception.InventoryNotFoundException;
import com.yldrmsrkn.Inventory.exception.ProductNotFoundException;
import com.yldrmsrkn.Inventory.repository.InventoryHistoryRepository;
import com.yldrmsrkn.Inventory.repository.InventoryRepository;
import com.yldrmsrkn.Inventory.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryHistoryRepository inventoryHistoryRepository;
    private static final Logger log = Logger.getLogger(InventoryService.class.getName());

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public List<Inventory> getAllInventoriesByWarehouse(Long id) {
        return inventoryRepository.findAllByWarehouse_Id(id);
    }

    public List<Inventory> getAllInventoriesByCity(City city) {
        return inventoryRepository.findAllByCity(city);
    }

    public List<Inventory> getAllInventoriesByRegion(Region region) {
        return inventoryRepository.findAllByRegion(region);
    }

    public Inventory getInventoryById(Long id) throws InventoryNotFoundException {
        return inventoryRepository.findById(id).orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id : " + id));
    }

    @Transactional
    public void createInventory(Inventory inventory) throws ProductNotFoundException, InventoryNotFoundException {
        if (!productRepository.existsById(inventory.getProduct().getId())) {
            throw new ProductNotFoundException("Product not exists.");
        }

        /*UUID productId = inventoryRepository.findAll().stream().filter(inv -> inv.getRegion().equals(inventory.getRegion())).map(Inventory::getProduct).filter(p -> p.getId().equals(inventory.getProduct().getId())).map(Product::getId).findFirst().orElse(null);
         */
        Inventory existingItem = inventoryRepository.findAll().stream().filter(inv -> inv.getWarehouse().getId().equals(inventory.getWarehouse().getId())).filter(inv -> inv.getProduct().getId().equals(inventory.getProduct().getId())).findFirst().orElse(null);

        if (existingItem == null) {

            Inventory saveInventory = new Inventory();
            saveInventory.setId(inventory.getId());
            saveInventory.setQuantity(inventory.getQuantity());
            saveInventory.setRegion(inventory.getRegion());
            saveInventory.setCity(inventory.getCity());

            InventoryHistory inventoryHistory = new InventoryHistory();
            inventoryHistory.setProductId(inventory.getId());
            inventoryHistory.setWarehouse(inventory.getWarehouse());
            inventoryHistory.setQuantity(inventory.getQuantity());
            inventoryHistory.setDate(LocalDateTime.now());
            inventoryHistory.setRegion(inventory.getRegion());
            inventoryHistory.setCity(inventory.getCity());
            inventoryHistory.setOperationType(OperationType.ADD);
            inventoryHistoryRepository.save(inventoryHistory);

            inventoryRepository.save(inventory);
            inventoryHistoryRepository.save(inventoryHistory);

        } else
            throw new InventoryNotFoundException("Inventory exists.");

    }

    public void deleteInventoryById(Long id) {
        inventoryRepository.deleteById(id);
    }

    @Transactional
    public void updateInventory(Inventory inventoryDetails) throws InventoryNotFoundException {
        Inventory existingItem = inventoryRepository.findAll().stream().filter(inv -> inv.getWarehouse().getId().equals(inventoryDetails.getWarehouse().getId())).filter(inv -> inv.getProduct().getId().equals(inventoryDetails.getProduct().getId())).findFirst().orElse(null);

        if (existingItem == null) {
            throw new InventoryNotFoundException("Inventory item not found with ID: ");
        }

        existingItem.setProduct(inventoryDetails.getProduct());
        existingItem.setCity(inventoryDetails.getCity());
        existingItem.setQuantity(inventoryDetails.getQuantity() + existingItem.getQuantity());

        InventoryHistory inventoryHistory = new InventoryHistory();
        inventoryHistory.setProductId(existingItem.getId());
        inventoryHistory.setWarehouse(existingItem.getWarehouse());
        inventoryHistory.setQuantity(existingItem.getQuantity());
        inventoryHistory.setDate(LocalDateTime.now());
        inventoryHistory.setRegion(existingItem.getRegion());
        inventoryHistory.setCity(existingItem.getCity());
        inventoryHistory.setOperationType(OperationType.UPDATE);
        inventoryHistoryRepository.save(inventoryHistory);

    }

    @Transactional
    public void removeFromInventory(Inventory inventoryDetails, Integer removeQuantity) throws InventoryNotFoundException, ProductNotFoundException {

        Inventory existingItem = inventoryRepository.findAll().stream().filter(inv -> inv.getWarehouse().getId().equals(inventoryDetails.getWarehouse().getId())).filter(inv -> inv.getProduct().getId().equals(inventoryDetails.getProduct().getId())).findFirst().orElse(null);

        if (existingItem == null) {
            throw new InventoryNotFoundException("Inventory item not found");
        }

        int remainingQuantity = existingItem.getQuantity() - removeQuantity;
        if (remainingQuantity < 0) {
            throw new ProductNotFoundException("Not enough inventory");
        }
        existingItem.setQuantity(remainingQuantity);
        inventoryRepository.save(existingItem);

        InventoryHistory inventoryHistory = new InventoryHistory();
        inventoryHistory.setProductId(existingItem.getId());
        inventoryHistory.setWarehouse(existingItem.getWarehouse());
        inventoryHistory.setQuantity(existingItem.getQuantity());
        inventoryHistory.setDate(LocalDateTime.now());
        inventoryHistory.setRegion(existingItem.getRegion());
        inventoryHistory.setCity(existingItem.getCity());
        inventoryHistory.setOperationType(OperationType.REMOVE);
        inventoryHistoryRepository.save(inventoryHistory);

        inventoryRepository.save(existingItem);
        inventoryHistoryRepository.save(inventoryHistory);

        if (remainingQuantity < 5) {
            log.info(" inventory level critical - warehouse:" + existingItem.getWarehouse().getName() + " - product" + existingItem.getProduct().getName());
        }
    }

    public void deleteInventory(Long id) throws InventoryNotFoundException {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);

        if (!optionalInventory.isPresent()) {
            throw new InventoryNotFoundException("Inventory with id " + id + " not found.");
        }

        InventoryHistory inventoryHistory = new InventoryHistory();
        inventoryHistory.setProductId(optionalInventory.get().getId());
        inventoryHistory.setWarehouse(optionalInventory.get().getWarehouse());
        inventoryHistory.setQuantity(optionalInventory.get().getQuantity());
        inventoryHistory.setDate(LocalDateTime.now());
        inventoryHistory.setRegion(optionalInventory.get().getRegion());
        inventoryHistory.setCity(optionalInventory.get().getCity());
        inventoryHistory.setOperationType(OperationType.DELETE);

        inventoryRepository.deleteById(id);
        inventoryHistoryRepository.save(inventoryHistory);

    }
}