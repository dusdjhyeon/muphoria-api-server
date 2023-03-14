package com.hyuns.muphoria.api.domain.album.dto;

import com.hyuns.muphoria.api.domain.album.Album;
import com.hyuns.muphoria.api.domain.user.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class AlbumRequestDto {
    @ApiModel(value="그룹 등록")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Register {
        @NotEmpty @Size(min = 1, max = 128, message = "길이는 1 ~ 128자 이내여야 합니다.")
        @ApiModelProperty(value = "앨범 이름")
        private String title;

        @ApiModelProperty(value = "앨범 커버")
        private String coverImage;

        @ApiModelProperty(value = "앨범 설명")
        private String description;
    }
}
