package com.Blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Blog.DTO.Comentarios.ComentarioCreate;
import com.Blog.DTO.Comentarios.ComentarioTDO;
import com.Blog.entity.Comentario;
import com.Blog.entity.Post;
import com.Blog.entity.Rol;
import com.Blog.entity.Usuario;
import com.Blog.service.comentario.ComentarioService;
import com.Blog.service.post.PostService;
import com.Blog.service.usuario.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequestMapping("/comentario")
@RestController
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;
    private final UsuarioService usuarioService;
    private final PostService postService;

    @Operation(summary= "Obtener Todos los Comentarios ", description = "Este EndPoint Permite Buscar un Producto Segun su ID")
    @GetMapping("/all")
    public ResponseEntity<List<ComentarioTDO>> allcomentarios()
    {
        List<ComentarioTDO> comentarios = comentarioService.allComentarios();

        return new ResponseEntity<>(comentarios,HttpStatus.OK);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<List<ComentarioTDO>>comentByPost(@PathVariable("id")Long id)
    {
        List<ComentarioTDO> comentarioTDOs = comentarioService.findByIdPost(id);

        return new ResponseEntity<>(comentarioTDOs,HttpStatus.OK);
    }

    @PostMapping("/{idUser}/created")
    public ResponseEntity<?> guardarComentario(@PathVariable("idUser") Long id, @RequestBody ComentarioCreate comentario) {
        
        // Verifica si el ID del post es nulo
        if (comentario.getIdPost()== null) {
            return ResponseEntity.badRequest().body("ID de post nulo. Proporciona un ID de post válido.");
        }
        Usuario usuario = usuarioService.obteneUsuario(id);
        Post post = postService.buscarPostComentario(comentario.getIdPost());
        if (usuario.getId_usuario() == post.getUsuario().getId_usuario() || usuario.getRol().equals(Rol.ADMINISTRADOR)) {
            Comentario comentarioCreado = new Comentario();
            comentarioCreado.setPost(post);
            comentarioCreado.setUsuario(usuario);
            comentarioCreado.setComentario(comentario.getComentario());
            Comentario created = comentarioService.guardarComentario(comentarioCreado);
    
            if (created != null) {
                return new ResponseEntity<>("Comentario Creado", HttpStatus.CREATED);
            }
        } else {
            return ResponseEntity.badRequest().body("No Cuentas Con los Permisos Necesarios");
        }
        return ResponseEntity.badRequest().body("Hubo un Error al Guardar el Comentario");
    }
}
