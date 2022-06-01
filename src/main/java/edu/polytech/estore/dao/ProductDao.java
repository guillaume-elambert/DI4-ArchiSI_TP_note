package edu.polytech.estore.dao;

import java.util.List;

import edu.polytech.estore.model.Product;

public interface ProductDao {

    public List<Product> getProducts();

    public Product getProduct(Long productId);

    public List<Product> getProductsOfCategory(String category);

    public void deleteProduct(Long productId);

    public void createProduct(Product product);

    public void updateProduct(Product product);
}
