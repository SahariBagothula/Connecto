package com.social.connecto.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.social.connecto.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("select p from Post p where p.user.id=:userid")
    List<Post> findPostByUserid(int userid);

}
