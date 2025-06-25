package com.UCOS.simple.Service;

import com.UCOS.simple.DTO.UserInfo;
import com.UCOS.simple.Entity.User;
import com.UCOS.simple.Exception.DuplicateUsernameException;
import com.UCOS.simple.Repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not present"));
    }

    public Boolean userExists(String username) {
        return userRepository.findUserByUsername(username)
                .isPresent();
    }

    public List<UserInfo> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(User::toUserInfo)
                .toList();
    }

    public String deleteUser(String username) {
        if (!userExists(username)) {
            throw new UsernameNotFoundException("The username " + username + " was not found");
        }
        Long deleteCount = userRepository.deleteByUsername(username);
        if (deleteCount > 0) {
            return "User " + username + " successfully deleted";
        }

        return "Unable to delete user with username " + username;

    }

    public String createUser(UserInfo userInfo) throws DuplicateUsernameException {

        if (userExists(userInfo.getUsername())) {
            throw new DuplicateUsernameException("The username " + userInfo.getUsername() + "already exists");
        }
        User user = new User(userInfo);
        userRepository.save(user);
        return "New user " + userInfo.getUsername() + " successfully created";

    }
}
