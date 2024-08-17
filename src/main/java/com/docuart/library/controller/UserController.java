package com.docuart.library.controller;

import com.docuart.library.entity.User;
import com.docuart.library.service.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(userServices.findAll(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody User user){

        return new ResponseEntity<>(userServices.add(user),HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<?> update(@Valid  @RequestParam Long userId, @RequestBody User user){
        return new ResponseEntity<>(userServices.update(userId,user),HttpStatus.CREATED);
    }
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam Long userId){
        return new ResponseEntity<>(userServices.delete(userId), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getUsersCount(){
        return new ResponseEntity<>(userServices.getcountusers(),HttpStatus.OK);
    }



}
