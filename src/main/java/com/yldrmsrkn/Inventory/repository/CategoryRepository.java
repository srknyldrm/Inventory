package com.yldrmsrkn.Inventory.repository;

import com.yldrmsrkn.Inventory.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
