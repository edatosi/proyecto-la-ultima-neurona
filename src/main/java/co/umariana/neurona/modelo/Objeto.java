package co.umariana.neurona.modelo;

/**
 * Un objeto del Neuronario: una imagen con su título, categoría y nivel de caos.
 *
 * <p>La clase ya tiene sus atributos, constructor y getters. Lo que falta es {@link #validar()}
 * (tarea de MODELO).
 */
public class Objeto {

    private final String codigo;
    private final String titulo;
    private final String categoria;
    private final int caos;
    private final double tamanoCm;
    private final String descripcion;
    private final String archivo;

    public Objeto(String codigo, String titulo, String categoria, int caos,
                double tamanoCm, String descripcion, String archivo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.categoria = categoria;
        this.caos = caos;
        this.tamanoCm = tamanoCm;
        this.descripcion = descripcion;
        this.archivo = archivo;
    }

    /**
     * Revisa que el objeto sea válido. Si no lo es, lanza {@link IllegalArgumentException}
     * con un mensaje que explique el problema. Reglas:
     * <ul>
     *   <li>{@code codigo}, {@code titulo}, {@code categoria} y {@code archivo} no pueden ser nulos ni estar en blanco.</li>
     *   <li>{@code caos} debe estar entre 1 y 10 (incluidos).</li>
     *   <li>{@code tamanoCm} debe ser mayor que 0.</li>
     *   <li>{@code descripcion} puede estar vacía, pero no ser nula.</li>
     *   <li>Ningún texto puede contener el carácter {@code ;} (separa los campos del archivo .txt)
     *       ni saltos de línea.</li>
     * </ul>
     */
        public void validar() {
        validarTexto("El código", codigo, true);
        validarTexto("El título", titulo, true);
        validarTexto("La categoría", categoria, true);
        validarTexto("El archivo", archivo, true);
        validarTexto("La descripción", descripcion, false);

        if (caos < 1 || caos > 10) {
            throw new IllegalArgumentException("El caos debe estar entre 1 y 10, pero es " + caos);
        }
        if (Double.isNaN(tamanoCm) || tamanoCm <= 0) {
            throw new IllegalArgumentException("El tamaño debe ser mayor que 0, pero es " + tamanoCm);
        }
    }

    /** Revisa un texto: nulo, en blanco (si es obligatorio), ';' y saltos de línea. */
    private static void validarTexto(String nombre, String valor, boolean obligatorio) {
        if (valor == null) {
            throw new IllegalArgumentException(nombre + " no puede ser nulo");
        }
        if (obligatorio && valor.isBlank()) {
            throw new IllegalArgumentException(nombre + " no puede estar en blanco");
        }
        if (valor.indexOf(';') >= 0) {
            throw new IllegalArgumentException(nombre + " no puede contener el carácter ';'");
        }
        if (valor.indexOf('\n') >= 0 || valor.indexOf('\r') >= 0) {
            throw new IllegalArgumentException(nombre + " no puede contener saltos de línea");
        }
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; } 
    public String getCategoria() { return categoria; }
    public int getCaos() { return caos; }
    public double getTamanoCm() { return tamanoCm; }
    public String getDescripcion() { return descripcion; }
    public String getArchivo() { return archivo; }

    @Override
    public String toString() {
        return codigo + " - " + titulo + " (" + categoria + ", caos " + caos + ")";
    }
}
