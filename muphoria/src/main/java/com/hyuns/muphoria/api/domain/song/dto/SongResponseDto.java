package com.hyuns.muphoria.api.domain.song.dto;

import com.hyuns.muphoria.api.domain.song.Song;
import com.hyuns.muphoria.api.domain.song.SongTag;
import com.hyuns.muphoria.api.domain.tag.dto.TagResponseDto;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SongResponseDto {
    @ApiModel(value = "노래 요약 정보")
    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Simple {
        private Integer id;
        private String title;
        // 앨범 사진
        private Integer like;
        private User user;
        private Set<TagResponseDto.Info> tags;

        public static Simple of(Song song) {
            return Simple.builder()
                    .id(song.getId())
                    .title(song.getTitle())
                    .like(song.getLike())
                    .user(song.getUser())
                    .tags(song.getTags()
                            .stream()
                            .map(TagResponseDto.Info::of)
                            .collect(Collectors.toSet())
        }

        public static List<Simple> of(List<Song> songs) {
            return songs.stream().map(Simple::of).collect(Collectors.toList());
        }
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tag {
        private Integer id;
        private String title;
        private String lyrics;
        private Integer like;
        private User user;

        public static Tag of(SongTag songTag) {
            Song song = songTag.getSong();

            return Tag.builder()
                    .id(song.getId())
                    .title(song.getTitle())
                    .lyrics(song.getLyrics())
                    .like(song.getLike())
                    .user(song.getUser())
                    .build();
        }

        public static List<Tag> of(List<SongTag> songs) {
            return songs.stream().map(Tag::of).collect(Collectors.toList());
        }
    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Detail {
        private Integer id;
        private String title;
        private String lyrics;
        private Integer like;
        private List<CommentResponseDto.Info> comments;
        private User user;
        private List<TagResponseDto.Info> tags;

        public static Detail of(Song song) {
            return Detail.builder()
                    .id(song.getId())
                    .title(song.getTitle())
                    .lyrics(song.getLyrics())
                    .like(song.getLike())
                    .comments(song.getComments()
                            .stream()
                            .map(CommentResponseDto.Song::of)
                            .collect(Collectors.toList()))
                    .build();
        }


    }
}
