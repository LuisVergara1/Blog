package com.Blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Blog.entity.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario,Long> {

    @Query("SELECT c FROM Comentario c WHERE c.post.id_post = :idPost")
    List<Comentario> findComentariosByPostId(Long idPost);
}
