package com.Blog.DTO.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class PostCreated {
    public String titulo;
    public String descripcion;
    public String categoria;  
}
