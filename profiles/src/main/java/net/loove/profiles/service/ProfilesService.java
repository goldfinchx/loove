package net.loove.profiles.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.loove.profiles.model.Profile;
import net.loove.profiles.repository.ProfilesRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@CacheConfig(cacheNames = "profiles")
@RequiredArgsConstructor
public class ProfilesService {

    private final ProfilesRepository repository;

    @CachePut(key = "#id", unless = "#result == null")
    public Optional<Profile> getProfile(Long id) {
        return this.repository.findById(id);
    }

    @CachePut(key = "#profile.id")
    public Profile createProfile(Profile profile) {
        return this.repository.save(profile);
    }

    @CachePut(key = "#profile.id")
    public Profile updateProfile(Profile profile) {
        return this.repository.save(profile);
    }

    public void deleteProfile(Long id) {
        this.repository.deleteById(id);
    }

    @CacheEvict(allEntries = true)
    public List<Profile> getProfiles() {
        return this.repository.findAll();
    }

}
