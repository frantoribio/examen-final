package es.joseluisgs.dam.examenmayo.repositories;

import es.joseluisgs.dam.examenmayo.managers.DataBaseManager;
import es.joseluisgs.dam.examenmayo.models.HOUSES;
import es.joseluisgs.dam.examenmayo.models.Personaje;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class PersonajeRepository implements IPersonajeRepository{

    private final DataBaseManager db;
    
    public PersonajeRepository(DataBaseManager db) {
        this.db = db;
    }

    /**
     * Busca un personaje por su id
     *
     * @param id id del personaje
     * @return el personaje encontrado o null si no lo encuentra
     */
    @Override
    public Optional<Personaje> findById(Integer id) throws SQLException {
        String query = "SELECT * FROM HOGWARTS WHERE id = ?";
        db.open();
        ResultSet result = db.select(query, id).orElseThrow(SQLException::new);
        if (result.first()) {
            Personaje personaje = new Personaje(
                    result.getInt("id"),
                    result.getString("nombre"),
                    result.getString("apodo"),
                    LocalDate.parse(result.getString("fNacimiento")),
                    HOUSES.parse(result.getString("casa")),
                    result.getInt("altura"),
                    result.getString("hechizo"));
            db.close();
            return Optional.of(personaje);
        }
        return Optional.empty();
    }
    /**
     * @return lista de personajes
     */
    @Override
    public ObservableList<Personaje> findAll() throws SQLException {
        String query = "SELECT * FROM HOGWARTS";
        db.open();
        ResultSet result = db.select(query).orElseThrow(() -> new SQLException("Error al obtener todos los paises"));
        ArrayList<Personaje> list = new ArrayList<>();
        while (result.next()) {
            list.add(
                    new Personaje(
                            result.getInt("id"),
                            result.getString("nombre"),
                            result.getString("apodo"),
                            LocalDate.parse(result.getString("fNacimiento")),
                            HOUSES.parse(result.getString("casa")),
                            result.getInt("altura"),
                            result.getString("hechizo"))
            );
        }
        db.close();
        return FXCollections.observableList(list);
    }

    /**
     * Añade un personaje
     *
     * @param personaje personaje a añadir
     */
    @Override
    public Optional<Personaje> save(Personaje personaje) throws SQLException {
        String query = "INSERT INTO HOGWARTS VALUES (null, ?, ?, ?, ?, ?, ?)";
        db.open();
        ResultSet res = db.insert(query, personaje.getName(), personaje.getNick(), personaje.getDate(),
                personaje.getHouse(), personaje.getHeight(), personaje.getSpell()).orElseThrow(SQLException::new);

        if (res.first()) {
            personaje.setId(res.getInt("id"));
            db.close();
            return Optional.of(personaje);
        }
        return Optional.empty();
    }

    /**
     * Actualiza un personaje
     *
     * @param personaje personaje con los nuevos datos
     * @return el personaje actualizado
     */
    @Override
    public Optional<Personaje> update(Personaje personaje) throws SQLException {
        this.findById(personaje.getId()).orElseThrow(SQLException::new);
        String query = "UPDATE HOGWARTS SET nombre = ?, apodo = ?, casa = ?, altura = ?, fNacimiento = ?, hechizo = ?" +
                "WHERE id = ?";
        db.open();
        db.update(query, personaje.getName(), personaje.getNick(), personaje.getHouse(),
                personaje.getHeight(), personaje.getDate(), personaje.getSpell(), personaje.getId());
        db.close();
        return Optional.of(personaje);
    }

    /**
     * Elimina un personaje
     *
     * @param personaje personaje a eliminar
     */
    @Override
    public void delete(Personaje personaje) throws SQLException {
        String query = "DELETE FROM HOGWARTS WHERE id = ?";
        db.open();
        db.delete(query, personaje.getId());
        db.close();
    }

    /**
     * Elimina todos
     * @throws SQLException
     */
    @Override
    public void deleteAll() throws SQLException {
        String query = "DELETE FROM HOGWARTS";
        db.open();
        db.delete(query);
        db.close();
    }

}
