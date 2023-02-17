package com.hyuns.muphoria.api.domain.user.controller;

import com.hyuns.muphoria.api.domain.user.dto.UserRequestDto;
import com.hyuns.muphoria.api.domain.user.dto.UserResponseDto;
import com.hyuns.muphoria.api.domain.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"User Controller"})
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation("전체 유저 리스트를 반환 합니다.")
    @GetMapping(value = "/list")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<UserResponseDto.Default>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @ApiOperation("해당 아이디를 가진 유저의 정보를 반환 합니다.")
    @GetMapping(value = "/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserResponseDto.Profile> getUserById(@ApiParam(value="유저 ID", required = true)
                                                               @PathVariable @Valid final Integer userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @ApiOperation("현재 유저의 요악 정보를 반환 합니다.")
    @GetMapping(value = "/my_profile/default")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserResponseDto.Default> getMyDefaultProfile(){
        return ResponseEntity.ok(userService.getMyDefaultProfile());
    }

    @ApiOperation("현재 유저의 전체 정보를 반환 합니다.")
    @GetMapping(value = "/my_profile/info")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserResponseDto.Profile> getMyProfile(){
        return ResponseEntity.ok(userService.getMyProfile());
    }

    @ApiOperation("현재 유저를 회원으로 등록 합니다.")
    @PostMapping(value = "/register")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserResponseDto.Profile> register(@ApiParam(value = "유저 정보")
                                                            @Valid @RequestBody UserRequestDto.Register data){
        return ResponseEntity.ok(userService.register(data));
    }

    @ApiOperation("유저 이름을 통해 검색합니다.")
    @GetMapping(value = "/search")
    public ResponseEntity<List<UserResponseDto.Default>> searchUser(@ApiParam(value = "유저 이름", required = true) @RequestParam String userName) {
        return ResponseEntity.ok(userService.searchUser(userName));
    }
}
