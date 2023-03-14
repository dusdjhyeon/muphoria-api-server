package com.hyuns.muphoria.api.domain.album.dto;

import com.hyuns.muphoria.api.domain.album.Album;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AlbumResponseDto {
    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Default {
        private Integer id;
        private String title;
        private String coverImage;
        public static Default of(Album album) {
            return Default.builder()
                    .id(album.getId())
                    .title(album.getTitle())
                    .coverImage(album.getCoverImage())
                    .build();
        }
        public static List<Default> of(List<Album> albums) {
            return albums.stream().map(Default::of).collect(Collectors.toList());
        }
    }

    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail {
        private Integer id;
        private String title;
        private String coverImage;
        private LocalDate release;
        private String description;

        public static Detail of(Album album) {
            return Detail.builder()
                    .id(album.getId())
                    .title(album.getTitle())
                    .coverImage(album.getCoverImage())
                    .release(album.getReleaseDate())
                    .description(album.getDescription())
                    .build();
        }
    }
}
