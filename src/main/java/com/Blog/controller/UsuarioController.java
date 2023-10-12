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

import com.Blog.DTO.User.UserCreated;
import com.Blog.DTO.User.UserModify;
import com.Blog.entity.Rol;
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
               description = "Crea un nuevo usuario con el rol por defecto 'Usuario'. <br>"+"El Sistema valida que Correo y Username Sean Unicos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente" ),
        @ApiResponse(responseCode = "400", description = "No se pudo Crear el Usuario")
    })
    @PostMapping("/created")
    public ResponseEntity<?> createUser(@RequestBody UserCreated usuarioCreado) {
        try {
            boolean existeCorreo = usuarioService.existsByCorreo(usuarioCreado.getCorreo());
            boolean existeUserName = usuarioService.existsByUsername(usuarioCreado.getUserName());
            if (usuarioCreado.getNombre() != null && !usuarioCreado.getNombre().isEmpty() &&
                usuarioCreado.getUserName() != null && !usuarioCreado.getUserName().isEmpty() &&
                usuarioCreado.getCorreo() != null && !usuarioCreado.getCorreo().isEmpty() &&
                usuarioCreado.getContraseña() != null && !usuarioCreado.getContraseña().isEmpty()) {
            if (existeCorreo && existeUserName) {
                return ResponseEntity.badRequest().body("Correo y Nombre de Usuario no disponibles");
            } else if (existeCorreo) {
                return ResponseEntity.badRequest().body("Correo no disponible");
            } else if (existeUserName) {
                return ResponseEntity.badRequest().body("Nombre de Usuario no disponible");
            } else {
                Usuario newUser = usuarioService.guardarUsuario(usuarioCreado);
                if (newUser != null) {
                    return ResponseEntity.ok(newUser);
                } else {
                    return ResponseEntity.badRequest().body("Error al guardar el usuario");
                }
            }
        }else{
            return ResponseEntity.badRequest().body("Los Campos No pueden estar Vacios o Nulos");
        }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error al Guardar el Usuario");
        }
    }

    @Operation(summary = "Listar Usuarios",
               description = "Permite Listar Todos Los Usuarios solo a los Rol 'Administrador'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "401" , description = "Sin Autorizacion")
    })
    @GetMapping("{idUser}/all")
    public ResponseEntity<?>getall(@PathVariable("idUser")Long id){

        Usuario usuarioEncontrado = usuarioService.obteneUsuario(id);
        if(usuarioEncontrado.getRol().equals(Rol.ADMINISTRADOR))
        {List<Usuario> usuarios = usuarioService.listarUsuario();

        return new ResponseEntity<>(usuarios,HttpStatus.OK);
    }
        return new ResponseEntity<>("Acceso Denegado",HttpStatus.UNAUTHORIZED);        
    }



    @Operation(summary = "Recuperar Usuario por ID",
    description = "Recupera un usuario por su ID. Los usuarios pueden recuperar su propia información o, si son administradores, acceder a la información de otros usuarios."
    + "<br> Donde idUser : es el Usuario Activo <br> idFound: Usuario a Buscar")  
     @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario Encontrado" , headers = { }),
        @ApiResponse(responseCode = "401", description = "Sin Autorizacion"),
        @ApiResponse(responseCode = "404", description = "Usuario no Encontrado")
    })
    @GetMapping("/{idUser}/{idFound}")
    public ResponseEntity<?>getUserById(@PathVariable("idUser") Long id,@PathVariable("idFound")Long idFound)
    {
        Usuario usuarioEncontrado = usuarioService.obteneUsuario(id);
        Usuario usuarioFound = usuarioService.obteneUsuario(idFound);

        if (usuarioFound == null ||  usuarioEncontrado == null) {
            return new ResponseEntity<>("Usuario No Encontrado", HttpStatus.NOT_FOUND);
        }   

        if (usuarioEncontrado.getRol().equals(Rol.ADMINISTRADOR) || usuarioEncontrado.getId_usuario() == idFound) {
            return new ResponseEntity<>(usuarioFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Tienes Permisos Para Buscar Este Usuario", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Modificar Usuarios",
               description = "Se puede Modificar usuarios de dos formas:<br> 1) Un administrador puede modificar cualquier usuario usando su propio id como referencia, incluyendo la capacidad de cambiar su rol.<br>"+ 
                              "2) Un usuario puede modificar sus propios datos, pero no puede cambiar su propio rol.<br>"+" Datos no Modificables : <br> username <br> correo ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario Modificado"),
        @ApiResponse(responseCode = "304", description = "Usuario no Modificado"),
        @ApiResponse(responseCode = "404", description = "Usuario no Encontrado"),
        @ApiResponse(responseCode = "401", description = "Sin Autorizacion")
    })
    @PutMapping("/{idUser}/modificar")
    public ResponseEntity<String>modificarUsuario(@PathVariable("idUser")Long id , @RequestBody UserModify usuarioModificado)
    {
       Usuario usuarioencontrado = usuarioService.obteneUsuario(id);

       if(usuarioencontrado!=null){
       if(usuarioencontrado.getRol().equals(Rol.ADMINISTRADOR)|| usuarioencontrado.getId_usuario() == usuarioModificado.getId()){
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

    @Operation(summary = "Eliminar Usuarios",
               description = "Permite Eliminar Un Usuarios <br>"+"Tienes 2 Opciones de Eliminacion :"+
               "1°:Un Usuario con Rol 'Administrador' puede Eliminar un Usuario. <br>"+
               "2°:Un Usuario Puede Eliminar su Propia Cuenta")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @DeleteMapping("/{idUser}/{idDelete}")
    public ResponseEntity<?>eliminarUsuario(@PathVariable("idUser")Long id , @PathVariable("idDelete")Long idDelete)
    {
        Usuario usuarioEncontrado = usuarioService.obteneUsuario(id);
        Usuario usuarioDelete = usuarioService.obteneUsuario(idDelete);
         if (usuarioEncontrado == null ||  usuarioDelete == null) {
            return new ResponseEntity<>("Usuario No Encontrado", HttpStatus.NOT_FOUND);
        }   

        if (usuarioEncontrado.getRol().equals(Rol.ADMINISTRADOR) || usuarioEncontrado.getId_usuario() == idDelete) {
            Boolean result = usuarioService.eliminarUsuario(idDelete);
            if(result ==true){
            return new ResponseEntity<>("Usuario Eliminado", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("No se a podido Eliminar el Usuario", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("No Tienes Permisos Para Buscar Este Usuario", HttpStatus.UNAUTHORIZED);
        }


    }


}
