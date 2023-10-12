package com.Blog.DTO.Comentarios;

import com.Blog.DTO.User.UserPostDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioTDO {
    private Long id;
    private String descripcion;
    private UserPostDTO usuario;
    
}
