package com.hyuns.muphoria.api.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

public class UserRequestDto {
    @ApiModel(value = "유저 정보 등록")
    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Register {
        @NotEmpty
        @Size(min = 4, max = 32, message = "길이는 4~32자 이내여야 합니다.")
        @ApiModelProperty(value = "유저 이름", required = true)
        private String name;

        @ApiModelProperty(value = "유저 프로필 사진")
        private String profileImage;

        @ApiModelProperty(value = "유저 소개")
        private String introduction;

        @ApiModelProperty(value = "유저 링크ㅑ")
        private String bio;
    }
}
