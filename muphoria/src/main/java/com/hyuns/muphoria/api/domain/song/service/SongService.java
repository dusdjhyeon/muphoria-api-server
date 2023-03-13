package com.hyuns.muphoria.api.domain.song.service;

import com.hyuns.muphoria.api.domain.song.Song;
import com.hyuns.muphoria.api.domain.song.SongTag;
import com.hyuns.muphoria.api.domain.song.dto.SongRequestDto;
import com.hyuns.muphoria.api.domain.song.dto.SongResponseDto;
import com.hyuns.muphoria.api.domain.song.repository.SongRepository;
import com.hyuns.muphoria.api.domain.song.repository.SongTagRepository;
import com.hyuns.muphoria.api.domain.tag.repository.TagRepository;
import com.hyuns.muphoria.api.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    private final SongTagRepository songTagRepository;
    private final TagService tagService;
    private final UserService userService;
    private final TagRepository tagRepository;

    // 노래 등록
    @Transactional
    public Integer postSong(SongRequestDto.Register data) {
        User user = userService.getCurrentUser();
        if (user.getRole() != Role.USER)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "노래를 등록할 권한이 없습니다.");

        Song song = songRepository.save(data.toEntity());
        Integer id = song.getId();
        String tagName = song.getTag();

        songTagRepository.save(
                SongTag.builder()
                        .song(song)
                        .tag(tagService.searchTag(tagName))
                        .build()
        );

        return id;
    }

    // 노래 삭제
    @Transactional
    public void deleteSong(Integer songId) {
        User user = userService.getCurrentUser();

        if (user != song.getUser()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "노래를 삭제할 권한이 없습니다.");
        }

        Song song = songRepository.findById(songId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "해당하는 ID를 가진 노래가 존재하지 않습니다."
        ));

        songRepository.delete(song);
    }

    // 노래 상세 정보 확인
    @Transactional(readOnly = true)
    public SongResponseDto.Detail getSongDetails(Integer songId) {
        User user = userService.getCurrentUser();
        if (user.getRole() != Role.USER)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 후에 상세 정보를 볼 수 있습니다.");

        Song song = songRepository.findById(songId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "해당하는 ID를 가진 노래가 존재하지 않습니다."
        ));

        return SongResponseDto.Detail.of(song);
    }

    // 노래 제목으로 검색
    @Transactional(readOnly = true)
    public List<SongResponseDto.Simple> searchTitle(String query) {
        return SongResponseDto.Simple.of(songRepository.findByTitleContains(query));
    }

    // 해당 유저의 노래 리스트
    @Transactional(readOnly = true)
    public List<SongResponseDto.Simple> getAllMySongs(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "해당하는 유저가 존재하지 않습니다."
        ));

        return songRepository.findByUser(user);
    }
}
