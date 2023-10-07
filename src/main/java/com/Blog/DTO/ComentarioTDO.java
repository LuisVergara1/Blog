package com.Blog.DTO;

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
