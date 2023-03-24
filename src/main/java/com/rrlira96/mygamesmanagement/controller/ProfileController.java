package com.rrlira96.mygamesmanagement.controller;

import com.rrlira96.mygamesmanagement.entities.Game;
import com.rrlira96.mygamesmanagement.entities.Profile;
import com.rrlira96.mygamesmanagement.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<List<Profile>> getAllProfiles() {
        return ResponseEntity.ok().body(profileService.getAllProfiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable String id) {
        return ResponseEntity.ok().body(profileService.getProfileById(id));
    }

    @PostMapping
    public ResponseEntity<Profile> addProfile(@RequestBody Profile obj) {
        Profile profile = profileService.addProfile(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(profile.getId()).toUri();
        return ResponseEntity.created(uri).body(profile);
    }

    @PostMapping("/{id}/games")
    public ResponseEntity<Profile> addGamesOnProfile(@PathVariable String id, @RequestBody List<Game> games) {
        Profile profile = profileService.addGamesOnProfile(id, games);
        return ResponseEntity.ok().body(profile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Profile> deleteProfile(@PathVariable String id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

}
