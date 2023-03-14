package com.hyuns.muphoria.api.domain.album;

import com.hyuns.muphoria.api.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
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
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column
    @JoinColumn(name = "release_date")
    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String description;

    @Column(name = "cover_image")
    private String coverImage;
/*
    @OneToMany(mappedBy = "album")
    private Set<Song> song = new HashSet<>();
 */
}
