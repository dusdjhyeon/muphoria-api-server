package com.hyuns.muphoria.api.domain.user;

import com.hyuns.muphoria.api.domain.album.Album;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

@Builder
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(length = 64, nullable = false, unique = true, name = "nickname")
    private String name;

    @Column(name = "profile_image")
    private String profileImage;

    @Column
    private String introduction;

    @Column
    private String bio;

    @Column(name = "email", unique = true)
    private String email;

    @Column
    private Role role;

/*    @OneToMany(mappedBy = "user")
    private Set<Play> playlist = new HashSet<>();
*/
    @OneToMany(mappedBy = "user")
    private Set<Album> album = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role.getKey()));
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return String.valueOf(this.id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.role == Role.USER;
    }
}
