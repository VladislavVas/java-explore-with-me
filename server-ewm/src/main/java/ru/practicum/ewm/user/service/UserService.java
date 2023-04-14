package ru.practicum.ewm.user.service;


import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(NewUserRequest userRequest);

    UserDto getUserById(Long id);

    List<UserDto> getListUsers(List<Long> ids, Integer from, Integer size);

    void deleteUser(Long id);
}
