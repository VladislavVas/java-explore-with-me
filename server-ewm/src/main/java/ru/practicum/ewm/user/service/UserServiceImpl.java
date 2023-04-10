package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
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
    public UserDto getUserById(long id) {
        return userMapper.toUserDto(getUserFromRepository(id));
    }

    @Override
    public List<UserDto> getListUsers(List<Long> ids, int from, int size) {
        return userMapper.toListUserDto(userRepository.getByIds(ids, size, from));
    }

    @Override
    public void deleteUser(long id) {
            userRepository.deleteById(id);
    }

    private User getUserFromRepository(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id=%" + id + "was not found"));
    }

//    private int getPage(int from, int size) {
//        if (from < 0 || size <= 0) {
//            throw new ValidateException("Invalid page or size parameters");
//        } else {
//            return from / size;
//        }
//    }
}
