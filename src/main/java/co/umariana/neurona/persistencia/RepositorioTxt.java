package co.umariana.neurona.persistencia;

import co.umariana.neurona.modelo.Objeto;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Guarda los objetos en un archivo de texto (tarea de PERSISTENCIA).
 *
 * <p>Formato: UNA línea por objeto, con los campos separados por {@code ;} y en este orden:
 * <pre>codigo;titulo;categoria;caos;tamanoCm;descripcion;archivo</pre>
 * Ejemplo:
 * <pre>L001;Carro rojo;Objeto;2;1.32;Objeto de ejemplo de todos los días;car.webp</pre>
 * <ul>
 *   <li>Codificación UTF-8 (para las tildes y la ñ).</li>
 *   <li>{@code tamanoCm} se escribe con punto decimal.</li>
 *   <li>{@code cargar}: si el archivo no existe devuelve una lista vacía; ignora las líneas en
 *       blanco y las líneas dañadas (cantidad de campos distinta de 7 o números inválidos).</li>
 *   <li>{@code guardar}: reemplaza todo el contenido del archivo y crea las carpetas que falten.
 *       Una lista vacía deja el archivo vacío.</li>
 * </ul>
 */
public class RepositorioTxt implements Repositorio {

    private final Path archivo;

    public RepositorioTxt(Path archivo) {
        this.archivo = archivo;
    }

    @Override
    public List<Objeto> cargar() throws IOException {
        // TODO (PERSISTENCIA)
        throw new UnsupportedOperationException("TODO: cargar()");
    }

    @Override
    public void guardar(List<Objeto> objetos) throws IOException {
        // TODO (PERSISTENCIA)
        throw new UnsupportedOperationException("TODO: guardar()");
    }
}
