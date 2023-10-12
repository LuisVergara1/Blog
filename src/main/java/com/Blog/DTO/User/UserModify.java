package com.Blog.DTO.User;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModify {
    public Long id;
    public String contraseña;
    public String rol;
}
