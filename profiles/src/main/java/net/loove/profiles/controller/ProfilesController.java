package net.loove.profiles.controller;

import java.util.List;
import net.loove.profiles.service.ProfilesService;
import net.loove.profiles.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/profiles")
public class ProfilesController {

    private final ProfilesService service;

    @Autowired
    public ProfilesController(ProfilesService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfile(@PathVariable Long id) {
        return this.service.getProfile(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Profile> getProfiles() {
        return this.service.getProfiles();
    }

    @PostMapping
    public Profile createProfile(@Validated Profile profile) {
        return this.service.createProfile(profile);
    }


}
