package ru.practicum.ewm.user.service;


import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(NewUserRequest userRequest);

    UserDto getUserById(long id);

    List<UserDto> getListUsers(List<Long> ids, int from, int size);

    void deleteUser(long id);
}
