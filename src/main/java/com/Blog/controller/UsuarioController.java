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
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente" ),
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

    @Operation(summary = "Listar Usuarios",
               description = "Permite Listar Todos Los Usuarios solo a los Rol 'Administrador'.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @GetMapping("{idUser}/all")
    public ResponseEntity<?>getall(@PathVariable("idUser")Long id){

        Usuario usuarioEncontrado = usuarioService.obteneUsuario(id);
        if(usuarioEncontrado.getRol().equals("Admin"))
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

        if (usuarioEncontrado.getRol().equals("Admin") || usuarioEncontrado.getId_usuario() == idFound) {
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
        @ApiResponse(responseCode = "401", description = "Sin Permisos Necesarios")
    })
    @PutMapping("/{idUser}/modificar")
    public ResponseEntity<String>modificarUsuario(@PathVariable("idUser")Long id , @RequestBody Usuario usuarioModificado)
    {
       Usuario usuarioencontrado = usuarioService.obteneUsuario(id);

       if(usuarioencontrado!=null){
       if(usuarioencontrado.getRol().equals("Admin")|| usuarioencontrado.getId_usuario() == usuarioModificado.getId_usuario()){
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


    @DeleteMapping("/{idUser}/{idDelete}")
    public ResponseEntity<?>eliminarUsuario(@PathVariable("idUser")Long id , @PathVariable("idDelete")Long idDelete)
    {
        Usuario usuarioEncontrado = usuarioService.obteneUsuario(id);
        Usuario usuarioDelete = usuarioService.obteneUsuario(idDelete);
         if (usuarioEncontrado == null ||  usuarioDelete == null) {
            return new ResponseEntity<>("Usuario No Encontrado", HttpStatus.NOT_FOUND);
        }   

        if (usuarioEncontrado.getRol().equals("Admin") || usuarioEncontrado.getId_usuario() == idDelete) {
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
