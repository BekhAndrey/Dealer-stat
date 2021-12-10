package com.bekh.dealerstat.service;

import com.bekh.dealerstat.model.GameObject;
import com.bekh.dealerstat.repository.GameObjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service("gameObjectService")
public class GameObjectService {

    private final GameObjectRepository gameObjectRepository;

    public GameObjectService(GameObjectRepository gameObjectRepository) {
        this.gameObjectRepository = gameObjectRepository;
    }

    public List<GameObject> findAll() {
        return (List<GameObject>) gameObjectRepository.findAll();
    }

    public GameObject findById(Long id) {
        return gameObjectRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find the resource"));
    }

    public void save(GameObject gameObject) {
        gameObjectRepository.save(gameObject);
    }

    public void delete(GameObject gameObject) {
        gameObjectRepository.delete(gameObject);
    }

    public List<GameObject> findAllByAuthorId(Long authorId) {
        return gameObjectRepository.findAllByAuthorId(authorId);
    }

    public GameObject findByIdAndAuthorId(Long id, Long authorId) {
        return gameObjectRepository.findByIdAndAuthorId(id, authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find the resource"));
    }

    public List<GameObject> findAllByApproved(Boolean approved) {
        return gameObjectRepository.findAllByApproved(approved);
    }
}
