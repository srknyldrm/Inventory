package com.yldrmsrkn.Inventory.service;

import com.yldrmsrkn.Inventory.entity.Warehouse;
import com.yldrmsrkn.Inventory.exception.WarehouseNotFoundException;
import com.yldrmsrkn.Inventory.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(long id) throws WarehouseNotFoundException {
        return warehouseRepository.findById(id).orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found with id: " + id));
    }

    public Warehouse addWarehouse(Warehouse warehouse) {
        warehouseRepository.save(warehouse);
        return warehouse;
    }

    public Warehouse updateWarehouse(Long id, Warehouse warehouse) throws WarehouseNotFoundException {
        Warehouse existingWarehouse = warehouseRepository.findById(id).orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found with id: " + id));
        existingWarehouse.setName(warehouse.getName());
        existingWarehouse.setAdress(warehouse.getAdress());
        warehouseRepository.save(existingWarehouse);
        return existingWarehouse;
    }

    public void deleteWarehouse(Long id) throws WarehouseNotFoundException {
        Warehouse existingWarehouse = warehouseRepository.findById(id).orElseThrow(() -> new WarehouseNotFoundException("Warehouse not found with id: " + id));
        warehouseRepository.delete(existingWarehouse);
    }
}