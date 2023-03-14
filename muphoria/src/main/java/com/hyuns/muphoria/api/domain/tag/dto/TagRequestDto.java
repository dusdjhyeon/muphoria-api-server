package com.hyuns.muphoria.api.domain.tag.dto;

import com.hyuns.muphoria.api.domain.tag.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

public class TagRequestDto {
    @ApiModel(value = "태그 등록")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Register {
        @NotEmpty @Size(min = 2, max = 128, message = "길이는 2 ~ 128자 이내여야 합니다.")
        @ApiModelProperty(value = "태그 이름")
        private String name;

        public Tag toEntity() {
            return Tag.builder()
                    .name(this.name)
                    .build();
        }
    }
}