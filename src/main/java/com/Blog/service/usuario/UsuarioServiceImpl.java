package com.Blog.service.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Blog.DTO.UserCreated;
import com.Blog.entity.Usuario;
import com.Blog.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Usuario guardarUsuario(UserCreated usuario) {
      
      if (usuario.getNombre() != null && !usuario.getNombre().isEmpty() &&
        usuario.getUserName() != null && !usuario.getUserName().isEmpty() &&
        usuario.getCorreo() != null && !usuario.getCorreo().isEmpty() &&
        usuario.getContraseña() != null && !usuario.getContraseña().isEmpty()) {
        Usuario user = new Usuario();
            user.setNombre(usuario.getNombre());
            user.setCorreo(usuario.getCorreo());
            user.setUserName(usuario.getUserName());
            user.setContraseña(usuario.getContraseña());
            user.setRol("Usuario");
            return usuarioRepository.save(user);
      }
      throw new IllegalArgumentException("Los campos no pueden estar vacíos o nulos");
    }

    @Override
    public Usuario modificarUsuario(Long id, Usuario usuarioModificado) {
       Usuario usuarioEncontrado = usuarioRepository.findById(id).get();
       Long idModificado = usuarioModificado.getId_usuario();
       Usuario usuarioModicar = usuarioRepository.findById(idModificado).get();
       usuarioModicar.setContraseña(usuarioModificado.getContraseña());
       if(usuarioEncontrado.getRol().equals("Admin")){
       usuarioModicar.setRol(usuarioModificado.getRol());
       }
       usuarioRepository.save(usuarioModicar);
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

    @Override
    public Usuario guardarUsuariosH2(Usuario usuario) {
       
        return usuarioRepository.save(usuario);
    }
    
}
