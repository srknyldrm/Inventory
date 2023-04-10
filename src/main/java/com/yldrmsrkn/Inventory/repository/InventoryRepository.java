package com.yldrmsrkn.Inventory.repository;

import com.yldrmsrkn.Inventory.entity.Inventory;
import com.yldrmsrkn.Inventory.enums.City;
import com.yldrmsrkn.Inventory.enums.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory getById(Long id);

    List<Inventory> findAllByWarehouse_Id(Long id);

    List<Inventory> findAllByCity(City city);

    List<Inventory> findAllByRegion(Region region);

}