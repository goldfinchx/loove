package net.loove.profiles.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.json.JsonMapper;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.loove.profiles.model.Profile;
import net.loove.profiles.service.ProfilesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

@Slf4j
@WebAppConfiguration
@WebMvcTest(ProfilesController.class)
class ProfilesControllerTest {

    @MockBean
    private ProfilesService service;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Autowired
    private MockMvc mvc;

    private final JsonMapper jsonMapper = new JsonMapper();


    @Test
    void getProfile_returnsProfile_whenProfileExists() throws Exception {
        final Profile profile = new Profile();
        profile.setId(1L);

        when(this.service.getProfile(1L)).thenReturn(Optional.of(profile));

        final String profileJson = this.jsonMapper.writeValueAsString(profile);

        this.mvc.perform(MockMvcRequestBuilders.get("/api/v1/profiles/1"))
            .andExpect(status().isOk())
            .andExpect(content().json(profileJson));

    }

    @Test
    void getProfile_returnsNotFound_whenProfileDoesntExist() throws Exception {
        when(this.service.getProfile(-1L)).thenReturn(Optional.empty());
        this.mvc.perform(MockMvcRequestBuilders.get("/api/v1/profiles/-1"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getProfiles_returnsListOfProfiles() throws Exception {
        final Profile profile1 = new Profile();
        profile1.setId(1L);
        final Profile profile2 = new Profile();
        profile2.setId(2L);
        final String profilesJson = this.jsonMapper.writeValueAsString(List.of(profile1, profile2));

        when(this.service.getProfiles()).thenReturn(List.of(profile1, profile2));

        this.mvc.perform(MockMvcRequestBuilders.get("/api/v1/profiles"))
            .andExpect(status().isOk())
            .andExpect(content().json(profilesJson));
    }

    @Test
    void createProfile_returnsCreatedProfile() throws Exception {
        final Profile profile = new Profile();
        final Profile createdProfile = new Profile();
        createdProfile.setId(1L);
        when(this.service.createProfile(profile)).thenReturn(createdProfile);

        final String profileJson = this.jsonMapper.writeValueAsString(profile);
        final String createdProfileJson = this.jsonMapper.writeValueAsString(createdProfile);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/profiles")
            .contentType(MediaType.APPLICATION_JSON)  // Set the Content-Type header
            .content(profileJson);

        this.mvc.perform(requestBuilder)
            .andExpect(status().isCreated())
            .andExpect(content().json(createdProfileJson));
    }

    @Test
    void createProfile_returnsBadRequest_whenAgeIsUnder18() throws Exception {
        final Profile profile = new Profile();
        profile.setAge(17);

        final String profileJson = this.jsonMapper.writeValueAsString(profile);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/profiles")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(profileJson);

        this.mvc.perform(requestBuilder).andExpect(status().isBadRequest());
    }

}