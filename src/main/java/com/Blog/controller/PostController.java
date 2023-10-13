package com.Blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Blog.DTO.Post.PostCreated;
import com.Blog.DTO.Post.PostDetails;
import com.Blog.DTO.Post.PostFullDetails;
import com.Blog.DTO.Post.PostModify;
import com.Blog.entity.Post;
import com.Blog.entity.Rol;
import com.Blog.entity.Usuario;
import com.Blog.service.post.PostService;
import com.Blog.service.usuario.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UsuarioService usuarioService;


    @Operation(summary = "Listar Post",
               description = "Permite Listar Todos Los Post.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post Listados"),
    })
    @GetMapping("/all")
    public ResponseEntity<List<PostDetails>>getall()
    {
        List<PostDetails>post = postService.todoslosPost();
        return new ResponseEntity<>(post,HttpStatus.OK);
    }
    @Operation(summary = "Busca un Post por su ID",
               description = "Permite Mostrar un Post Segun su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post Listado"),
        @ApiResponse(responseCode = "404",description = "Post no Encontrado")
    })
    @GetMapping("/{idPost}")
    public ResponseEntity<PostDetails>PostById(@PathVariable("idPost") Long id)
    {
        PostDetails post = postService.buscarPost(id);
        if(post!=null)
        {
            return new ResponseEntity<>(post,HttpStatus.OK);
        }else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Modifica un Post",
               description = "Permite Modificar un Post. <br>"+"idUser: Usuario Activo <br>"+"idPost: ID del Post a Modificar"+
               "<br>Solo El dueño del Post o un Usuario con Rol 'Administrador' pueden Modificar un Post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post Modificado"),
        @ApiResponse(responseCode = "404",description = "Post no Modificado"),
        @ApiResponse(responseCode = "401",description = "No tienes los Permisos Necesarios")
    })
    @PutMapping("/{idUser}/{idPost}/modificar")
    public ResponseEntity<?>modificarPost(@PathVariable("idUser")Long idUser ,@PathVariable("idPost")Long idPost,@RequestBody PostModify postModificado)
    {
        Usuario usuarioEncontrado = usuarioService.obteneUsuario(idUser);
        PostDetails postEncontrado = postService.buscarPost(idPost);
        if(usuarioEncontrado.getRol().equals(Rol.ADMINISTRADOR)|| postEncontrado.getUsuario().getId() == idUser)
        {
            Post postActualizado = postService.modifcarPost(idPost, postModificado);
                if(postActualizado!=null){
                return new ResponseEntity<>("Post Actualizado",HttpStatus.OK);
        }
            else{
            return new ResponseEntity<>("Post No Actualizado",HttpStatus.NOT_MODIFIED);
        }
        }
        return new ResponseEntity<>("No Tienes Permisos Para Modificar Este Post",HttpStatus.UNAUTHORIZED);
    }

    //Completado Y Funcionando
    @Operation(summary= "Crear Post ", description = "Se debe Agregar el id del Usuario que crea el Post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Post Guardado"),
        @ApiResponse(responseCode = "404",description = "Post no Guardado"),
        @ApiResponse(responseCode = "401",description = "No tienes los Permisos Necesarios")
    })
    @PostMapping("/{idUser}/created")
    public ResponseEntity<String>createPost(@PathVariable("idUser")Long idUser,@RequestBody PostCreated post)
    {
        Usuario usuarioEncontrado = usuarioService.obteneUsuario(idUser);

        Post postcreated = postService.guardarPost(idUser, post);

        if(usuarioEncontrado!= null)
        {
        if(postcreated!=null)
        {return new ResponseEntity<>("Noticia Guardara",HttpStatus.CREATED);}
        else{
            return new ResponseEntity<>("Noticia No Guardada",HttpStatus.NOT_FOUND);
        } 
    }
        return new ResponseEntity<>("Usuario No Valido Para Crear una Noticia",HttpStatus.UNAUTHORIZED);
    }
    //*--------------------------------------------------------------------------------------------------------- */

    @Operation(summary= "Eliminar Post ", description = "Se Debe Indicar el id del Post a Borrar <br>"+"Si se Elimina un Post Automaticamente Se eliminan todos los Comentarios "
    +"Relacionados al Post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post Eliminado"),
        @ApiResponse(responseCode = "400",description = "Post no Eliminado"),
    })
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?>delete(@PathVariable("id") Long id)
    {
   

     Boolean delete = postService.eliminarPost(id);
     if(delete)
     {
        return ResponseEntity.ok("Post Eliminado");
     }
     return ResponseEntity.badRequest().body("No Se pudo Eliminar");

    }
    
    @GetMapping("/by/{id}")
    public ResponseEntity<List<PostDetails>>getPostById(@PathVariable("id")Long id)
    {
        List<PostDetails>postDetails = postService.buscarPostPorIdUser(id);
        return new ResponseEntity<>(postDetails,HttpStatus.OK);
    }




    @GetMapping("/{id}/full")
    public ResponseEntity<PostFullDetails>getPostFull(@PathVariable("id")Long id)
    {
        PostFullDetails postFull =  postService.postFull(id);
        return new ResponseEntity<>(postFull,HttpStatus.OK);
    }

}
