package com.Blog.service.post;

import java.util.List;

import com.Blog.DTO.Post.PostCreated;
import com.Blog.DTO.Post.PostDetails;
import com.Blog.DTO.Post.PostFullDetails;
import com.Blog.DTO.Post.PostModify;
import com.Blog.entity.Post;

public interface PostService {
    
    List<PostDetails>todoslosPost();

    PostDetails buscarPost(Long id);

    Post modifcarPost(Long idPost, PostModify postModificado);

    Boolean eliminarPost(Long id);

    Post guardarPost(Long id, PostCreated post);

    List<PostDetails> buscarPostPorIdUser(Long id);

    PostFullDetails postFull(Long id);

    //Solo Usado para Cargar Datos desde H2
    //--------------------------------->
    Post savePost(Post post);
    //--------------------------------->




}
