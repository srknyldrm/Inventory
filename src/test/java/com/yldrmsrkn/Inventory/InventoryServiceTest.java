package com.yldrmsrkn.Inventory;

import com.yldrmsrkn.Inventory.entity.Inventory;
import com.yldrmsrkn.Inventory.entity.InventoryHistory;
import com.yldrmsrkn.Inventory.entity.Product;
import com.yldrmsrkn.Inventory.entity.Warehouse;
import com.yldrmsrkn.Inventory.enums.City;
import com.yldrmsrkn.Inventory.enums.OperationType;
import com.yldrmsrkn.Inventory.enums.Region;
import com.yldrmsrkn.Inventory.exception.InventoryNotFoundException;
import com.yldrmsrkn.Inventory.exception.ProductNotFoundException;
import com.yldrmsrkn.Inventory.repository.InventoryHistoryRepository;
import com.yldrmsrkn.Inventory.repository.InventoryRepository;
import com.yldrmsrkn.Inventory.repository.ProductRepository;
import com.yldrmsrkn.Inventory.repository.WarehouseRepository;
import com.yldrmsrkn.Inventory.service.InventoryService;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class InventoryServiceTest {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private InventoryHistoryRepository inventoryHistoryRepository;

    private Product product;
    private Warehouse warehouse;
    private City city;
    private Region region;
    private Inventory inventory;

    @Before
    public void setUp() {
        product = new Product();
        product.setName("Test Product");
        productRepository.save(product);

        warehouse = new Warehouse();
        warehouse.setName("Test Warehouse");
        warehouseRepository.save(warehouse);

        city = City.ISTANBUL;
        region = Region.MARMARA;

        inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setWarehouse(warehouse);
        inventory.setCity(city);
        inventory.setRegion(region);
        inventory.setQuantity(10);
    }

    @Test
    public void testCreateInventory() throws ProductNotFoundException, InventoryNotFoundException {
        inventoryService.createInventory(inventory);
        Inventory savedInventory = (Inventory) inventoryRepository.findAllByWarehouse_Id(product.getCategory().getId());
        assertNotNull(savedInventory);
        assertEquals(inventory.getQuantity(), savedInventory.getQuantity());

        List<InventoryHistory> inventoryHistoryList = inventoryHistoryRepository.findAllById(Collections.singleton(savedInventory.getId()));
        assertNotNull(inventoryHistoryList);
        assertEquals(1, inventoryHistoryList.size());
        InventoryHistory inventoryHistory = inventoryHistoryList.get(0);
        assertEquals(savedInventory.getId(), inventoryHistory.getProductId());
        assertEquals(savedInventory.getQuantity(), inventoryHistory.getQuantity());
        assertEquals(warehouse, inventoryHistory.getWarehouse());
        assertEquals(region, inventoryHistory.getRegion());
        assertEquals(city, inventoryHistory.getCity());
        assertEquals(OperationType.ADD, inventoryHistory.getOperationType());
    }

    @Test(expected = InventoryNotFoundException.class)
    public void testCreateInventoryThrowsInventoryNotFoundException() throws ProductNotFoundException, InventoryNotFoundException {
        inventoryRepository.save(inventory);

        Inventory existingInventory = new Inventory();
        existingInventory.setProduct(product);
        existingInventory.setWarehouse(warehouse);
        existingInventory.setCity(city);
        existingInventory.setRegion(region);
        existingInventory.setQuantity(10);

        inventoryService.createInventory(existingInventory);
    }

    @Test(expected = ProductNotFoundException.class)
    public void testCreateInventoryThrowsProductNotFoundException() throws ProductNotFoundException, InventoryNotFoundException {
        Product invalidProduct = new Product();
        invalidProduct.setId(UUID.randomUUID());

        Inventory invalidInventory = new Inventory();
        invalidInventory.setProduct(invalidProduct);
        invalidInventory.setWarehouse(warehouse);
        invalidInventory.setCity(city);
        invalidInventory.setRegion(region);
        invalidInventory.setQuantity(10);

        inventoryService.createInventory(invalidInventory);
    }
}