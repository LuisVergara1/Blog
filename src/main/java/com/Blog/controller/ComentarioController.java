package com.Blog.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Blog.DTO.ComentarioTDO;
import com.Blog.service.comentario.ComentarioService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequestMapping("/comentario")
@RestController
@RequiredArgsConstructor
public class ComentarioController {

    
    private final ComentarioService comentarioService;
    @Operation(summary= "Obtener Todos los Comentarios ", description = "Este EndPoint Permite Buscar un Producto Segun su ID")
    @GetMapping("/all")
    public ResponseEntity<List<ComentarioTDO>> allcomentarios()
    {
        List<ComentarioTDO> comentarios = comentarioService.allComentarios();

        return new ResponseEntity<>(comentarios,HttpStatus.OK);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<List<ComentarioTDO>>comentByPost(@PathVariable("id")Long id)
    {
        List<ComentarioTDO> comentarioTDOs = comentarioService.findByIdPost(id);

        return new ResponseEntity<>(comentarioTDOs,HttpStatus.OK);
    }
}
