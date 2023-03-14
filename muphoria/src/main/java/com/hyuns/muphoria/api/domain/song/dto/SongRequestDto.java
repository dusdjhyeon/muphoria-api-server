package com.hyuns.muphoria.api.domain.song.dto;

import com.hyuns.muphoria.api.domain.song.Song;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

public class SongRequestDto {

    @ApiModel(value = "노래 등록")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Register {
        @NotEmpty
        @Size(min = 2, max = 255, message = "제목 길이를 확인해주세요.")
        @ApiModelProperty(value = "노래 이름")
        private String title;

        @ApiModelProperty(value = "가사")
        private String lyrics;

        @Size(min = 2, max = 128, message = "길이는 2 ~ 128자 이내여야 합니다.")
        @ApiModelProperty(value = "태그 이름")
        private String tag;

        public Song toEntity() {
            return Song.builder()
                    .title(this.title)
                    .lyrics(this.lyrics)
                    .tag(this.tag)
                    .build();
        }
    }
}
