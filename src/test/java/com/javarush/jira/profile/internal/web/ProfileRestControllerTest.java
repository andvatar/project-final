package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.login.internal.web.UserController;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import com.javarush.jira.profile.internal.model.Profile;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.profile.internal.web.ProfileRestController.REST_URL;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static com.javarush.jira.login.internal.web.UserTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class ProfileRestControllerTest extends AbstractControllerTest {
    @Autowired
    private ProfileRepository profileRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(user.id()));
    }

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void getNotExisting() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(guest.id()));
                //.andExpect(PROFILE_MATCHER.contentJson(ProfileTestData.getNew(guest.id())));
    }
    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getUpdatedTo())))
                .andDo(print())
                .andExpect(status().isNoContent());

        Profile profileAfter = profileRepository.getOrCreate(user.id());

        PROFILE_MATCHER.assertMatch(profileAfter, getUpdated(user.id()));
    }

    @Test
    void updateUnauthorized() throws Exception {

        Profile profileBefore = profileRepository.getOrCreate(user.id());

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getUpdatedTo())))
                .andDo(print())
                .andExpect(status().isUnauthorized());

        Profile profileAfter = profileRepository.getOrCreate(user.id());
        PROFILE_MATCHER.assertMatch(profileAfter, profileBefore);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateInvalid() throws Exception {

        Profile profileBefore = profileRepository.getOrCreate(user.id());

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getInvalidTo())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        Profile profileAfter = profileRepository.getOrCreate(user.id());
        PROFILE_MATCHER.assertMatch(profileAfter, profileBefore);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateUnknownNotification() throws Exception {

        Profile profileBefore = profileRepository.getOrCreate(user.id());

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getWithUnknownNotificationTo())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        Profile profileAfter = profileRepository.getOrCreate(user.id());
        PROFILE_MATCHER.assertMatch(profileAfter, profileBefore);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateUnknownContact() throws Exception {

        Profile profileBefore = profileRepository.getOrCreate(user.id());

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getWithUnknownContactTo())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        Profile profileAfter = profileRepository.getOrCreate(user.id());
        PROFILE_MATCHER.assertMatch(profileAfter, profileBefore);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateContactHtmlUnsafe() throws Exception {

        Profile profileBefore = profileRepository.getOrCreate(user.id());

        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getWithContactHtmlUnsafeTo())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

        Profile profileAfter = profileRepository.getOrCreate(user.id());
        PROFILE_MATCHER.assertMatch(profileAfter, profileBefore);
    }
}