package org.example.steps;

import net.serenitybdd.annotations.Step;
import org.assertj.core.api.Assertions;
import org.example.entity.UserEntity;
import org.example.model.UserDTO;
import org.example.security.JwtUtil;
import org.example.service.GreetingService;
import org.example.service.UserServiceImpl;
import org.example.util.Constants;

import java.util.List;

public class UnitSteps {

    private GreetingService greetingService;
    private UserServiceImpl userService;
    private JwtUtil jwtUtil;

    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // --- Greeting steps ---

    @Step("Greeting for '{0}' returns 'Hello, {0}'")
    public String greetingReturnsHello(String name) {
        String result = greetingService.getGreeting(name);
        Assertions.assertThat(result).isEqualTo("Hello, " + name);
        return result;
    }

    @Step("Greeting result starts with 'Hello, '")
    public void greetingResultStartsWithHello(String result) {
        Assertions.assertThat(result).startsWith("Hello, ");
    }

    @Step("Greeting result contains name '{0}'")
    public void greetingResultContainsName(String result, String name) {
        Assertions.assertThat(result).contains(name);
    }

    @Step("Greeting result is not null or blank")
    public void greetingResultIsNotBlank(String result) {
        Assertions.assertThat(result).isNotNull().isNotBlank();
    }

    @Step("Greeting result has length greater than zero")
    public void greetingResultHasLength(String result) {
        Assertions.assertThat(result).hasSizeGreaterThan(0);
    }

    @Step("Greeting result equals expected value '{0}'")
    public void greetingResultEquals(String result, String expected) {
        Assertions.assertThat(result).isEqualTo(expected);
    }

    // --- JWT steps ---

    @Step("JWT token is generated for username '{0}'")
    public String generateJwtToken(String username) {
        String token = jwtUtil.generateToken(username);
        Assertions.assertThat(token).isNotNull().isNotBlank();
        return token;
    }

    @Step("JWT token is not null or blank")
    public void jwtTokenIsNotBlank(String token) {
        Assertions.assertThat(token).isNotNull().isNotBlank();
    }

    @Step("JWT username extracted from token equals '{0}'")
    public void jwtExtractedUsernameEquals(String token, String expected) {
        Assertions.assertThat(jwtUtil.extractUsername(token)).isEqualTo(expected);
    }

    @Step("JWT token is valid for username '{0}'")
    public void jwtTokenIsValid(String token, String username) {
        Assertions.assertThat(jwtUtil.isTokenValid(token, username)).isTrue();
    }

    @Step("JWT token is invalid for wrong username '{0}'")
    public void jwtTokenIsInvalidForUsername(String token, String wrongUsername) {
        Assertions.assertThat(jwtUtil.isTokenValid(token, wrongUsername)).isFalse();
    }

    @Step("JWT token has three dot-separated parts")
    public void jwtTokenHasThreeParts(String token) {
        Assertions.assertThat(token.split("\\.")).hasSize(3);
    }

    // --- UserDTO steps ---

    @Step("UserDTO is built with username '{0}' and email '{1}'")
    public UserDTO buildUserDTO(Long id, String username, String email) {
        return new UserDTO(id, username, email);
    }

    @Step("UserDTO id is null")
    public void userDTOIdIsNull(UserDTO dto) {
        Assertions.assertThat(dto.getId()).isNull();
    }

    @Step("UserDTO id equals {0}")
    public void userDTOIdEquals(UserDTO dto, Long expected) {
        Assertions.assertThat(dto.getId()).isEqualTo(expected);
    }

    @Step("UserDTO username equals '{0}'")
    public void userDTOUsernameEquals(UserDTO dto, String expected) {
        Assertions.assertThat(dto.getUsername()).isEqualTo(expected);
    }

    @Step("UserDTO email equals '{0}'")
    public void userDTOEmailEquals(UserDTO dto, String expected) {
        Assertions.assertThat(dto.getEmail()).isEqualTo(expected);
    }

    @Step("UserDTO username is not blank")
    public void userDTOUsernameIsNotBlank(UserDTO dto) {
        Assertions.assertThat(dto.getUsername()).isNotNull().isNotBlank();
    }

    @Step("UserDTO email contains '@'")
    public void userDTOEmailContainsAt(UserDTO dto) {
        Assertions.assertThat(dto.getEmail()).contains("@");
    }

    @Step("UserDTO setter updates username to '{0}'")
    public void userDTOSetUsername(UserDTO dto, String username) {
        dto.setUsername(username);
        Assertions.assertThat(dto.getUsername()).isEqualTo(username);
    }

    @Step("UserDTO setter updates email to '{0}'")
    public void userDTOSetEmail(UserDTO dto, String email) {
        dto.setEmail(email);
        Assertions.assertThat(dto.getEmail()).isEqualTo(email);
    }

    @Step("UserDTO setter updates id to {0}")
    public void userDTOSetId(UserDTO dto, Long id) {
        dto.setId(id);
        Assertions.assertThat(dto.getId()).isEqualTo(id);
    }

    // --- UserEntity steps ---

