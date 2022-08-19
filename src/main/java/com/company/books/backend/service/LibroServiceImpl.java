package com.company.books.backend.service;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.Libro;
import com.company.books.backend.model.dao.LibroDao;
import com.company.books.backend.response.CategoriaResponseRest;
import com.company.books.backend.response.LibroResponseRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibroServiceImpl implements LibroService{

    private static final Logger log = LoggerFactory.getLogger(LibroServiceImpl.class);

    @Autowired
    private LibroDao libroDao;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<LibroResponseRest> buscarLibros() {
        log.info("inicio metodo buscarLibros()");

        LibroResponseRest response = new LibroResponseRest();
        try{
            List<Libro> libro = libroDao.findAll();
            response.getLibroResponse().setLibro(libro);
            response.setMetadata("Respuesta ok","00", "Respuesta exitosa");
        }catch (Exception e){
            response.setMetadata("Respuesta nok","-1","Error al consulta libros");
            log.error("Error al consultar libros", e.getMessage());
            e.getStackTrace();
            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);//error 500
        }

        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);//devuelve 200
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<LibroResponseRest> buscarPorId(Long id) {
        log.info("Inicio metodo buscarPorId");
        LibroResponseRest response = new LibroResponseRest();
        List<Libro> list = new ArrayList<>();

        try{
            Optional<Libro> libro = libroDao.findById(id);
            if (libro.isPresent()){
                list.add(libro.get());
                response.getLibroResponse().setLibro(list);
            }else{
                log.error("Error al consultar libro");
                response.setMetadata("Respuesta nok","-1","Libro no encontrado");
                return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);//devuelve 404
            }
        }catch (Exception e){
            log.error("Error al consultar libro");
            response.setMetadata("Respuesta nok","-1","Error al consultar libro");
            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);//devuelve 500
        }
        response.setMetadata("Respuesta ok","00", "Respuesta exitosa");
        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //devuelve 200
    }

    @Override
    @Transactional
    public ResponseEntity<LibroResponseRest> crear(Libro libro) {
        log.info("Inicio metodo crear libro");
        LibroResponseRest response = new LibroResponseRest();
        List<Libro> list = new ArrayList<>();
        try{
            Libro libroGuardado = libroDao.save(libro);
            if(libroGuardado != null){
                list.add(libroGuardado);
                response.getLibroResponse().setLibro(list);
            }else{
                log.error("Error al crear librp");
                response.setMetadata("Respuesta nok","-1","Libro no creado");
                return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);//devuelve 403
            }
        }catch (Exception e){
            log.error("Error al crear libro");
            response.setMetadata("Respuesta nok","-1","Error al crear libro");
            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);//devuelve 500
        }

        response.setMetadata("Respuesta ok","00", "Libro creado");
        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //devuelve 200
    }

    @Override
    @Transactional
    public ResponseEntity<LibroResponseRest> actualizar(Libro libro, Long id) {
        log.info("Inicio metodo actualizar libro");
        LibroResponseRest response = new LibroResponseRest();
        List<Libro> list = new ArrayList<>();
        try{
            Optional<Libro> LibroBuscado = libroDao.findById(id);
            if(LibroBuscado.isPresent()){
                LibroBuscado.get().setNombre(libro.getNombre());
                LibroBuscado.get().setDescripcion(libro.getDescripcion());
                LibroBuscado.get().setCategoria(libro.getCategoria());

                Libro libroActualizar = libroDao.save(LibroBuscado.get());//actualizando
                if(libroActualizar != null){
                    list.add(libroActualizar);
                    response.getLibroResponse().setLibro(list);
                }else{
                    log.error("Error al actualizar libro");
                    response.setMetadata("Respuesta nok","-1","Libro no actualizado");
                    return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);//devuelve 403
                }
            }else {
                log.error("Error al consultar libro");
                response.setMetadata("Respuesta nok","-1","Libro no encontrado");
                return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);//devuelve 404
            }

        }catch (Exception e){
            log.error("Error al actualizar libro", e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta nok","-1","Error al actualizar libro");
            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);//devuelve 500
        }
        response.setMetadata("Respuesta ok","00", "Libro actualizado");
        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //devuelve 200
    }

    @Override
    @Transactional
    public ResponseEntity<LibroResponseRest> eliminar(Long id) {
        log.info("Inicio metodo eliminar libro");
        LibroResponseRest response = new LibroResponseRest();
        List<Libro> list = new ArrayList<>();
        try{
            libroDao.deleteById(id);
        }catch (Exception e){
            log.error("Error al eliminar libro", e.getMessage());
            e.getStackTrace();
            response.setMetadata("Respuesta nok","-1","Error al eliminar libro");
            return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);//devuelve 500
        }
        response.setMetadata("Respuesta ok","00", "Libro eliminado");
        return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //devuelve 200
    }
}
