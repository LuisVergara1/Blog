package com.Blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Blog.entity.Usuario;
import com.Blog.service.usuario.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/created")
    public ResponseEntity<Usuario>createUser(@RequestBody Usuario usuariocreado)
    {   
         Usuario  userCreated = usuarioService.guardarUsuario(usuariocreado);
        
        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Usuario>>getall(){
        List<Usuario> usuarios = usuarioService.listarUsuario();

        return new ResponseEntity<>(usuarios,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario>getUserById(@PathVariable("id") Long id)
    {
        Usuario usuarioEncontrado = usuarioService.obteneUsuario(id);
        return new ResponseEntity<>(usuarioEncontrado,HttpStatus.OK);
    }

    @PutMapping("/{id}/modificar")
    public ResponseEntity<String>modificarUsuario(@PathVariable("id")Long id , @RequestBody Usuario usuarioModificado)
    {
       Usuario usuarioActualizado = usuarioService.modificarUsuario(id, usuarioModificado);

       if(usuarioActualizado!=null)
       {
        return new ResponseEntity<>("Usuario Actualizado",HttpStatus.OK);
       }else
       {
        return new ResponseEntity<>("Usuario No Actualizado",HttpStatus.NOT_ACCEPTABLE);
       }
    }
}
