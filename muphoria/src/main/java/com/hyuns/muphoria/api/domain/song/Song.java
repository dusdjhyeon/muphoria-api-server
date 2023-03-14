package com.hyuns.muphoria.api.domain.song;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.catalina.User;
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
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @NotNull
    @Column
    private String title;

    @Column
    private String lyrics;

    @Column
    private String tag;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @Column
    private Integer like;

    //@OneToMany(mappedBy = "song")
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "song")
    private Set<SongTag> tags = new HashSet<>();
}
