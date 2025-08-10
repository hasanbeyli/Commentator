package com.example.Commentator.controller;

import com.example.Commentator.dto.UserResponseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Commentator.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping()
    public List<UserResponseDto> getAllUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestParam @NotBlank String username) {
        UserResponseDto userResponseDto = userService.create(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @PutMapping("/{userId}")
    public UserResponseDto update(@PathVariable Long userId, @RequestParam String username) {
        return userService.updateUser(userId, username);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long userId) {

        userService.delete(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User with ID " + userId + " has been deleted successfully");
        return ResponseEntity.ok(response);
    }
}
