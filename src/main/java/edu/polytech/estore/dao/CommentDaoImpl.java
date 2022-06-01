package edu.polytech.estore.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import edu.polytech.estore.model.Comment;

@Stateless
public class CommentDaoImpl implements CommentDao {

    @PersistenceContext(unitName = "EStore")
    private EntityManager em;

    @Override
    public List<Comment> getComments(Long productId) {
        Query request = em.createQuery("select c from Comment c where c.product.productId = ?1");
        request.setParameter(1, productId);
        return request.getResultList();
    }

    @Override
    public Comment getComment(Long commentId) {
        return em.find(Comment.class, commentId);
    }

    @Override
    public void deleteComments(Long productId) {
        List<Comment> comments = getComments(productId);
        for (Comment comment : comments) {
            em.remove(comment);
        }
    }

}
