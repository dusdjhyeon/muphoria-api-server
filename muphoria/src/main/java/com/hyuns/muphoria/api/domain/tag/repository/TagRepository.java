package com.hyuns.muphoria.api.domain.tag.repository;

import com.hyuns.muphoria.api.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findByNameContains(String tagName);
    Optional<Tag> findByName(String tagName);
}
