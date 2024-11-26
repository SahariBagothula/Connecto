package com.social.connecto.servicelayers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.connecto.models.User;
import com.social.connecto.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> findUserById(int userid) {
        return userRepository.findById(userid);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> followUser(int userid1, int userid2) {

        Optional<User> user1 = findUserById(userid1);
        Optional<User> user2 = findUserById(userid2);

        if (user1.isPresent() && user2.isPresent()) {
            user2.get().getFollowers().add(user1.get().getId());
            userRepository.save(user2.get());

            user1.get().getFollowing().add(user2.get().getId());
            userRepository.save(user1.get());
        }

        return user1;

    }

    public User updateUser(User user) {
        User updatedUser = userRepository.findById(user.getId()).orElse(null);
        updatedUser.setFirstname(user.getFirstname());
        updatedUser.setLastname(user.getLastname());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setGender(user.getGender());
        updatedUser.setFollowers(user.getFollowers());
        updatedUser.setFollowing(user.getFollowing());
        userRepository.save(updatedUser);
        return updatedUser;
    }

    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }

    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public void deleteUser(int userid) {
        userRepository.deleteById(userid);
    }

}
