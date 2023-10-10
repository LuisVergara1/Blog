package com.Blog.service.usuario;

import java.util.List;

import com.Blog.DTO.UserCreated;
import com.Blog.entity.Usuario;

public interface UsuarioService {
    
    Usuario guardarUsuario(UserCreated usuario);

    List<Usuario> listarUsuario();

    Usuario modificarUsuario(Long id,Usuario usuarioModificado);

    Boolean eliminarUsuario (Long id);

    Usuario obteneUsuario(Long id);


    //Solo para Carga de Datos de H2
    //----------------------------------------------->
    Usuario guardarUsuariosH2(Usuario usuario);
    //----------------------------------------------->


}
