package co.umariana.neurona.modelo;

import java.util.List;
import java.util.Map;

/**
 * Lo que la aplicación web necesita del modelo. NO se modifica.
 * {@link GestorObjetos} es la implementación (tarea de MODELO).
 */
public interface ServicioObjetos {

    /** Valida el objeto, rechaza códigos repetidos y guarda el cambio en el archivo. */
    void agregar(Objeto objeto);

    /** Devuelve el objeto con ese código, o {@code null} si no existe. */
    Objeto buscar(String codigo);

    /** Elimina el objeto con ese código y guarda el cambio. Devuelve {@code false} si no existía. */
    boolean eliminar(String codigo);

    /** Todos los objetos, en el orden de la lista. */
    List<Objeto> listar();

    /** Lista NUEVA ordenada de mayor a menor caos (empate: por código). No altera el orden de la lista. */
    List<Objeto> ranking();

    /** Promedio de caos por categoría, ordenado alfabéticamente por categoría. */
    Map<String, Double> promedioPorCategoria();

    /** Cantidad de objetos guardados. */
    int total();

    /** Objeto donde está el cursor (el visor), o {@code null} si no hay objetos. */
    Objeto actual();

    /** Mueve el cursor al siguiente objeto y lo devuelve ({@code null} si no hay objetos). */
    Objeto siguiente();

    /** Mueve el cursor al objeto anterior y lo devuelve ({@code null} si no hay objetos). */
    Objeto anterior();
}
