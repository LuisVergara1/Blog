package com.Blog.entity;


import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id_comentario;

    public String comentario;

    @ManyToOne
    @JoinColumn(name ="id_post",referencedColumnName = "id_post")
    Post post;

    @ManyToOne
    @JoinColumn(name ="id_usuario",referencedColumnName = "id_usuario")
    Usuario usuario;

}
