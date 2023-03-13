package com.hyuns.muphoria.api.domain.album.controller;

import com.hyuns.muphoria.api.domain.album.dto.AlbumRequestDto;
import com.hyuns.muphoria.api.domain.album.dto.AlbumResponseDto;
import com.hyuns.muphoria.api.domain.album.service.AlbumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Album Controller"})
@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @ApiOperation("현재 유저의 앨범 리스트를 반환 합니다.")
    @GetMapping(value = "/list")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<AlbumResponseDto.Default>> getAllAlbum(){
        return ResponseEntity.ok(albumService.getAllAlbum());
    }

    @ApiOperation("새 앨범을 등록 합니다.")
    @PostMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<String> postAlbum(@Valid @RequestBody final AlbumRequestDto.Register data){
        return ResponseEntity.status(HttpStatus.CREATED).body("/album" + albumService.postAlbum(data));
    }

    @ApiOperation("앨범을 삭제 합니다.")
    @DeleteMapping(value = "/delete/{albumId}")
    public ResponseEntity<Void> deleteAlbum(@ApiParam(value="앨범 ID", required = true) @PathVariable final Integer albumId) {
        albumService.deleteAlbum(albumId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    //주의 ! 앨범 내의 노래 목록을 반환하는 구성을 추가해야 함
    @ApiOperation("해당 아이디의 앨범 정보를 반환 합니다.")
    @GetMapping(value = "/{albumId}")
    public ResponseEntity<AlbumResponseDto.Detail> getAlbumById(@ApiParam(value="앨범 ID", required = true) @PathVariable final Integer albumId){
        return ResponseEntity.ok(albumService.getAlbumById(albumId));
    }

    @ApiOperation("앨범 타이틀을 통해 검색합니다.")
    @GetMapping(value = "/search")
    public ResponseEntity<List<AlbumResponseDto.Default>> searchAlbum(@ApiParam(value = "앨범 타이틀", required = true) @RequestParam String albumTitle){
        return ResponseEntity.ok(albumService.searchAlbum(albumTitle));
    }
}
