package com.Blog.service.usuario;

import java.util.List;

import com.Blog.entity.Usuario;

public interface UsuarioService {
    
    Usuario guardarUsuario(Usuario usuario);

    List<Usuario> listarUsuario();

    Usuario modificarUsuario(Long id,Usuario usuarioModificado);

    Boolean eliminarUsuario (Long id);

    Usuario obteneUsuario(Long id);


}
