package com.hyuns.muphoria.api.domain.user.service;

import com.hyuns.muphoria.api.domain.user.User;
import com.hyuns.muphoria.api.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


public class UserDebugService extends UserService{
    private final HttpServletRequest request;

    public UserDebugService(UserRepository userRepository, HttpServletRequest request) {
        super(userRepository);
        this.request = request;
    }

    private Integer getUserIdInHeader(){
        String userIdString = request.getHeader("userId");

        if(userIdString == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        try {
            return Integer.parseInt(userIdString);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId를 파싱할 수 없습니다.");
        }
    }

    @Override
    public User getCurrentUser() {
        return userRepository.getById(getUserIdInHeader());
    }
}
