package com.Hms.controller;

import com.Hms.entity.UserApp;
import com.Hms.payload.LoginDto;
import com.Hms.payload.UserAppDto;
import com.Hms.service.UserAppService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v6/user")
public class UserAppController {
    private UserAppService userAppService;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(
            @RequestBody UserAppDto userAppDto
    ){
        return userAppService.createUser(userAppDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable("id") long id
    ){
        userAppService.deleteById(id);
        return new ResponseEntity<>("Record Deleted", HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<UserAppDto> updateUser(
            @RequestParam("id") long id,
            @RequestBody UserAppDto userAppDto
    ){
        return userAppService.updateUser(id,userAppDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAppDto> findUser(
            @PathVariable("id") long id
    ){
        return userAppService.findUserById(id);
    }

    @GetMapping
    public ResponseEntity<List<UserAppDto>> listAll(){
        return userAppService.listUser();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginDto loginDto
    ){
        boolean status = userAppService.verifyLogin(loginDto);
        if(status){
            return new ResponseEntity<>("User Logged in",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid Username/Password",HttpStatus.FORBIDDEN);
        }
    }
}


