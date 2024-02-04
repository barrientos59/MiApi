package com.example.MiApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS= "/users";

    @Autowired
    UserController userController;
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> users(){

        return new ResponseEntity<>( userController.readAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> user(@PathVariable Integer id){
        return new ResponseEntity<>( userController.getUserById(id), HttpStatus.OK);
    }
    @GetMapping("/{id}/email")
    public ResponseEntity<Map<String,String>> email(@PathVariable Integer id){
        return new ResponseEntity<>(Collections.singletonMap(
                                                "email",
                                                userController.getUserById(id).getEmail()),
                                    HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDto> newUser(@RequestBody User user){
        return  ResponseEntity.ok(userController.addUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody User u){
        Integer id = u.getId();
        User updateUser = userService.updateUser(id, u);
        if (updateUser != null){
            return ResponseEntity.ok(new UserDto(updateUser));
        }return ResponseEntity.notFound().build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateParcial(@PathVariable Integer id,@RequestBody Map<String, Object> upd){
        User updateUser = userController.updateParcial(id,upd);
        if (updateUser != null){
            return ResponseEntity.ok(new UserDto(updateUser));
        }return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        userController.removeUSer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }






}
