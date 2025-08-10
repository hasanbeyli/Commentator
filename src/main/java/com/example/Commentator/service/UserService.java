package com.example.Commentator.service;

import com.example.Commentator.dto.UserResponseDto;
import com.example.Commentator.entity.User;
import com.example.Commentator.mapper.UserMapper;
import com.example.Commentator.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepo userRepo;


    @Transactional
    public UserResponseDto create(String username) {
        User savedUser = userRepo.save(new User(null, username));
        return userMapper.toUserResponseDto(savedUser);
    }

    public List<UserResponseDto> findAllUsers() {
        List<User> all = userRepo.findAll();
        return userMapper.toUserResponseDtoList(all);
    }

    @Transactional
    public UserResponseDto updateUser(Long userId, String username) {
        return userRepo.findAll().stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .map(user -> {
                    user.setUsername(username);
                    return userRepo.save(user);
                })
                .map(userMapper::toUserResponseDto)
                .orElseThrow(() -> new IllegalArgumentException("User doesn't exist"));
    }

    public User findById(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User with user id = "+ userId + " not found" ));
    }

    @Transactional
    public void delete(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));
        userRepo.delete(user);
    }

}
