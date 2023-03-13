package com.hyuns.muphoria.api.domain.user.service;

import com.hyuns.muphoria.api.domain.user.Role;
import com.hyuns.muphoria.api.domain.user.User;
import com.hyuns.muphoria.api.domain.user.dto.UserRequestDto;
import com.hyuns.muphoria.api.domain.user.dto.UserResponseDto;
import com.hyuns.muphoria.api.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.security.core.Authentication;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    protected final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserResponseDto.Default> getAllUser() {
        return UserResponseDto.Default.of(userRepository.findAll());
    }

    @Transactional(readOnly = true)
    public UserResponseDto.Profile getUserById(Integer id){
        return UserResponseDto.Profile.of(userRepository.getAllInfoOfUserById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."
                        )
                ));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto.Default> searchUser(String userName){
        return UserResponseDto.Default.of(userRepository.findByName(userName));
    }

    @Transactional(readOnly = true)
    public UserResponseDto.Default getMyDefaultProfile() {
        return UserResponseDto.Default.of(userRepository.findById(getCurrentUser().getId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."
                )
        ));
    }

    @Transactional(readOnly = true)
    public UserResponseDto.Profile getMyProfile(){
        return UserResponseDto.Profile.of(userRepository.findById(getCurrentUser().getId()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."
                )
        ));
    }

    @Transactional
    public UserResponseDto.Profile register(UserRequestDto.Register data) {
        User user = getCurrentUser();

        if(user.getRole() != Role.GUEST) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "손님 권한을 가진 사람만 등록할 수 있습니다."
            );
        }
        user.setName(data.getName());
        user.setIntroduction(data.getIntroduction());
        user.setProfileImage(data.getProfileImage());
        user.setBio(data.getBio());
        user.setRole(Role.USER);

        return UserResponseDto.Profile.of(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal().equals("annoymousUser")) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "로그인 되지 않았습니다."
            );
        }

        return (User) authentication.getPrincipal();
    }
}
