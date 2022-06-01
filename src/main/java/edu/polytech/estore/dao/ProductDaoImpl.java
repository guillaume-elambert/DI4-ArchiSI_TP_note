package edu.polytech.estore.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.polytech.estore.model.Product;

@Stateless
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext(unitName = "EStore")
    private EntityManager em;

    @Override
    public List<Product> getProducts() {
        Query request = em.createQuery("select p from Product p");
        return request.getResultList();
    }

    @Override
    public Product getProduct(Long productId) {
        return em.find(Product.class, productId);
    }

    @Override
    public List<Product> getProductsOfCategory(String category) {
        Query request = em.createQuery("select p from Product p where p.category = ?1");
        request.setParameter(1, category);
        return request.getResultList();
    }

    @Override
    public void deleteProduct(Long productId) {
        em.remove(getProduct(productId));
    }

    @Override
    public void createProduct(Product product) {
        em.persist(product);
    }

    @Override
    public void updateProduct(Product product) {
        em.merge(product);
    }
}
