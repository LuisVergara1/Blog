package com.Blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Blog.DTO.UserCreated;
import com.Blog.entity.Usuario;
import com.Blog.service.usuario.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    @Operation(summary= "Crear User ", description = "Crea Nuevos Usuarios Donde Rol por Defecto Sera Usuario ")
    @PostMapping("/created")
    public ResponseEntity<Usuario>createUser(@RequestBody UserCreated usuariocreado)
    {   
         Usuario user =  new Usuario();
         user.setRol("Usuario");
         Usuario  userCreated = usuarioService.guardarUsuario(user);
        
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
    @Operation(summary= "Modify Users ", description = "modify Users ")
    @PutMapping("/{id}/modificar")
    public ResponseEntity<String>modificarUsuario(@PathVariable("id")Long id , @RequestBody Usuario usuarioModificado)
    {
       Usuario usuarioencontrado = usuarioService.obteneUsuario(id);
       if(usuarioencontrado.getRol().equals("Admin")|| usuarioencontrado.getId_usuario()==usuarioModificado.getId_usuario()){
         Usuario usuarioActualizado = usuarioService.modificarUsuario(id, usuarioModificado);
         if(usuarioActualizado!=null){
            return new ResponseEntity<>("Usuario Actualizado",HttpStatus.OK);
        }
        else
       {
        return new ResponseEntity<>("Error al Actualizar",HttpStatus.NOT_ACCEPTABLE);
       }
       
       }
       return new ResponseEntity<>("No Tienes Permisos para Modificar este Usuario",HttpStatus.NOT_ACCEPTABLE);
  
    }
}
