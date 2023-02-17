package com.hyuns.muphoria.api.domain.user.repository;

import com.hyuns.muphoria.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    //song이랑 playlist랑 fetch join하기
//    @Query("select u from User u left join fetch u.groups ug left join fetch ug.group g where u.id = :id")
    Optional<User> getAllInfoOfUserById(Integer id);
    List<User> findByName(String userName);
    Optional<User> findByEmail(String email);
}
