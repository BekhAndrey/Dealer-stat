package com.bekh.dealerstat.repository;

import com.bekh.dealerstat.model.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    Comment findByAuthorId(Long authorId);

    Optional<Comment> findByIdAndAuthorId(Long id, Long authorId);

    Comment findByTraderId(Long traderId);

    List<Comment> findAllByTraderId(Long traderId);

    Optional<Comment> findByIdAndTraderId(Long id, Long traderId);

    List<Comment> findAllByApproved(Boolean approved);

    List<Comment> findAllByTraderIdAndApproved(Long traderId, Boolean approved);
}
