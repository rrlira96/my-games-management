package com.rrlira96.mygamesmanagement.service;

import com.rrlira96.mygamesmanagement.entities.Game;
import com.rrlira96.mygamesmanagement.entities.Profile;
import com.rrlira96.mygamesmanagement.repository.ProfileRepository;
import com.rrlira96.mygamesmanagement.service.exceptions.ResourceAlreadyExistsException;
import com.rrlira96.mygamesmanagement.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private GameService gameService;

    // todo implement DTO
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    public Profile getProfileById(String id) {
        Optional<Profile> profile = profileRepository.findById(id);
        return profile.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Profile addProfile(Profile profile) {
        try {
            return profileRepository.save(profile);
        } catch (DataIntegrityViolationException exception) {
            throw new ResourceAlreadyExistsException(profile.getName());
        }
    }


    public Profile addGamesOnProfile(String profileId, List<Game> newGames) {
        Optional<Profile> profile = profileRepository.findById(profileId);
        List<Game> gameList = profile.get().getAllGames();

        List<Game> gamesToAdd = newGames.stream()
                .filter(newGame -> !gameList.contains(newGame))
                .collect(Collectors.toList());

        gameList.addAll(gamesToAdd);
        profile.get().setAllGames(gameList);
        return profileRepository.save(profile.get());
    }

    public void deleteProfile(String id) {
        Optional<Profile> profile = profileRepository.findById(id);
        if (profile.isPresent()) {
            profileRepository.deleteById(id);
            return;
        }
        throw new ResourceNotFoundException(id);
    }

}
