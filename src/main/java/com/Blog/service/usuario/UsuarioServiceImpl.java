package com.Blog.service.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Blog.DTO.User.UserCreated;
import com.Blog.DTO.User.UserModify;
import com.Blog.entity.Rol;
import com.Blog.entity.Usuario;
import com.Blog.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Usuario guardarUsuario(UserCreated usuario) {
      
        Usuario user = new Usuario();
            user.setNombre(usuario.getNombre());
            user.setCorreo(usuario.getCorreo());
            user.setUserName(usuario.getUserName());
            user.setContraseña(usuario.getContraseña());
            user.setRol(Rol.USUARIO);
            return usuarioRepository.save(user);
    }

    @Override
    public Usuario modificarUsuario(Long id, UserModify usuarioModificado) {
       Usuario usuarioEncontrado = usuarioRepository.findById(id).get();
       Long idModificado = usuarioModificado.getId();
       Usuario usuarioModicar = usuarioRepository.findById(idModificado).get();
       usuarioModicar.setContraseña(usuarioModificado.getContraseña());
       if(usuarioEncontrado.getRol().equals(Rol.ADMINISTRADOR)){
        Rol rol = Rol.fromCodigo(usuarioModificado.getRol());
        if(rol != null){
        usuarioModicar.setRol(Rol.fromCodigo(usuarioModificado.getRol()));}
        else{
            return null;
        }
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

    @Override
    public boolean existsByCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    @Override
    public boolean existsByUsername(String username) {
        
        return usuarioRepository.existsByUserName(username);
    }

    
}
