package edu.polytech.estore.business;

import java.util.List;

import javax.ejb.Local;

import edu.polytech.estore.model.Comment;
import edu.polytech.estore.model.Product;

@Local
public interface StoreBusinessLocal {

    /**
     * Pour le service n°1.
     */
    List<Product> getProducts();

    /**
     * Pour le service n°2.
     *
     * @param productId L'identifiant du produit.
     */
    Product getProduct(Long productId);

    /**
     * Pour le service n°3.
     *
     * @param category La catégorie sur laquelle filtrer.
     */
    List<Product> getProductsOfCategory(String category);

    /**
     * Pour le service n°4.
     *
     * @param asc Si <code>true</code>, le tri est ascendant sur le prix, si
     *            <code>false</code>, le tri est descendant.
     */
    List<Product> getSortedProducts(Boolean asc);

    /**
     * Pour le service n°4.
     *
     * @param category La catégorie sur laquelle filtrer.
     * @param asc      Si <code>true</code>, le tri est ascendant sur le prix, si
     *                 <code>false</code>, le tri est descendant.
     */
    List<Product> getSortedProductsOfCategory(String category, Boolean asc);

    /**
     * Pour le service n°6.
     *
     * @param productId L'identifiant du produit à supprimer.
     */
    void deleteProduct(Long productId);

    /**
     * Pour le service n°7.
     *
     * @param product Le produit à créer.
     */
    void createProduct(Product product);

    /**
     * Pour le service n°8.
     *
     * @param product Le produit à modifier (modification totale).
     */
    void updateProduct(Product product);

    /**
     * Pour le service n°9.
     *
     * @param productId L'identifiant du produit à modifier (modification
     *                  partielle).
     * @param patch     Les modifications à apporter.
     */
    void patchProduct(Long productId, Product patch);

    /**
     * Pour le service n°10.
     *
     * @param productId L'identifiant du produit dont on souhaite récupérer les
     *                  commentaires.
     */
    List<Comment> getProductComments(Long productId);
}
