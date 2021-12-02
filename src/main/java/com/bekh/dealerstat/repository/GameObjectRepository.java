package com.bekh.dealerstat.repository;

import com.bekh.dealerstat.model.Comment;
import com.bekh.dealerstat.model.GameObject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GameObjectRepository extends CrudRepository<GameObject, Long> {

    List<GameObject> findAllByAuthorId(Long authorId);

    Optional<GameObject> findByIdAndAuthorId(Long id, Long authorId);
}
