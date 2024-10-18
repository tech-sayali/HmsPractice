package com.Hms.service;

import com.Hms.entity.UserApp;
import com.Hms.payload.UserAppDto;
import com.Hms.repository.UserAppRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserAppService {
    private UserAppRepository userAppRepository;
    private ModelMapper modelMapper;

    public ResponseEntity<?> createUser(UserAppDto userAppDto) {
        Optional<UserApp> username = userAppRepository.findByUsername(userAppDto.getUsername());

        if(username.isPresent()){
            return new ResponseEntity<>("Username already Present", HttpStatus.INTERNAL_SERVER_ERROR);
    }

        Optional<UserApp> password = userAppRepository.findByPassword(userAppDto.getPassword());

        if(password.isPresent()){
            return new ResponseEntity<>("Password already Present",HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UserApp user=mapToEntity(userAppDto);

        user.setPassword(BCrypt.hashpw(userAppDto.getPassword(),BCrypt.gensalt(4)));
        UserApp save = userAppRepository.save(user);
        UserAppDto dto = mapToDto(save);

        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    UserApp mapToEntity(UserAppDto userAppDto){
        UserApp user = modelMapper.map(userAppDto, UserApp.class);
        return user;
    }

    UserAppDto mapToDto(UserApp userApp){
        UserAppDto dto = modelMapper.map(userApp, UserAppDto.class);
        return dto;
    }

    public void deleteById(long id) {
        userAppRepository.deleteById(id);
    }

    public ResponseEntity<UserAppDto> updateUser(long id,UserAppDto userAppDto) {
        UserApp user = userAppRepository.findById(id).get();
        user.setName(userAppDto.getName());
        user.setEmail(user.getEmail());
        user.setUsername(userAppDto.getUsername());
        user.setPassword(BCrypt.hashpw(userAppDto.getPassword(),BCrypt.gensalt(4)));
        UserApp save = userAppRepository.save(user);
        UserAppDto dto = mapToDto(save);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    public ResponseEntity<UserAppDto> findUserById(long id) {
        UserApp user = userAppRepository.findById(id).get();
        UserAppDto dto = mapToDto(user);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    public ResponseEntity<List<UserAppDto>> listUser() {
        List<UserApp> all = userAppRepository.findAll();
        List<UserAppDto> collect = all.stream().map(this::mapToDto).collect(Collectors.toList());
        return new ResponseEntity<>(collect,HttpStatus.OK);
    }
}
