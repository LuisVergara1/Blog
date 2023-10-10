package com.Blog.service.post;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Blog.DTO.ComentarioTDO;
import com.Blog.DTO.PostCreated;
import com.Blog.DTO.PostDetails;
import com.Blog.DTO.PostFullDetails;
import com.Blog.DTO.UserPostDTO;
import com.Blog.entity.Post;
import com.Blog.entity.Usuario;
import com.Blog.repository.PostRepository;
import com.Blog.service.comentario.ComentarioService;
import com.Blog.service.usuario.UsuarioService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ComentarioService comentarioService;

    @Override
    public List<PostDetails> todoslosPost() {
        List<Post>posts = postRepository.findAll();
       List<PostDetails>postDetailsList = new ArrayList<>();

       for(Post postEncontrados :  posts)
       {
        Long userId = postEncontrados.getUsuario().getId_usuario();
        Usuario usuarioEncontrado = usuarioService.obteneUsuario(userId);
        PostDetails postDetails = new PostDetails();
        postDetails.setId_post(postEncontrados.getId_post());
        postDetails.setTitulo(postEncontrados.getTitulo());
        postDetails.setDescripcion(postEncontrados.getDescripcion());
        postDetails.setCategoria(postEncontrados.getCategoria());
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setId(usuarioEncontrado.getId_usuario());
        userPostDTO.setNombre(usuarioEncontrado.getNombre());
        userPostDTO.setRol(usuarioEncontrado.getRol());
        postDetails.setUsuario(userPostDTO);
        postDetailsList.add(postDetails);
       }
       return postDetailsList;
    }
    @Override
    public PostDetails buscarPost(Long id) {
      Post postEncontrado = postRepository.findById(id).orElse(null);
        if(postEncontrado!=null)
        {
          Long user = postEncontrado.getUsuario().getId_usuario();
          Usuario usuarioEncontrado = usuarioService.obteneUsuario(user);

          PostDetails postDetails = new PostDetails();
          postDetails.setId_post(postEncontrado.getId_post());
          postDetails.setTitulo(postEncontrado.getTitulo());
          postDetails.setDescripcion(postEncontrado.getDescripcion());
          postDetails.setCategoria(postEncontrado.getCategoria());
          UserPostDTO userPostDTO = new UserPostDTO();
          userPostDTO.setId(usuarioEncontrado.getId_usuario());
          userPostDTO.setNombre(usuarioEncontrado.getNombre());
          userPostDTO.setRol(usuarioEncontrado.getRol());
          postDetails.setUsuario(userPostDTO);
          return postDetails;
        }else{
            return null;
        }
    }

    
    @Override
    public Boolean eliminarPost(Long id) {
        try{
            postRepository.deleteById(id);
            return true;
        }catch(Exception e)
        {
            return false;
        }
    }
    @Override
    public Post modifcarPost(Long id, Post postModificado) {
        Post postEncontrado =  postRepository.findById(id).get();
        postEncontrado.setTitulo(postModificado.getTitulo());
        postEncontrado.setDescripcion(postModificado.getDescripcion());
        postEncontrado.setCategoria(postModificado.getCategoria());
        postRepository.save(postEncontrado);
        return postEncontrado;
    }
    @Override
    public Post guardarPost(Long id, PostCreated post) {
        Post newPost =  new Post();
        Usuario usuarioEncontrado = usuarioService.obteneUsuario(id);
        newPost.setTitulo(post.getTitulo());
        newPost.setDescripcion(post.getDescripcion());
        newPost.setCategoria(post.getCategoria());
        newPost.setUsuario(usuarioEncontrado);
        return postRepository.save(newPost);
    }
    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }
    @Override
    public List<Post> buscarPostPorIdUser(Long id) {
        List<Post>postDetails = postRepository.buscarPostPorUser(id);
        return postDetails;
    }
    @Override
    public PostFullDetails postFull(Long id) {

        Post postEncontrado = postRepository.findById(id).orElse(null);
        if(postEncontrado!=null)
        {
          Long user = postEncontrado.getUsuario().getId_usuario();
          Usuario usuarioEncontrado = usuarioService.obteneUsuario(user);
          List<ComentarioTDO> comentarios = comentarioService.findByIdPost(id);

          PostFullDetails postDetails = new PostFullDetails();
          postDetails.setId_post(postEncontrado.getId_post());
          postDetails.setTitulo(postEncontrado.getTitulo());
          postDetails.setDescripcion(postEncontrado.getDescripcion());
          postDetails.setCategoria(postEncontrado.getCategoria());
          UserPostDTO userPostDTO = new UserPostDTO();
          userPostDTO.setId(usuarioEncontrado.getId_usuario());
          userPostDTO.setNombre(usuarioEncontrado.getNombre());
          userPostDTO.setRol(usuarioEncontrado.getRol());
          postDetails.setUsuario(userPostDTO);
          
          postDetails.setComentarios(comentarios);
          return postDetails;
        }else{
            return null;
        }
    }  
}
