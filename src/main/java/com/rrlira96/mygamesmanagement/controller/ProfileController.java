package com.rrlira96.mygamesmanagement.controller;

import com.rrlira96.mygamesmanagement.entities.Profile;
import com.rrlira96.mygamesmanagement.entities.Review;
import com.rrlira96.mygamesmanagement.entities.MyGame;
import com.rrlira96.mygamesmanagement.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Profile> deleteProfile(@PathVariable String id) {
        profileService.deleteProfile(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/my-games")
    public ResponseEntity<List<MyGame>> getGamesOfProfile(@PathVariable String id,
                                                          @RequestParam Map<String, String> filter) {
        return ResponseEntity.ok().body(profileService.getGamesOfProfile(id, filter));
    }

    @PutMapping("/{id}/my-games")
    public ResponseEntity<Profile> updateGamesOnProfile(@PathVariable String id, @RequestBody MyGame myGame) {
        Profile profile = profileService.updateGamesOnProfile(id, myGame);
        return ResponseEntity.ok().body(profile);
    }

    @PostMapping("/{profileId}/my-games/{gameId}/review")
    public ResponseEntity<Profile> addReviewOnGame(@PathVariable String profileId,
                                                   @PathVariable String gameId,
                                                   @RequestBody Review review) {

        Profile profile = profileService.addReview(profileId, gameId, review);
        return ResponseEntity.ok().body(profile);
    }

    @PatchMapping("/{profileId}/my-games/{gameId}/review")
    public ResponseEntity<Profile> updateMyGameFlags(@PathVariable String profileId,
                                                           @PathVariable String gameId,
                                                           @RequestBody Map<String, Boolean> fields) {

        Profile profile = profileService.updateMyGameFlags(profileId, gameId, fields);
        return ResponseEntity.ok().body(profile);
    }


}
