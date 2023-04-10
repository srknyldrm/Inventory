package com.yldrmsrkn.Inventory.repository;

import com.yldrmsrkn.Inventory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findById(UUID id);

    boolean existsByName(String name);

    boolean existsById(UUID id);

    List<Product> findProductByCategory_Id(Long id);

}
