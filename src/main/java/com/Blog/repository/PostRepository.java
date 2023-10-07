package com.Blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Blog.entity.Post;


@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query( value = "SELECT * FROM POST where id_Usuario=:id",nativeQuery = true)
    List<Post>buscarPostPorUser(Long id);

}
