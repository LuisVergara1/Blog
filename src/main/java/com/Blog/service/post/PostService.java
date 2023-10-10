package com.Blog.service.post;

import java.util.List;

import com.Blog.DTO.PostCreated;
import com.Blog.DTO.PostDetails;
import com.Blog.DTO.PostFullDetails;
import com.Blog.entity.Post;

public interface PostService {
    
    List<PostDetails>todoslosPost();

    PostDetails buscarPost(Long id);

    Post modifcarPost(Long id, Post postModificado);

    Boolean eliminarPost(Long id);

    Post guardarPost(Long id, PostCreated post);

    List<Post> buscarPostPorIdUser(Long id);

    PostFullDetails postFull(Long id);

    //Solo Usado para Cargar Datos desde H2
    //--------------------------------->
    Post savePost(Post post);
    //--------------------------------->




}
