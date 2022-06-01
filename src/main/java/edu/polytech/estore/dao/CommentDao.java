package edu.polytech.estore.dao;

import java.util.List;

import edu.polytech.estore.model.Comment;

public interface CommentDao {

    public List<Comment> getComments(Long productId);

    public Comment getComment(Long commentId);

    public void deleteComments(Long productId);
}
