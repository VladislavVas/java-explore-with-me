package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.Create;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> postUser(@RequestBody @Validated(Create.class) NewUserRequest userRequest) {
        log.info("POST UserController " + userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequest));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getListUsers(@RequestParam(required = false) List<Long> ids,
                                                      @RequestParam(defaultValue = "0") int from,
                                                      @RequestParam(defaultValue = "10") int size) {
        log.info("GET UserController by ids =" + ids);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getListUsers(ids, from, size));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Min(1) long userId) {
        userService.deleteUser(userId);
        log.info("DELETE UserController id=" + userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
