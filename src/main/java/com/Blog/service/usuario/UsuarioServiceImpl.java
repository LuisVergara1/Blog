package com.Blog.service.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Blog.entity.Usuario;
import com.Blog.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
      return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario modificarUsuario(Long id, Usuario usuarioModificado) {
       Usuario usuarioEncontrado = usuarioRepository.findById(id).get();
       usuarioEncontrado.setContraseña(usuarioModificado.getContraseña());
       usuarioEncontrado.setRol(usuarioModificado.getRol());
       usuarioRepository.save(usuarioEncontrado);
       return usuarioEncontrado;
    }

    @Override
    public Boolean eliminarUsuario(Long id) {
        try{
            usuarioRepository.deleteById(id);
            return true;
        }catch(Exception e)
        {
            return false;
        }
    }

    @Override
    public Usuario obteneUsuario(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<Usuario> listarUsuario() {
       return usuarioRepository.findAll();
    }
    
}
