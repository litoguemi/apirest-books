package com.company.books.backend.service;

import com.company.books.backend.model.Libro;
import com.company.books.backend.response.LibroResponseRest;
import org.springframework.http.ResponseEntity;

public interface LibroService {

    public ResponseEntity<LibroResponseRest> buscarLibros();
    public ResponseEntity<LibroResponseRest> buscarPorId(Long id);
    public ResponseEntity<LibroResponseRest> crear(Libro libro);
    public ResponseEntity<LibroResponseRest> actualizar(Libro libro, Long id);
    public ResponseEntity<LibroResponseRest> eliminar(Long id);
}
