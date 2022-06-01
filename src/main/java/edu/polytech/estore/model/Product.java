package edu.polytech.estore.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entité JPA représentant un produit.
 *
 */
@Entity
@XmlRootElement
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String label;
    private String category;
    private Double priceInEuro;
    private Integer stock;
    @Transient
    private List<Comment> comments = null;

    public Product() {
    }

    public Product(String label, String category, Double priceInEuro, Integer stock) {
        this.label = label;
        this.category = category;
        this.priceInEuro = priceInEuro;
        this.stock = stock;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPriceInEuro() {
        return priceInEuro;
    }

    public void setPriceInEuro(Double priceInEuro) {
        this.priceInEuro = priceInEuro;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
