package com.company.books.backend.controllers;

import com.company.books.backend.controller.CategoriaController;
import com.company.books.backend.model.Categoria;
import com.company.books.backend.response.CategoriaResponseRest;
import com.company.books.backend.service.CategoriaService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CategoriaControllerTest {

    @InjectMocks
    private CategoriaController categoriaController;

    @Mock
    private CategoriaService service;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void crearTest(){
        //Genera contexto para solicitud de http
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Categoria categoria = new Categoria(Long.valueOf(1),"Fisica","Libros de ejercicios de fisica");

        //Cuando encuentra el metodo crear, retorne la entidad de respuesta con estado OK
        when(service.crear(any(Categoria.class))).thenReturn(new ResponseEntity<CategoriaResponseRest>(HttpStatus.OK));
        ResponseEntity<CategoriaResponseRest> response = categoriaController.crear(categoria);

        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

}
