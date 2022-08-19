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

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id) {
        log.info("Inicio metodo buscarPorId");
        CategoriaResponseRest response = new CategoriaResponseRest();
        List<Categoria> list = new ArrayList<>();

        try{
            Optional<Categoria> categoria = categoriaDao.findById(id);
            if (categoria.isPresent()){
                list.add(categoria.get());
                response.getCategoriaResponse().setCategoria(list);
            }else{
                log.error("Error al consultar categoria");
                response.setMetadata("Respuesta nok","-1","Categoria no encontrada");
                return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);//devuelve 404
            }
        }catch (Exception e){
            log.error("Error al consultar categoria");
            response.setMetadata("Respuesta nok","-1","Error al consultar categoria");
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);//devuelve 500
        }
        response.setMetadata("Respuesta ok","00", "Respuesta exitosa");
        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> crear(Categoria categoria) {
        log.info("Inicio metodo crear categoria");
        CategoriaResponseRest response = new CategoriaResponseRest();
        List<Categoria> list = new ArrayList<>();
        try{
            Categoria categoriaGuardada = categoriaDao.save(categoria);
            if(categoriaGuardada != null){
                list.add(categoriaGuardada);
                response.getCategoriaResponse().setCategoria(list);
            }else{
                log.error("Error al crear categoria");
                response.setMetadata("Respuesta nok","-1","Categoria no creada");
                return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);//devuelve 403
            }
        }catch (Exception e){
            log.error("Error al crear categoria");
            response.setMetadata("Respuesta nok","-1","Error al crear categoria");
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);//devuelve 500
        }

        response.setMetadata("Respuesta ok","00", "Categoria creada");
        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id) {
        log.info("Inicio metodo actualizar categoria");
        CategoriaResponseRest response = new CategoriaResponseRest();
        List<Categoria> list = new ArrayList<>();
        try{
            Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);
            if(categoriaBuscada.isPresent()){
                categoriaBuscada.get().setNombre(categoria.getNombre());
                categoriaBuscada.get().setDescripcion(categoria.getDescripcion());

                Categoria categoriaActualizar = categoriaDao.save(categoriaBuscada.get());//actualizando
                if(categoriaActualizar != null){
                    list.add(categoriaActualizar);
                    response.getCategoriaResponse().setCategoria(list);
                }else{
                    log.error("Error al actualizar categoria");
                    response.setMetadata("Respuesta nok","-1","Categoria no actualizada");
                    return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.BAD_REQUEST);//devuelve 403
                }
            }else {
                log.error("Error al consultar categoria");
                response.setMetadata("Respuesta nok","-1","Categoria no encontrada");
                return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.NOT_FOUND);//devuelve 404
            }

        }catch (Exception e){
            log.error("Error al actualizar categoria", e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta nok","-1","Error al actualizar categoria");
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);//devuelve 500
        }
        response.setMetadata("Respuesta ok","00", "Categoria actualizada");
        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
    }

    @Override
    @Transactional
    public ResponseEntity<CategoriaResponseRest> eliminar(Long id) {
        log.info("Inicio metodo eliminar categoria");
        CategoriaResponseRest response = new CategoriaResponseRest();
        List<Categoria> list = new ArrayList<>();
        try{
            categoriaDao.deleteById(id);
        }catch (Exception e){
            log.error("Error al eliminar categoria", e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta nok","-1","Error al eliminar categoria");
            return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);//devuelve 500
        }
        response.setMetadata("Respuesta ok","00", "Categoria eliminada");
        return new ResponseEntity<CategoriaResponseRest>(response, HttpStatus.OK); //devuelve 200
    }
}
