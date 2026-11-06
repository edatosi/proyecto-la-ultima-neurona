package co.umariana.neurona.persistencia;

import co.umariana.neurona.modelo.Objeto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
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
        if (Files.notExists(archivo)) {
            return new ArrayList<>();
        }

        List<Objeto> objetos = new ArrayList<>();
        boolean primera = true;
        for (String linea : Files.readAllLines(archivo, StandardCharsets.UTF_8)) {
            // El Bloc de notas de Windows puede guardar el archivo con una marca invisible (BOM) al inicio;
            // si no se quita, el primer código quedaría como "\uFEFFO001" y no se reconocería.
            if (primera) {
                primera = false;
                if (linea.startsWith("\uFEFF")) {
                    linea = linea.substring(1);
                }
            }
            if (linea.isBlank()) {
                continue;
            }

            String[] campos = linea.split(";", -1);
            if (campos.length != 7) {
                continue;
            }

            try {
                objetos.add(new Objeto(
                        campos[0],
                        campos[1],
                        campos[2],
                        Integer.parseInt(campos[3]),
                        Double.parseDouble(campos[4]),
                        campos[5],
                        campos[6]));
            } catch (NumberFormatException e) {
                // Una línea con valores numéricos inválidos no impide cargar las demás.
            }
        }
        return objetos;
    }

    @Override
    public void guardar(List<Objeto> objetos) throws IOException {
        Path carpeta = archivo.getParent();
        if (carpeta != null) {
            Files.createDirectories(carpeta);
        }

        List<String> lineas = objetos.stream()
                .map(objeto -> String.join(";",
                        objeto.getCodigo(),
                        objeto.getTitulo(),
                        objeto.getCategoria(),
                        Integer.toString(objeto.getCaos()),
                        Double.toString(objeto.getTamanoCm()),
                        objeto.getDescripcion(),
                        objeto.getArchivo()))
                .toList();

        Files.write(
                archivo,
                lineas,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE);
    }
}
