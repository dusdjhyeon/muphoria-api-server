package com.hyuns.muphoria.api.domain.tag.controller;

import com.hyuns.muphoria.api.domain.song.dto.SongResponseDto;
import com.hyuns.muphoria.api.domain.tag.dto.TagResponseDto;
import com.hyuns.muphoria.api.domain.tag.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Tag Controller"})
@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @ApiOperation("태그를 등록합니다.")
    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<String> postTag(@ApiParam(value = "태그 이름", required = true) @RequestParam String tagName) {
        return ResponseEntity.status(HttpStatus.CREATED).body("/tag/" + tagService.postTag(tagName));
    }

    @ApiOperation("태그를 삭제합니다.")
    @DeleteMapping(value = "/delete/{tagId}")
    public ResponseEntity<Void> deleteTag(@ApiParam(value = "태그 ID", required = true) @PathVariable final Integer tagId) {
        tagService.deleteTag(tagId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("태그 전체 목록을 보여줍니다.")
    @GetMapping(value = "/list")
    public ResponseEntity<List<TagResponseDto.Info>> getTagById() {
        return ResponseEntity.ok(tagService.getAllTag());
    }

    @ApiOperation("해당 태그를 가진 노래들을 반환합니다.")
    @GetMapping(value = "/songList/{tagId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<SongResponseDto.Simple>> getSongListForTag(@ApiParam(value = "태그 ID", required = true) @PathVariable final Integer tagId) {
        return ResponseEntity.ok(tagService.getSongListForTag(tagId));
    }

    @ApiOperation("태그 이름을 통해 검색합니다.")
    @GetMapping(value = "/search")
    public ResponseEntity<List<TagResponseDto.Info>> searchTag(@ApiParam(value = "태그 이름", required = true) @RequestParam String tagName) {
        return ResponseEntity.ok(tagService.findByNameContains(tagName));
    }

}
