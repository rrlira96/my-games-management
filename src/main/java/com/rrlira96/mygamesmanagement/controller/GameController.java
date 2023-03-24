package com.rrlira96.mygamesmanagement.controller;

import com.rrlira96.mygamesmanagement.entities.Game;
import com.rrlira96.mygamesmanagement.entities.Profile;
import com.rrlira96.mygamesmanagement.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        return ResponseEntity.ok().body(gameService.getAllGames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable String id) {
        return ResponseEntity.ok().body(gameService.getGameById(id));
    }

    @PostMapping
    public ResponseEntity<Game> addGame(@RequestBody Game obj) {
        Game game = gameService.addGame(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(game.getId()).toUri();
        return ResponseEntity.created(uri).body(game);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable String id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }


}
