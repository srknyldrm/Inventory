package com.yldrmsrkn.Inventory.service;

import com.yldrmsrkn.Inventory.entity.Product;
import com.yldrmsrkn.Inventory.entity.ProductHistory;
import com.yldrmsrkn.Inventory.enums.OperationType;
import com.yldrmsrkn.Inventory.exception.ProductAlreadyExistsException;
import com.yldrmsrkn.Inventory.exception.ProductNotFoundException;
import com.yldrmsrkn.Inventory.repository.ProductHistoryRepository;
import com.yldrmsrkn.Inventory.repository.ProductRepository;
import com.yldrmsrkn.Inventory.util.GeneteraUUıd;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductHistoryRepository productHistoryRepository;

    @Transactional
    public void addProduct(Product product) throws ProductAlreadyExistsException {

        if (productRepository.existsByName(product.getName())) {
            throw new ProductAlreadyExistsException();
        }

        UUID uuid = GeneteraUUıd.generateCode();

        Product saveProduct = new Product();
        saveProduct.setId(uuid);
        saveProduct.setName(product.getName());
        saveProduct.setDescription(product.getDescription());
        saveProduct.setPrice(product.getPrice());
        saveProduct.setCategory(product.getCategory());

        ProductHistory productHistory = new ProductHistory();
        productHistory.setProductName(product.getName());
        productHistory.setProductId(GeneteraUUıd.generateCode());
        productHistory.setProductName(product.getName());
        productHistory.setPrice(product.getPrice());
        productHistory.setDate(LocalDateTime.now());
        productHistory.setCategory(product.getCategory());
        productHistory.setOperationType(OperationType.ADD);

        productRepository.save(product);
        productHistoryRepository.save(productHistory);

    }

    public Product getProductById(UUID id) throws ProductNotFoundException {
        if (productRepository.findById(id) == null) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findByCategory(Long category) {
        return productRepository.findProductByCategory_Id(category);
    }

    @Transactional
    public Product updateProduct(UUID id, Product product) throws ProductNotFoundException {
        Product existingProduct = getProductById(id);
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());

        ProductHistory productHistory = new ProductHistory();
        productHistory.setId(productHistory.getId());
        productHistory.setProductName(product.getName());
        productHistory.setProductId(product.getId());
        productHistory.setProductName(product.getName());
        productHistory.setPrice(product.getPrice());
        productHistory.setDate(LocalDateTime.now());
        productHistory.setCategory(product.getCategory());
        productHistory.setOperationType(OperationType.UPDATE);

        productRepository.save(existingProduct);
        productHistoryRepository.save(productHistory);
        return existingProduct;
    }

    public void deleteProduct(UUID id) throws ProductNotFoundException {
        Product existingProduct = getProductById(id);

        ProductHistory productHistory = new ProductHistory();
        productHistory.setId(productHistory.getId());
        productHistory.setProductName(existingProduct.getName());
        productHistory.setProductId(existingProduct.getId());
        productHistory.setProductName(existingProduct.getName());
        productHistory.setPrice(existingProduct.getPrice());
        productHistory.setDate(LocalDateTime.now());
        productHistory.setCategory(existingProduct.getCategory());
        productHistory.setOperationType(OperationType.DELETE);

        productRepository.delete(existingProduct);
        productHistoryRepository.save(productHistory);
    }
}