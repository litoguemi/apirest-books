package com.company.books.backend.service;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.CategoriaDao;
import com.company.books.backend.response.CategoriaResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService{

    private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);

    @Autowired
    private CategoriaDao categoriaDao;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoriaResponseRest> buscarCategorias() {
        log.info("inicio metodo buscarCategorias()");

        CategoriaResponseRest response = new CategoriaResponseRest();
        try{
            List<Categoria> categoria  = categoriaDao.findAll();
            response.getCategoriaResponse().setCategoria(categoria);
            response.setMetadata("Respuesta ok","00", "Respuesta exitosa");
        }catch (Exception e){
            response.setMetadata("Respuesta nok","-1", "Error al consultar categorias");
            log.error("Error al consultar categorias", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);//devuelve 500
        }

        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
    }
}
