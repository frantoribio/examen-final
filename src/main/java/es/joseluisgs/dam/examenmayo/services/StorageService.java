package es.joseluisgs.dam.examenmayo.services;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService<T> {

    /**
     * Salva los elementos almacenados en item.
     *
     * @param item Elementos a almacenar.
     * @return true si se ha almacenado correctamente, false en caso contrario.
     */
    boolean save(Path path, T item);

    /**
     * Le el almacenado y lo devuelve en un item T.
     *
     * @return Elementos almacenados.
     */
    T load(Path Path) throws IOException;
}
