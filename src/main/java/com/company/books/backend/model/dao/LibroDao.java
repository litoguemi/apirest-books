package com.company.books.backend.model.dao;

import com.company.books.backend.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroDao extends JpaRepository<Libro,Long> {

}
