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

import com.Blog.DTO.PostDetails;
import com.Blog.DTO.PostFullDetails;
import com.Blog.entity.Post;
import com.Blog.service.post.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @GetMapping("/all")
    public ResponseEntity<List<PostDetails>>getall()
    {
        List<PostDetails>post = postService.todoslosPost();
        return new ResponseEntity<>(post,HttpStatus.OK);
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<PostDetails>PostById(@PathVariable("id") Long id)
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

    @PutMapping("/{id}")
    public ResponseEntity<String>modificarPost(@PathVariable("id")Long id,@RequestBody Post postModificado)
    {
        Post postActualizado = postService.modifcarPost(id, postModificado);

        if(postActualizado!=null){
        return new ResponseEntity<>("Post Actualizado",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Post No Actualizado",HttpStatus.NOT_MODIFIED);
        }
    }


    @PostMapping("/{id}/created")
    public ResponseEntity<String>createPost(@PathVariable("id")Long id ,@RequestBody Post post)
    {
        Post postcreated = postService.guardarPost(id, post);

        if(postcreated!=null)
        {return new ResponseEntity<>("Noticia Guardara",HttpStatus.CREATED);}
        else{
            return new ResponseEntity<>("Noticia No Guardada",HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Boolean>delete(@PathVariable("id") Long id)
    {
        Boolean delete = postService.eliminarPost(id);

        if(delete)
        {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
    }}

    @GetMapping("/by/{id}")
    public ResponseEntity<List<Post>>getPostById(@PathVariable("id")Long id)
    {
        List<Post>postDetails = postService.buscarPostPorIdUser(id);
        return new ResponseEntity<>(postDetails,HttpStatus.OK);
    }




    @GetMapping("/{id}/full")
    public ResponseEntity<PostFullDetails>getPostFull(@PathVariable("id")Long id)
    {
        PostFullDetails postFull =  postService.postFull(id);
        return new ResponseEntity<>(postFull,HttpStatus.OK);
    }

}
