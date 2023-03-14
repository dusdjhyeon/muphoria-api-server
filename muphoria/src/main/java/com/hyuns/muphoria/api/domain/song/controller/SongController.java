package com.hyuns.muphoria.api.domain.song.controller;

import com.hyuns.muphoria.api.domain.song.dto.SongRequestDto;
import com.hyuns.muphoria.api.domain.song.dto.SongResponseDto;
import com.hyuns.muphoria.api.domain.song.service.SongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Api(tags = {"Song Controller"})
@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;

    @ApiOperation("노래를 등록합니다.")
    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<String> postSong(@Valid @RequestBody final SongRequestDto.Register data) {
        return ResponseEntity.status(HttpStatus.CREATED).body("/song/" + songService.postSong(data));
    }

    @ApiOperation("노래를 삭제합니다.")
    @DeleteMapping(value = "/delete/{songId}")
    public ResponseEntity<Void> deleteSong(@ApiParam(value = "노래 ID", required = true) @PathVariable final Integer songId) {
        songService.deleteSong(songId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("해당 아이디를 가진 노래의 상세 정보를 반환합니다.")
    @GetMapping(value = "/{songId}")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<SongResponseDto.Detail> getSongDetails(@ApiParam(value = "노래 ID", required = true) @PathVariable final Integer songId) {
        return ResponseEntity.ok(songService.getSongDetails(songId));
    }

    @ApiOperation("노래를 제목을 통해 검색합니다.")
    @GetMapping(value = "/searchTitle")
    public ResponseEntity<List<SongResponseDto.Simple>> searchTitle(@ApiParam(value = "노래 제목", required = true) @RequestParam String songTitle) {
        return ResponseEntity.ok(songService.searchTitle(songTitle));
    }

    @ApiOperation("해당 유저가 등록한 모든 노래를 보여줍니다.")
    @GetMapping(value = "/list/{songId}")
    public ResponseEntity<List<SongResponseDto.Simple>> getAllMySongs(@ApiParam(value = "유저 ID", required = true) @PathVariable final Integer userId) {
        return ResponseEntity.ok(songService.getAllMySongs(userId));
    }
}
