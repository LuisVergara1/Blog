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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    @Operation(summary = "Crear Usuario",
               description = "Crea un nuevo usuario con el rol por defecto 'Usuario'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping("/created")
    public ResponseEntity<?>createUser(@RequestBody UserCreated usuariocreado)
    {   
        try {
            Usuario newUser = usuarioService.guardarUsuario(usuariocreado);
            return ResponseEntity.ok(newUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<Usuario>>getall(){
        List<Usuario> usuarios = usuarioService.listarUsuario();

        return new ResponseEntity<>(usuarios,HttpStatus.OK);
    }

    @Operation(summary = "Buscar Usuario",
               description = "Busca Un Usuario Segun su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario Encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no Encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?>getUserById(@PathVariable("id") Long id)
    {
        Usuario usuarioEncontrado = usuarioService.obteneUsuario(id);
        if(usuarioEncontrado !=null ){
        return new ResponseEntity<>(usuarioEncontrado,HttpStatus.OK);
        }
        return new ResponseEntity<>("Usuario No Encontrado",HttpStatus.NOT_FOUND);
    
    }


    @Operation(summary = "Modificar Usuarios",
               description = "Modifica Usuario el Id es del Usuario Actual de la Session")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario Modificado"),
        @ApiResponse(responseCode = "304", description = "Usuario no Modificado"),
        @ApiResponse(responseCode = "404", description = "Usuario no Encontrado"),
        @ApiResponse(responseCode = "401", description = "Sin Permisos Necesarios")
    })
    @PutMapping("/{id}/modificar")
    public ResponseEntity<String>modificarUsuario(@PathVariable("id")Long id , @RequestBody Usuario usuarioModificado)
    {
       Usuario usuarioencontrado = usuarioService.obteneUsuario(id);

       if(usuarioencontrado!=null){
       if(usuarioencontrado.getRol().equals("Admin")|| usuarioencontrado.getId_usuario()==usuarioModificado.getId_usuario()){
         Usuario usuarioActualizado = usuarioService.modificarUsuario(id, usuarioModificado);
         if(usuarioActualizado!=null){
            return new ResponseEntity<>("Usuario Actualizado",HttpStatus.OK);
        }
        else
       {
        return new ResponseEntity<>("Error al Actualizar",HttpStatus.NOT_MODIFIED);
       }
       
       }
       }else{
        return new ResponseEntity<>("Id De Usuario no Valido",HttpStatus.NOT_FOUND);
       }
       return new ResponseEntity<>("No Tienes Permisos para Modificar este Usuario",HttpStatus.UNAUTHORIZED);
  
    }
}
