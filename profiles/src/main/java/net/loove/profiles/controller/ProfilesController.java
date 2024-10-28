package net.loove.profiles.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.loove.profiles.service.ProfilesService;
import net.loove.profiles.model.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
@RestController
public class ProfilesController {

    private final ProfilesService service;

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
    public ResponseEntity<Profile> createProfile(@Validated @RequestBody Profile profile) {
        final Profile createdProfile = this.service.createProfile(profile);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }


}
