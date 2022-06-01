package edu.polytech.estore.dao;

import java.util.List;

import edu.polytech.estore.model.Comment;

public interface CommentDao {

    List<Comment> getComments(Long productId);

    Comment getComment(Long commentId);

    void deleteComments(Long productId);
}
