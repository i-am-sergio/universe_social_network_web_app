package com.unsa.backend.authtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.unsa.backend.auth.AuthUserResponse;
import com.unsa.backend.users.Role;
import com.unsa.backend.users.UserModel;

@SpringBootTest
class AuthUserResponseTest {

    private static String token = "your_token";
    private static Long id = 1L;
    private static String username = "john_doe";
    private static String firstname = "John";
    private static String lastname = "Doe";
    private static String profilePicture = "profile.jpg";
    private static String coverPicture = "cover.jpg";
    private static String about = "about";
    private static String livesIn = "livesIn";
    private static String worksAt = "worksAt";
    private static String relationship = "relationship";
    private static String country = "country";
    private static Role role = Role.USER;
    private static List<Long> followers = null;
    private static List<Long> following = null;

    @Test
    void builderTest() {
        UserModel user = UserModel.builder()
                .id(1L)
                .username(username)
                .firstname(firstname)
                .lastname(lastname)
                .isAdmin(false)
                .profilePicture(profilePicture)
                .build();

        AuthUserResponse authUserResponse = AuthUserResponse.from(user, token);

        assertThat(authUserResponse.getToken()).isEqualTo(token);
        assertThat(authUserResponse.getId()).isEqualTo(user.getId());
        assertThat(authUserResponse.getUsername()).isEqualTo(user.getUsername());
        assertThat(authUserResponse.getFirstname()).isEqualTo(user.getFirstname());
        assertThat(authUserResponse.getLastname()).isEqualTo(user.getLastname());
        assertThat(authUserResponse.isAdmin()).isEqualTo(user.isAdmin());
        assertThat(authUserResponse.getProfilePicture()).isEqualTo(user.getProfilePicture());
        assertThat(authUserResponse.getRole()).isEqualTo(user.getRole());
        assertThat(authUserResponse.getFollowers()).isEqualTo(user.getFollowers());
        assertThat(authUserResponse.getFollowing()).isEqualTo(user.getFollowing());
    }

    @Test
    void settersTest() {
        AuthUserResponse authUserResponse = AuthUserResponse.builder().build();

        authUserResponse.setToken(token);
        authUserResponse.setId(2L);
        authUserResponse.setUsername(username);
        authUserResponse.setFirstname(firstname);
        authUserResponse.setLastname(lastname);
        authUserResponse.setAdmin(true);
        authUserResponse.setProfilePicture(profilePicture);

        assertThat(authUserResponse.getToken()).isEqualTo(token);
        assertThat(authUserResponse.getId()).isEqualTo(2L);
        assertThat(authUserResponse.getUsername()).isEqualTo(username);
        assertThat(authUserResponse.getFirstname()).isEqualTo(firstname);
        assertThat(authUserResponse.getLastname()).isEqualTo(lastname);
        assertThat(authUserResponse.isAdmin()).isTrue();
        assertThat(authUserResponse.getProfilePicture()).isEqualTo(profilePicture);
    }
    @Test
    void allArgsConstructorTest() {
        boolean isAdmin = true;

        AuthUserResponse authUserResponse = 
            new AuthUserResponse(token,id,username,firstname,lastname,
                                isAdmin,profilePicture,coverPicture,about,
                                livesIn,worksAt,relationship,country,role,followers,following);

        assertThat(authUserResponse.getToken()).isEqualTo(token);
        assertThat(authUserResponse.getId()).isEqualTo(id);
        assertThat(authUserResponse.getUsername()).isEqualTo(username);
        assertThat(authUserResponse.getFirstname()).isEqualTo(firstname);
        assertThat(authUserResponse.getLastname()).isEqualTo(lastname);
        assertThat(authUserResponse.isAdmin()).isEqualTo(isAdmin);
        assertThat(authUserResponse.getProfilePicture()).isEqualTo(profilePicture);
        assertThat(authUserResponse.getCoverPicture()).isEqualTo(coverPicture);
        assertThat(authUserResponse.getAbout()).isEqualTo(about);
        assertThat(authUserResponse.getLivesIn()).isEqualTo(livesIn);
        assertThat(authUserResponse.getWorksAt()).isEqualTo(worksAt);
        assertThat(authUserResponse.getRelationship()).isEqualTo(relationship);
        assertThat(authUserResponse.getCountry()).isEqualTo(country);
        assertThat(authUserResponse.getRole()).isEqualTo(role);
        assertThat(authUserResponse.getFollowers()).isEqualTo(followers);
        assertThat(authUserResponse.getFollowing()).isEqualTo(following);
    }

    @Test
    void noArgsConstructorTest() {
        AuthUserResponse authUserResponse = new AuthUserResponse();
        assertThat(authUserResponse).isNotNull(); 
    }
}