    @Step("UserEntity is built with username '{0}' and email '{1}'")
    public UserEntity buildUserEntity(String username, String email) {
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setEmail(email);
        return entity;
    }

    @Step("UserEntity with constructor sets username '{0}' and email '{1}'")
    public UserEntity buildUserEntityWithConstructor(String username, String email, String password) {
        return new UserEntity(username, email, password);
    }

    @Step("UserEntity username equals '{0}'")
    public void userEntityUsernameEquals(UserEntity entity, String expected) {
        Assertions.assertThat(entity.getUsername()).isEqualTo(expected);
    }

    @Step("UserEntity email equals '{0}'")
    public void userEntityEmailEquals(UserEntity entity, String expected) {
        Assertions.assertThat(entity.getEmail()).isEqualTo(expected);
    }

    @Step("UserEntity id is null before persisting")
    public void userEntityIdIsNull(UserEntity entity) {
        Assertions.assertThat(entity.getId()).isNull();
    }

    @Step("UserEntity setter updates username to '{0}'")
    public void userEntitySetUsername(UserEntity entity, String username) {
        entity.setUsername(username);
        Assertions.assertThat(entity.getUsername()).isEqualTo(username);
    }

    @Step("UserEntity setter updates email to '{0}'")
    public void userEntitySetEmail(UserEntity entity, String email) {
        entity.setEmail(email);
        Assertions.assertThat(entity.getEmail()).isEqualTo(email);
    }

    @Step("UserEntity password equals '{0}'")
    public void userEntityPasswordEquals(UserEntity entity, String expected) {
        Assertions.assertThat(entity.getPassword()).isEqualTo(expected);
    }

    // --- Constants steps ---

    @Step("GREETING_KEY equals 'greeting'")
    public void greetingKeyEqualsGreeting() {
        Assertions.assertThat(Constants.GREETING_KEY).isEqualTo("greeting");
    }

    @Step("AUTHOR_KEY equals 'author'")
    public void authorKeyEqualsAuthor() {
        Assertions.assertThat(Constants.AUTHOR_KEY).isEqualTo("author");
    }

    @Step("VERSION_KEY equals 'version'")
    public void versionKeyEqualsVersion() {
        Assertions.assertThat(Constants.VERSION_KEY).isEqualTo("version");
    }

    @Step("All constants are not null")
    public void allConstantsAreNotNull() {
        Assertions.assertThat(Constants.GREETING_KEY).isNotNull();
        Assertions.assertThat(Constants.AUTHOR_KEY).isNotNull();
        Assertions.assertThat(Constants.VERSION_KEY).isNotNull();
    }

    @Step("All constants are not blank")
    public void allConstantsAreNotBlank() {
        Assertions.assertThat(Constants.GREETING_KEY).isNotBlank();
        Assertions.assertThat(Constants.AUTHOR_KEY).isNotBlank();
        Assertions.assertThat(Constants.VERSION_KEY).isNotBlank();
    }

    @Step("Constant '{0}' is not null")
    public void constantIsNotNull(String value) {
        Assertions.assertThat(value).isNotNull();
    }

    @Step("Constant equals '{0}'")
    public void constantEquals(String actual, String expected) {
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    // --- UserService steps ---

    @Step("UserService creates user with username '{0}'")
    public UserDTO createUser(String username, String email) {
        return userService.createUser(new UserDTO(null, username, email));
    }

    @Step("UserService retrieves user by id")
    public UserDTO getUserById(Long id) {
        return userService.getUserById(id);
    }

    @Step("UserService updates user with new username '{1}'")
    public UserDTO updateUser(Long id, String username, String email) {
        return userService.updateUser(id, new UserDTO(null, username, email));
    }

    @Step("UserService deletes user without throwing")
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }

    @Step("UserService returns list of all users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @Step("UserService throws RuntimeException for missing user id")
    public void getUserByIdThrowsForMissingId(Long id) {
        Assertions.assertThatThrownBy(() -> userService.getUserById(id))
                .isInstanceOf(RuntimeException.class);
    }

    @Step("UserService throws RuntimeException when deleting non-existent user")
    public void deleteUserThrowsForMissingId(Long id) {
        Assertions.assertThatThrownBy(() -> userService.deleteUser(id))
                .isInstanceOf(RuntimeException.class);
    }

    @Step("Returned UserDTO has id not null")
    public void returnedUserDTOHasId(UserDTO dto) {
        Assertions.assertThat(dto.getId()).isNotNull();
    }

    @Step("Returned UserDTO has username '{0}'")
    public void returnedUserDTOHasUsername(UserDTO dto, String expected) {
        Assertions.assertThat(dto.getUsername()).isEqualTo(expected);
    }

    @Step("Returned UserDTO has email '{0}'")
    public void returnedUserDTOHasEmail(UserDTO dto, String expected) {
        Assertions.assertThat(dto.getEmail()).isEqualTo(expected);
    }

    @Step("Users list is not null")
    public void usersListIsNotNull(List<UserDTO> users) {
        Assertions.assertThat(users).isNotNull();
    }
}
