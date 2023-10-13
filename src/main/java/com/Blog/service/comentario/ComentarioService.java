package com.Blog.service.comentario;

import java.util.List;


import com.Blog.DTO.Comentarios.ComentarioTDO;
import com.Blog.entity.Comentario;

public interface ComentarioService {
    
    Comentario guardarComentario(Comentario comentario);

    List<ComentarioTDO>allComentarios();

    Boolean eliminarComentario(Long id);

    Comentario save(Comentario comentario);

    List<ComentarioTDO> findByIdPost(Long id);

    Boolean deteleComentByIdPost(Long idPost);

}
