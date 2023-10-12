package com.Blog.DTO.Post;

import java.util.List;

import com.Blog.DTO.Comentarios.ComentarioTDO;
import com.Blog.DTO.User.UserPostDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostFullDetails {
    public Long id_post;
    public String titulo;
    public String descripcion;
    public String categoria;
    UserPostDTO usuario;
    private List<ComentarioTDO> comentarios;
   
}


