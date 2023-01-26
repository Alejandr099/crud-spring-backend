package com.wszib.aw.crud.controller;

import com.wszib.aw.crud.ResourceNotFoundException;
import com.wszib.aw.crud.model.User;
import com.wszib.aw.crud.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aw")
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Create
    @PostMapping("/users")
    public User createUser(@Validated @RequestBody User user) {
        return userRepo.save(user);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") long userId) throws ResourceNotFoundException {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for id: " + userId));

        return ResponseEntity.ok().body(user);
    }

    // Update
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") long userId, @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for id: " + userId));
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmailId(userDetails.getEmailId());
        userRepo.save(user);

        return ResponseEntity.ok().body(user);
    }

    // Delete
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") long userId) throws ResourceNotFoundException {
        userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for id: " + userId));
        userRepo.deleteById(userId);

        return ResponseEntity.ok().build();
    }
}
