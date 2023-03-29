package com.rrlira96.mygamesmanagement.service;

import com.rrlira96.mygamesmanagement.entities.MyGame;
import com.rrlira96.mygamesmanagement.entities.Profile;
import com.rrlira96.mygamesmanagement.entities.Review;
import com.rrlira96.mygamesmanagement.repository.ProfileRepository;
import com.rrlira96.mygamesmanagement.service.exceptions.ResourceAlreadyExistsException;
import com.rrlira96.mygamesmanagement.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private GameService gameService;

    private final String FINISHED_FILTER = "filter[finished]";
    private final String ALL_ACHIEVEMENTS_FILTER = "filter[allAchievements]";


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

    public List<MyGame> getGamesOfProfile(String profileId, Map<String, String> filter) {
        Optional<Profile> profile = profileRepository.findById(profileId);
        List<MyGame> myGames = profile.get().getGames();
        if (!filter.isEmpty()) {
            if (filter.containsKey(FINISHED_FILTER)) {
                myGames = myGames.stream()
                        .filter(myGame -> myGame.isFinished() == Boolean.parseBoolean(filter.get(FINISHED_FILTER)))
                        .collect(Collectors.toList());
            } else if (filter.containsKey(ALL_ACHIEVEMENTS_FILTER)) {
                myGames = myGames.stream()
                        .filter(myGame -> myGame.isAllAchievements() == Boolean.parseBoolean(filter.get(ALL_ACHIEVEMENTS_FILTER)))
                        .collect(Collectors.toList());
            }
        }
        return myGames;
    }


    // todo: add logic to support list of userGames
    public Profile updateGamesOnProfile(String profileId, MyGame myGame) {
        Optional<Profile> profile = profileRepository.findById(profileId);
        List<MyGame> profileGames = profile.get().getGames();

        if (!profileGames.contains(myGame)) {
            profileGames.add(myGame);
        } else {
            throw new ResourceAlreadyExistsException(myGame.getGame().getName());
        }
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

    public Profile addReview(String profileId, String gameId, Review review) {
        Optional<Profile> profile = profileRepository.findById(profileId);
        List<MyGame> myGames = profile.get().getGames();

        for (MyGame myGame : myGames) {
            if (myGame.getGame().getId().equals(gameId)) {
                myGame.setReview(review);
                profileRepository.save(profile.get());
                break;
            }
        }
        return profile.get();
    }

    public Profile updateMyGameFlags(String profileId, String gameId, Map<String, Boolean> fields) {
        Optional<Profile> profile = profileRepository.findById(profileId);
        List<MyGame> myGames = profile.get().getGames();

        for (MyGame myGame : myGames) {
            if (myGame.getGame().getId().equals(gameId)) {
                for (String field : fields.keySet()) {
                    if (field.equals("finished")) {
                        myGame.setFinished(fields.get("finished"));
                    } else {
                        myGame.setAllAchievements(fields.get("allAchievements"));
                    }
                }
                profileRepository.save(profile.get());
                break;
            }
        }
        return profile.get();
    }
}
