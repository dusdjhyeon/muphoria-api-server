package com.hyuns.muphoria.api.domain.user.dto;

import com.hyuns.muphoria.api.domain.user.Role;
import com.hyuns.muphoria.api.domain.user.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class UserResponseDto {
    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Default {
        private Integer id;
        private String nickname;
        private String profileImage;

        public static Default of(User user) {
            return Default.builder()
                    .id(user.getId())
                    .nickname(user.getName())
                    .profileImage(user.getProfileImage())
                    .build();
        }

        public static List<Default> of(List<User> users) {
            return users.stream().map(Default::of).collect(Collectors.toList());
        }
    }

    //playlist를 위한 play에 대한 dto 필요
    
    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Profile {
        private Integer id;
        private String email;
        private String name;
        private String introduction;
        private String profileImage;
        private String bio;
        private Role role;
        // playlist를 위한 play에 대한 변수 필요

        public static Profile of(User user) {
            return Profile.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .introduction(user.getIntroduction())
                    .profileImage(user.getProfileImage())
                    .bio(user.getBio())
                    .role(user.getRole())
                    .build();
        }
    }
}
