package com.hyuns.muphoria.api.domain.album.repository;

import com.hyuns.muphoria.api.domain.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {
    List<Album> findByTitle(String albumTitle);
}
