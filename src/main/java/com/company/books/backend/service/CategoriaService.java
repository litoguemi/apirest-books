package com.company.books.backend.service;

import com.company.books.backend.model.Categoria;
import com.company.books.backend.response.CategoriaResponseRest;
import org.springframework.http.ResponseEntity;

public interface CategoriaService {

    public ResponseEntity<CategoriaResponseRest> buscarCategorias();
    public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id);
    public ResponseEntity<CategoriaResponseRest> crear(Categoria categoria);
    public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id);
    public ResponseEntity<CategoriaResponseRest> eliminar(Long id);
}
