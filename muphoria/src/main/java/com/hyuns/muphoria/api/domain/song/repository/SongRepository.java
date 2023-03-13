package com.hyuns.muphoria.api.domain.song.repository;

import com.hyuns.muphoria.api.domain.song.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {
    List<Song> findByTitleContains(String songTitle);
    List<Song> findByUser(User user);
}
