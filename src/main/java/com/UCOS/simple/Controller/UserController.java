package com.UCOS.simple.Controller;

import com.UCOS.simple.DTO.UserInfo;
import com.UCOS.simple.Exception.DuplicateUsernameException;
import com.UCOS.simple.Service.UserService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserInfo>> getUsers(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody UserInfo userInfo){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.createUser(userInfo));
        } catch (DuplicateUsernameException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }

    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username){
        try{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.deleteUser(username));
        } catch(UsernameNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @GetMapping("/greeting")
    public ResponseEntity<String> getGreeting(HttpSession session) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body("Hello "+session.getAttribute("loggedInUsername"));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
