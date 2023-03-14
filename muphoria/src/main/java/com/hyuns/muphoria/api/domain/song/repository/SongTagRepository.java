package com.hyuns.muphoria.api.domain.song.repository;

import com.hyuns.muphoria.api.domain.song.SongTag;
import com.hyuns.muphoria.api.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongTagRepository extends JpaRepository<SongTag, Integer> {
    @Query("select st from SongTag st left join fetch st.song where st.tag = :tag")
    List<SongTag> findAllSongByTag(Tag tag);
}
