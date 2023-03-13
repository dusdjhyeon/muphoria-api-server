package com.hyuns.muphoria.api.domain.tag.dto;

import com.hyuns.muphoria.api.domain.tag.Tag;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class TagResponseDto {
    @ApiModel(value = "태그 정보")
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    static public class Info {
        private Integer id;
        private String name;

        public static Info of(Tag tag) {
            return Info.builder()
                    .id(tag.getId())
                    .name(tag.getName())
                    .build();
        }

        public static List<Info> of(List<Tag> tags) {
            return tags.stream().map(Info::of).collect(Collectors.toList());
        }
    }
}
