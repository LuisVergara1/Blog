package com.Blog.DTO.Comentarios;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioCreate {
    private Long idPost;
    private String comentario;  
}
