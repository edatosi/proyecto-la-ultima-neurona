package co.umariana.neurona.persistencia;

import co.umariana.neurona.modelo.Objeto;

import java.io.IOException;
import java.util.List;

/** Contrato de la persistencia. NO se modifica. {@link RepositorioTxt} es la implementación. */
public interface Repositorio {

    /** Lee todos los objetos guardados. Si todavía no hay nada guardado, devuelve una lista vacía. */
    List<Objeto> cargar() throws IOException;

    /** Guarda TODOS los objetos, reemplazando lo que hubiera antes. */
    void guardar(List<Objeto> objetos) throws IOException;
}
