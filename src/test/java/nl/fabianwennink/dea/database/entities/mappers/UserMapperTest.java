package nl.fabianwennink.dea.database.entities.mappers;

import nl.fabianwennink.dea.controllers.login.dto.LoginResponseDTO;
import nl.fabianwennink.dea.database.entities.User;
import nl.fabianwennink.dea.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserMapperTest {

    private List<User> users = new ArrayList<>();
    private List<LoginResponseDTO> dtos = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        // Fill the users list with dummy data
        users.add(createUser(1, "Some User", "someusername"));
        users.add(createUser(2, "Another User", "anotherusername"));
        users.add(createUser(3, "Yet Another User", "yetanotherusername"));

        // Fill the dtos list with dummy data
        users.forEach(user -> dtos.add(createResponseDTO(user.getName(), user.getToken())));
    }

    @Test
    public void should_ReturnLoginDTO_IfMappedCorrectly() {
        User user = users.get(0);
        LoginResponseDTO response = UserMapper.getInstance().convertToDTO(user);

        Assertions.assertEquals(response.getUser(), user.getName());
        Assertions.assertEquals(response.getToken(), user.getToken());
    }

    @Test
    public void should_ReturnUserEntity_IfMappedCorrectly() {
        LoginResponseDTO dto = dtos.get(0);
        User response = UserMapper.getInstance().convertToEntity(dto);

        Assertions.assertEquals(response.getName(), dto.getUser());
        Assertions.assertEquals(response.getToken(), dto.getToken());
    }

    @Test
    public void should_ReturnLoginDTOs_IfMappedCorrectly() {
        List<LoginResponseDTO> mappedDTOs = UserMapper.getInstance().convertToDTO(users);

        Assertions.assertEquals(mappedDTOs.size(), users.size());

        for(int i = 0; i < mappedDTOs.size(); i++) {
            Assertions.assertEquals(mappedDTOs.get(i).getUser(), users.get(i).getName());
            Assertions.assertEquals(mappedDTOs.get(i).getToken(), users.get(i).getToken());
        }
    }

    @Test
    public void should_ReturnUserEntities_IfMappedCorrectly() {
        List<User> mappedEntities = UserMapper.getInstance().convertToEntity(dtos);

        Assertions.assertEquals(mappedEntities.size(), dtos.size());

        for(int i = 0; i < mappedEntities.size(); i++) {
            Assertions.assertEquals(mappedEntities.get(i).getName(), dtos.get(i).getUser());
            Assertions.assertEquals(mappedEntities.get(i).getToken(), dtos.get(i).getToken());
        }
    }

    private User createUser(int id, String name, String username) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setUsername(username);
        user.setToken(UUID.randomUUID().toString());

        return user;
    }

    private LoginResponseDTO createResponseDTO(String name, String token) {
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setUser(name);
        dto.setToken(token);

        return dto;
    }
}
