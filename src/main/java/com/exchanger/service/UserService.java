package com.exchanger.service;

import com.exchanger.dto.UserRequest;
import com.exchanger.dto.UserResponse;
import com.exchanger.exception.NotUniqueUserException;
import com.exchanger.exception.UserNotFoundException;
import com.exchanger.mapper.UserMapper;
import com.exchanger.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UUID createUser(@Valid UserRequest request) {
        validateUser(request);
        return userRepository.save(userMapper.toEntity(request)).getId();
    }

    private void validateUser(@Valid UserRequest request) {
        if (!userRepository.findAllByEmailOrPhone(request.email(), request.phone()).isEmpty()) {
            throw new NotUniqueUserException();
        }
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    public UserResponse getUserById(UUID id) {
        return userMapper.toDto(
                userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("User by id: %s not found", id))));
    }
}
