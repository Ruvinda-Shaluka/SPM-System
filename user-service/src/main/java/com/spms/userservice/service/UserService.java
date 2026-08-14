package com.spms.userservice.service;

import com.spms.userservice.model.User;
import com.spms.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User authenticateUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    public User getUserProfile(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUserProfile(Long id, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(id);

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setName(updatedUser.getName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public String getBookingHistory(Long id) {
        User user = getUserProfile(id);
        return (user != null) ? user.getBookingHistory() : null;
    }
}