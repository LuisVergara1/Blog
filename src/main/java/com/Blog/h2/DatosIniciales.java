package com.Blog.h2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.Blog.entity.Comentario;
import com.Blog.entity.Post;
import com.Blog.entity.Usuario;
import com.Blog.service.comentario.ComentarioService;
import com.Blog.service.post.PostService;
import com.Blog.service.usuario.UsuarioService;

@Component
public class DatosIniciales implements CommandLineRunner {

    private final PostService postService;
    private final UsuarioService usuarioService;
    private final ComentarioService comentarioService;

    @Autowired
    public DatosIniciales( PostService postService,UsuarioService usuarioService, ComentarioService comentarioService)
    {
        this.postService = postService;
        this.usuarioService=usuarioService;
        this.comentarioService= comentarioService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Ejecutando Metodo Run");
        try{
            cargarDatosIniciales();
            System.out.println("Datos Cargados Correctamente");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void cargarDatosIniciales()
    {

        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Luis Vergara");
        usuario1.setUserName("Luzhitto");
        usuario1.setCorreo("luismarcelo.v.c@hotmail.com");
        usuario1.setContraseña("User123");
        usuario1.setRol("Admin");
        usuarioService.guardarUsuariosH2(usuario1);

        Post post1 = new Post();
        post1.setTitulo("Titulo Noticia1");
        post1.setDescripcion("Descripcion Noticia 1");
        post1.setCategoria("Tecnologia");
        post1.setUsuario(usuario1);
        postService.savePost(post1);

        Post post2 = new Post();
        post2.setTitulo("Titulo Noticia 2");
        post2.setDescripcion("Descripcion Noticia 2");
        post2.setCategoria("Cocina");
        post2.setUsuario(usuario1);
        postService.savePost(post2);

       

        Comentario comentario1 = new Comentario();
        comentario1.setComentario("Prueba Comentario");
        comentario1.setPost(post1);
        comentario1.setUsuario(usuario1);
        comentarioService.save(comentario1);

        Comentario comentario2 = new Comentario();
        comentario2.setComentario("Prueba Comentario 2");
        comentario2.setPost(post1);
        comentario2.setUsuario(usuario1);
        comentarioService.save(comentario2);

        Comentario comentario3 = new Comentario();
        comentario3.setComentario("Prueba Post 2 ");
        comentario3.setPost(post2);
        comentario3.setUsuario(usuario1);
        comentarioService.save(comentario3);

        Post post3 = new Post();
        post3.setTitulo("Titulo Noticia 3");
        post3.setDescripcion("Descripcion Noticia 3");
        post3.setCategoria("Internacional");
        post3.setUsuario(usuario1);
        postService.savePost(post3);
        
    }
    
}
