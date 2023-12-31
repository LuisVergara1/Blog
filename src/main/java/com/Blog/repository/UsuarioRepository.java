package com.Blog.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.Blog.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    boolean existsByCorreo(String correo);

    boolean existsByUserName(String username);
    
}
