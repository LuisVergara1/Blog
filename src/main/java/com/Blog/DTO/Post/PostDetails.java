package com.Blog.DTO.Post;

import com.Blog.DTO.User.UserPostDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDetails {
    public Long id_post;
    public String titulo;
    public String descripcion;
    public String categoria;
    UserPostDTO usuario;
}
