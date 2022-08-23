package com.company.books.backend.service;


import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.CategoriaDao;
import com.company.books.backend.response.CategoriaResponseRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CategoriaServiceImplTest {

    @InjectMocks//injectar mocks existente de esta clase, por ejemplo CategoriaDAO
    CategoriaServiceImpl service;

    @Mock
    CategoriaDao categoriaDao;

    List<Categoria> list = new ArrayList<Categoria>();

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        this.cargarCategorias();
    }

    @Test
    public void buscasCategoriasTest(){
        when(categoriaDao.findAll()).thenReturn(list);
        ResponseEntity<CategoriaResponseRest> response = service.buscarCategorias();

        Assertions.assertEquals(4, response.getBody().getCategoriaResponse().getCategoria().size());
        verify(categoriaDao, times(1)).findAll(); //Verificar que se ha llamado al metodo findAll() una sola vez
    }

    public void cargarCategorias(){
        Categoria cat1 = new Categoria(Long.valueOf(1),"Alimentos", "Alimentos vegetarianos");
        Categoria cat2 = new Categoria(Long.valueOf(2),"Plantas", "Plantas hornamentales");
        Categoria cat3 = new Categoria(Long.valueOf(3),"Quesos", "Quesos para vinos");
        Categoria cat4 = new Categoria(Long.valueOf(4),"Aceitunas", "Aceitunas verdes y rellenas");

        list.add(cat1);
        list.add(cat2);
        list.add(cat3);
        list.add(cat4);
    }
}
