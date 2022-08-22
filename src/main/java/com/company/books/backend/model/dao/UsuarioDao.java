package com.company.books.backend.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {

    public Usuario findByNombreUsuario(String nombreUsuario);

    @Query("select u from Usuario u where u.nombreUsuario=?1 ")
    public Usuario findByNombreUsuarioV2(String nombreUsuario);
}
