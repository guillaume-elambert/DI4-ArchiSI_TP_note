package edu.polytech.estore.business;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import edu.polytech.estore.dao.CommentDao;
import edu.polytech.estore.dao.ProductDao;
import edu.polytech.estore.model.Comment;
import edu.polytech.estore.model.Product;

@Stateless
public class StoreBusinessImpl implements StoreBusinessLocal {

    @Inject
    private ProductDao productDao;

    @Inject
    private CommentDao commentDao;

    @Override
    public List<Product> getProducts() {
        List<Product> products = this.productDao.getProducts();
        for (Product product : products) {
            product.setComments(this.commentDao.getComments(product.getProductId()));
        }
        return products;
    }

    @Override
    public Product getProduct(Long productId) {
        Product product = this.productDao.getProduct(productId);
        if (null != product) {
            product.setComments(this.commentDao.getComments(productId));
        }
        return product;
    }

    @Override
    public List<Product> getProductsOfCategory(String category) {
        return this.productDao.getProductsOfCategory(category);
    }

    /**
     * @param asc      Croissant (<code>true</code>) ou décroissant
     *                 (<code>false</code>) ?
     * @param products La liste à trier.
     * @return La liste en paramètre triée de manière croissante ou décroissante.
     */
    private List<Product> sortList(Boolean asc, List<Product> products) {
        if (asc) {
            Collections.sort(products, (p1, p2) -> p1.getPriceInEuro() > p2.getPriceInEuro() ? 1
                    : p1.getPriceInEuro() == p2.getPriceInEuro() ? 0 : -1);
        } else {
            Collections.sort(products, (p1, p2) -> p1.getPriceInEuro() < p2.getPriceInEuro() ? 1
                    : p1.getPriceInEuro() == p2.getPriceInEuro() ? 0 : -1);
        }
        return products;
    }

    @Override
    public List<Product> getSortedProducts(Boolean asc) {
        return sortList(asc, getProducts());
    }

    @Override
    public List<Product> getSortedProductsOfCategory(String category, Boolean asc) {
        return sortList(asc, getProductsOfCategory(category));
    }

    @Override
    public void deleteProduct(Long productId) {
        this.commentDao.deleteComments(productId);
        this.productDao.deleteProduct(productId);
    }

    @Override
    public void createProduct(Product product) {
        this.productDao.createProduct(product);
    }

    @Override
    public void updateProduct(Product product) {
        this.productDao.updateProduct(product);
    }

    @Override
    public void patchProduct(Long productId, Product patch) {
        Product product = getProduct(productId);

        if (null != patch.getCategory()) {
            product.setCategory(patch.getCategory());
        }
        if (null != patch.getLabel()) {
            product.setLabel(patch.getLabel());
        }
        if (null != patch.getPriceInEuro()) {
            product.setPriceInEuro(patch.getPriceInEuro());
        }
        if (null != patch.getStock()) {
            product.setStock(patch.getStock());
        }
        this.productDao.updateProduct(product);
    }

    @Override
    public List<Comment> getProductComments(Long productId) {
        return this.commentDao.getComments(productId);
    }
}
