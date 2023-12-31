package com.Blog.service.comentario;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.Blog.DTO.Comentarios.ComentarioTDO;
import com.Blog.DTO.User.UserPostDTO;
import com.Blog.entity.Comentario;
import com.Blog.entity.Usuario;
import com.Blog.repository.ComentarioRepository;
import com.Blog.service.usuario.UsuarioService;


@Service
public class ComentarioServiceImpl  implements ComentarioService{

     @Autowired
     private  ComentarioRepository comentarioRepository; 
     @Autowired 
     private UsuarioService usuarioService;

    @Override
    public Comentario guardarComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);

    }

    @Override
    public List<ComentarioTDO> allComentarios() {
        List<Comentario> comentarios = comentarioRepository.findAll();
        List<ComentarioTDO> comentariosdto = new ArrayList<>();

        for(Comentario comentariosEncontrados : comentarios)
        {
            Long userId = comentariosEncontrados.getUsuario().getId_usuario();
            Usuario usuarioEncontrado = usuarioService.obteneUsuario(userId);
            ComentarioTDO comentarioTDO = new ComentarioTDO();
            UserPostDTO userDTO = new UserPostDTO();
            userDTO.setId(usuarioEncontrado.getId_usuario());
            userDTO.setNombre(usuarioEncontrado.getNombre());
            userDTO.setRol(usuarioEncontrado.getRol());
            
            comentarioTDO.setUsuario(userDTO);
            comentarioTDO.setId(comentariosEncontrados.getId_comentario());
            comentarioTDO.setDescripcion(comentariosEncontrados.getComentario());
            comentarioTDO.setIdPost(comentariosEncontrados.getPost().getId_post());
            comentariosdto.add(comentarioTDO);
        }
        return comentariosdto;

    }


    @Override
    public Boolean eliminarComentario(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarComentario'");
    }

    @Override
    public Comentario save(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @Override
    public List<ComentarioTDO> findByIdPost(Long id) {
        List<Comentario> comentarios = comentarioRepository.findComentariosByPostId(id); 
        
        List<ComentarioTDO> comentarioTDOs = new ArrayList<>();

        for(Comentario comentariosEncontrados : comentarios)
        {
            Long userId = comentariosEncontrados.getUsuario().getId_usuario();
            Usuario usuarioEncontrado = usuarioService.obteneUsuario(userId);
            ComentarioTDO comentarioTDO = new ComentarioTDO();
            UserPostDTO userDTO = new UserPostDTO();
            userDTO.setId(usuarioEncontrado.getId_usuario());
            userDTO.setNombre(usuarioEncontrado.getNombre());
            userDTO.setRol(usuarioEncontrado.getRol());
            comentarioTDO.setUsuario(userDTO);
            comentarioTDO.setId(comentariosEncontrados.getId_comentario());
            comentarioTDO.setDescripcion(comentariosEncontrados.getComentario());
            comentarioTDO.setIdPost(comentariosEncontrados.getPost().getId_post());
            comentarioTDOs.add(comentarioTDO);
        }

        return  comentarioTDOs;
    }

    @Override
    public Boolean deteleComentByIdPost(Long idPost) {

      int estadoEliminacion = comentarioRepository.deleteComentariosByPostId(idPost);
      if(estadoEliminacion >= 0)
      {
        return true;
      }
      else{
        return false;
      }

    }



    
}
