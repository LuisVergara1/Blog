package com.Blog.DTO.User;

import com.Blog.entity.Rol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPostDTO {
    private Long id;
    private String nombre;
    private Rol rol;
}
