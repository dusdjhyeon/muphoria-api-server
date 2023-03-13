package com.hyuns.muphoria.api.domain.tag;

import com.hyuns.muphoria.api.domain.song.SongTag;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "tag", cascade = {CascadeType.REMOVE})
    private Set<SongTag> songs = new HashSet<>();
}
