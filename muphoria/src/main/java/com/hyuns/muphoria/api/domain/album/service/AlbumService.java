package com.hyuns.muphoria.api.domain.album.service;

import com.hyuns.muphoria.api.domain.album.Album;
import com.hyuns.muphoria.api.domain.album.dto.AlbumRequestDto;
import com.hyuns.muphoria.api.domain.album.dto.AlbumResponseDto;
import com.hyuns.muphoria.api.domain.album.repository.AlbumRepository;
import com.hyuns.muphoria.api.domain.user.Role;
import com.hyuns.muphoria.api.domain.user.User;
import com.hyuns.muphoria.api.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {
    protected final AlbumRepository albumRepository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public List<AlbumResponseDto.Default> getAllAlbum() {
        return AlbumResponseDto.Default.of(albumRepository.findAll());
    }

    @Transactional
    public Integer postAlbum(AlbumRequestDto.Register data){
        User user = userService.getCurrentUser();
        if(user.getRole() != Role.USER)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");

        Album album = albumRepository.save(
                Album.builder()
                        .title(data.getTitle())
                        .user(user)
                        .releaseDate(LocalDate.now())
                        .coverImage(data.getCoverImage())
                        .description(data.getDescription())
                        .build()
        );

        Integer id = album.getId();
        return id;
    }

    @Transactional
    public void deleteAlbum(Integer albumId){
        Album album = albumRepository.findById(albumId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "해당하는 ID를 가진 앨범이 존재하지 않습니다."
        ));

        User user = userService.getCurrentUser();

        if(user == album.getUser()){
            albumRepository.delete(album);
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"앨범 소유자만 앨범을 삭제할 수 있습니다.");
        }

    }

    @Transactional(readOnly = true)
    public AlbumResponseDto.Detail getAlbumById(Integer id){
        return AlbumResponseDto.Detail.of(albumRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "해당하는 ID를 가진 앨범이 존재하지 않습니다."
        )));
    }

    @Transactional(readOnly = true)
    public List<AlbumResponseDto.Default> searchAlbum(String albumTitle){
        return AlbumResponseDto.Default.of(albumRepository.findByTitle(albumTitle));
    }
}
