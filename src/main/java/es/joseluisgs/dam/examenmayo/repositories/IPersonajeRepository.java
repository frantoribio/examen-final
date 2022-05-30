package es.joseluisgs.dam.examenmayo.repositories;

import es.joseluisgs.dam.examenmayo.models.Personaje;

import java.sql.SQLException;

public interface IPersonajeRepository extends CrudRepository<Personaje, Integer>{

    void deleteAll() throws SQLException;

}
