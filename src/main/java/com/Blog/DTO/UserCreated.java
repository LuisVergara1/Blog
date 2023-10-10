package com.Blog.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreated {
    public Long id_usuario;
    public String nombre;
    public String correo;
    public String userName;
    public String contraseña;
    
}
