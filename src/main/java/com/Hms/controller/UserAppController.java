package com.Hms.controller;

import com.Hms.payload.UserAppDto;
import com.Hms.service.UserAppService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
