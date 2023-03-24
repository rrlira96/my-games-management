package com.rrlira96.mygamesmanagement.service;

import com.rrlira96.mygamesmanagement.entities.Game;
import com.rrlira96.mygamesmanagement.entities.Profile;
import com.rrlira96.mygamesmanagement.repository.GameRepository;
import com.rrlira96.mygamesmanagement.service.exceptions.ResourceAlreadyExistsException;
import com.rrlira96.mygamesmanagement.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    // todo implement DTO
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(String id) {
        Optional<Game> game = gameRepository.findById(id);
        return game.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Game getGameByName(String name) {
        Optional<Game> game = Optional.ofNullable(gameRepository.findByName(name));
        return game.orElseThrow(() -> new ResourceNotFoundException(name));
    }

    public Game addGame(Game game) {
        try {
            return gameRepository.save(game);
        } catch (DataIntegrityViolationException exception) {
            throw new ResourceAlreadyExistsException(game.getName());
        }
    }

    public void deleteGame(String id) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isPresent()) {
            gameRepository.deleteById(id);
            return;
        }
        throw new ResourceNotFoundException(id);
    }



}
