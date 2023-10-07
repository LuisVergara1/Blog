package com.Blog.DTO;

import java.util.List;


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


