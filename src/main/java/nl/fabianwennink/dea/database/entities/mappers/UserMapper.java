package nl.fabianwennink.dea.database.entities.mappers;

import nl.fabianwennink.dea.controllers.login.dto.LoginResponseDTO;
import nl.fabianwennink.dea.database.entities.User;
import nl.fabianwennink.dea.exceptions.MethodNotImplementedException;

import java.util.List;

public class UserMapper implements Mapper<User, LoginResponseDTO> {

    private static UserMapper mapper;

    @Override
    public User convertToEntity(LoginResponseDTO dto) {
        User user = new User();
        user.setUsername(dto.getUser());
        user.setToken(dto.getToken());

        return null;
    }

    @Override
    public LoginResponseDTO convertToDTO(User entity) {
        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setUser(entity.getName());
        dto.setToken(entity.getToken());

        return dto;
    }

    @Override
    public List<User> convertToEntity(List<LoginResponseDTO> dtos) throws MethodNotImplementedException {
        throw new MethodNotImplementedException();
    }

    @Override
    public List<LoginResponseDTO> convertToDTO(List<User> entities) throws MethodNotImplementedException {
        throw new MethodNotImplementedException();
    }

    public static UserMapper getInstance() {
        if(mapper == null) {
            mapper = new UserMapper();
        }

        return mapper;
    }
}
