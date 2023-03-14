package com.hyuns.muphoria.api.domain.tag.service;

import com.hyuns.muphoria.api.domain.song.dto.SongResponseDto;
import com.hyuns.muphoria.api.domain.song.repository.SongRepository;
import com.hyuns.muphoria.api.domain.song.repository.SongTagRepository;
import com.hyuns.muphoria.api.domain.song.service.SongService;
import com.hyuns.muphoria.api.domain.tag.Tag;
import com.hyuns.muphoria.api.domain.tag.dto.TagResponseDto;
import com.hyuns.muphoria.api.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final SongTagRepository songTagRepository;
    private final SongService songService;
    private final SongRepository songRepository;
    private final UserService userService;

    // 태그 생성
    @Transactional
    public Integer postTag(String tagName) {
        User user = userService.getCurrentUser();
        if (user.getRole() != Role.User)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");

        Tag tag = tagRepository.save(tagRepository.findByName(tagName).get());
        Integer id = tag.getId();

        return id;
    }

    // 찾은 태그 반환
   @Transactional(readOnly = true)
    public Tag searchTag(String tagName) {
        if (tagRepository.findByName(tagName).isEmpty()) {
            postTag(tagName);
            }

       return tagRepository.findByName(tagName).get();
   }


    // 태그 삭제
    @Transactional
    public void deleteTag(Integer tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "해당하는 ID를 가진 태그가 없습니다."
        ));

        // 본인이 삭제 가능
        User user = userService.getCurrentUser();
        if (user.getRole() != Role.User)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");

        tagRepository.delete(tag);
    }

    // 태그 전체 리스트
    @Transactional(readOnly = true)
    public List<TagResponseDto.Info> getAllTag() {
        return TagResponseDto.Info.of(tagRepository.findAll());
    }

    // 태그와 관련된 노래 리스트
    @Transactional(readOnly = true)
    public List<SongResponseDto.Simple> getSongListForTag(Integer tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "해당하는 ID를 가진 태그가 존재하지 않습니다."
        ));

        User user = userService.getCurrentUser();

        if (user.getRole() != Role.USER) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
        }

        return songTagRepository.findAllSongByTag(tag)
                .stream().map(SongTag::getSong).map(SongResponseDto.Simple::of).collect(Collectors.toList());
    }

    // 태그 이름으로 검색
    @Transactional(readOnly = true)
    public List<TagResponseDto.Info> findByNameContains(String query) {
        return tagRepository.findByNameContains(query)
                .stream().map(TagResponseDto.Info::of).collect(Collectors.toList());
    }
}
