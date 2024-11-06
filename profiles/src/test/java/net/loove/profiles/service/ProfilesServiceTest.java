package net.loove.profiles.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import net.loove.profiles.model.Profile;
import net.loove.profiles.repository.ProfilesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProfilesServiceTest {

    @Mock
    private ProfilesRepository repository;

    @InjectMocks
    private ProfilesService service;

    private Profile profile;

    @BeforeEach
    void setUp() {
        this.profile = Profile.builder()
            .id(1L)
            .age(18)
            .name("Test Profile")
            .build();
    }

    @Test
    @Order(1)
    void createProfile_returnsProfile_whenProfileIsCreated() {
        when(this.repository.save(this.profile)).thenReturn(this.profile);
        final Profile savedProfile = this.service.createProfile(this.profile);
        assertEquals(this.profile, savedProfile);
    }

    @Test
    @Order(2)
    void getProfile_returnsProfile_whenProfileExists() {
        when(this.service.getProfile(this.profile.getId())).thenReturn(Optional.of(this.profile));

        final Optional<Profile> result = this.service.getProfile(this.profile.getId());
        final Profile profile = result.orElse(null);
        assertNotNull(profile);
    }

    @Test
    @Order(3)
    void getProfile_returnsEmpty_whenProfileDoesNotExist() {
        when(this.service.getProfile(-this.profile.getId())).thenReturn(Optional.empty());

        final Optional<Profile> result = this.service.getProfile(-this.profile.getId());
        assertEquals(Optional.empty(), result);
    }

    @Test
    @Order(4)
    void getProfiles_returnsProfiles() {
        when(this.service.getProfiles()).thenReturn(List.of(this.profile));
        assertFalse(this.service.getProfiles().isEmpty());
    }

    @Test
    @Order(5)
    void updateProfile_returnsUpdatedProfile() {
        this.profile.setAge(20);
        when(this.repository.save(this.profile)).thenReturn(this.profile);

        final Profile updatedProfile =  this.service.updateProfile(this.profile);
        assertEquals(20, updatedProfile.getAge());
    }

    @Test
    @Order(6)
    void deleteProfile_deletesProfile() {
        doNothing().when(this.repository).deleteById(this.profile.getId());
        this.service.deleteProfile(this.profile.getId());

        verify(this.repository).deleteById(this.profile.getId());
    }



}