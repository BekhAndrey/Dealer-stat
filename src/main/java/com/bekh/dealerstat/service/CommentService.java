package com.bekh.dealerstat.service;

import com.bekh.dealerstat.model.Comment;
import com.bekh.dealerstat.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service("commentService")
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findAll() {
        return (List<Comment>) commentRepository.findAll();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    public Comment findByAuthorId(Long authorId) { return commentRepository.findByAuthorId(authorId);}

    public Comment findByIdAndAuthorId(Long id, Long authorId) {
        return commentRepository.findByIdAndAuthorId(id, authorId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find the resource"));
    }

    public Comment findByTraderId(Long traderId) { return commentRepository.findByTraderId(traderId);}

    public List<Comment> findAllByTraderId(Long traderId){ return commentRepository.findAllByTraderId(traderId);}

    public Comment findByIdAndTraderId(Long id, Long traderId) {
        return commentRepository.findByIdAndTraderId(id, traderId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find the resource"));
    }
}
