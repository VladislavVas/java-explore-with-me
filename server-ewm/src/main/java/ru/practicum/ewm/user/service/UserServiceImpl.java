package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(NewUserRequest userRequest) {
        User user = userMapper.toUser(userRequest);
        return userMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto getUserById(Long id) {
        return userMapper.toUserDto(getUserFromRepository(id));
    }

    @Override
    public List<UserDto> getListUsers(List<Long> ids, Integer from, Integer size) {
        return userMapper.toListUserDto(userRepository.findAllByIdIn(ids, PageRequest.of(from / size, size)));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User getUserFromRepository(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=%" + id + "was not found"));
    }

}
