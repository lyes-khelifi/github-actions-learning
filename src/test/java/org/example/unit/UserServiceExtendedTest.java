package org.example.unit;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.example.entity.UserEntity;
import org.example.model.UserDTO;
import org.example.repository.UserRepository;
import org.example.service.UserServiceImpl;
import org.example.steps.UnitSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SerenityJUnit5Extension.class)
@Tag("unit")
@WithTagValuesOf({"unit", "user"})
public class UserServiceExtendedTest {

    @Steps
    UnitSteps unitSteps;

    private UserRepository userRepository;
    private long nextId;

    @BeforeEach
    void setUp() {
        nextId = 1L;
        userRepository = mock(UserRepository.class);
        when(userRepository.save(any())).thenAnswer(invocation -> {
            UserEntity entity = invocation.getArgument(0);
            if (entity.getId() == null) {
                entity.setId(nextId++);
            }
            return entity;
        });
        unitSteps.setUserService(new UserServiceImpl(userRepository));
    }

    @Test
    void createUserReturnsIdNotNull() {
        UserDTO result = unitSteps.createUser("alice", "alice@example.com");
        unitSteps.returnedUserDTOHasId(result);
    }

    @Test
    void createUserReturnsCorrectUsername() {
        UserDTO result = unitSteps.createUser("bob", "bob@example.com");
        unitSteps.returnedUserDTOHasUsername(result, "bob");
    }

    @Test
    void createUserReturnsCorrectEmail() {
        UserDTO result = unitSteps.createUser("carol", "carol@example.com");
        unitSteps.returnedUserDTOHasEmail(result, "carol@example.com");
    }

    @Test
    void getUserByIdReturnsCorrectUsername() {
        UserDTO created = unitSteps.createUser("dave", "dave@example.com");
        UserEntity entity = new UserEntity();
        entity.setId(created.getId());
        entity.setUsername("dave");
        entity.setEmail("dave@example.com");
        when(userRepository.findById(created.getId())).thenReturn(Optional.of(entity));
        UserDTO fetched = unitSteps.getUserById(created.getId());
        unitSteps.returnedUserDTOHasUsername(fetched, "dave");
    }

    @Test
    void getUserByIdReturnsCorrectEmail() {
        UserDTO created = unitSteps.createUser("eve", "eve@example.com");
        UserEntity entity = new UserEntity();
        entity.setId(created.getId());
        entity.setUsername("eve");
        entity.setEmail("eve@example.com");
        when(userRepository.findById(created.getId())).thenReturn(Optional.of(entity));
        UserDTO fetched = unitSteps.getUserById(created.getId());
        unitSteps.returnedUserDTOHasEmail(fetched, "eve@example.com");
    }

    @Test
    void getMissingUserThrowsRuntimeException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        unitSteps.getUserByIdThrowsForMissingId(99L);
    }

    @Test
    void updateUserChangesUsername() {
        UserDTO created = unitSteps.createUser("frank", "frank@example.com");
        UserEntity entity = new UserEntity();
        entity.setId(created.getId());
        entity.setUsername("frank-updated");
        entity.setEmail("frank@example.com");
        when(userRepository.findById(created.getId())).thenReturn(Optional.of(entity));
        UserDTO updated = unitSteps.updateUser(created.getId(), "frank-updated", "frank@example.com");
        unitSteps.returnedUserDTOHasUsername(updated, "frank-updated");
    }

    @Test
    void updateUserChangesEmail() {
        UserDTO created = unitSteps.createUser("grace", "grace@example.com");
        UserEntity entity = new UserEntity();
        entity.setId(created.getId());
        entity.setUsername("grace");
        entity.setEmail("grace-new@example.com");
        when(userRepository.findById(created.getId())).thenReturn(Optional.of(entity));
        UserDTO updated = unitSteps.updateUser(created.getId(), "grace", "grace-new@example.com");
        unitSteps.returnedUserDTOHasEmail(updated, "grace-new@example.com");
    }

    @Test
    void deleteUserDoesNotThrow() {
        UserDTO created = unitSteps.createUser("henry", "henry@example.com");
        when(userRepository.existsById(created.getId())).thenReturn(true);
        doNothing().when(userRepository).deleteById(created.getId());
        unitSteps.deleteUser(created.getId());
    }

    @Test
    void deleteMissingUserThrowsRuntimeException() {
        when(userRepository.existsById(99L)).thenReturn(false);
        unitSteps.deleteUserThrowsForMissingId(99L);
    }

    @Test
    void getAllUsersReturnsNonNullList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        List<UserDTO> users = unitSteps.getAllUsers();
        unitSteps.usersListIsNotNull(users);
    }

    @Test
    void getAllUsersReturnsPopulatedList() {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setUsername("ivy");
        entity.setEmail("ivy@example.com");
        when(userRepository.findAll()).thenReturn(List.of(entity));
        List<UserDTO> users = unitSteps.getAllUsers();
        unitSteps.usersListIsNotNull(users);
    }

}
