package edu.polytech.estore.dao;

import java.util.List;

import edu.polytech.estore.model.Product;

public interface ProductDao {

    List<Product> getProducts();

    Product getProduct(Long productId);

    List<Product> getProductsOfCategory(String category);

    void deleteProduct(Long productId);

    void createProduct(Product product);

    void updateProduct(Product product);
}
