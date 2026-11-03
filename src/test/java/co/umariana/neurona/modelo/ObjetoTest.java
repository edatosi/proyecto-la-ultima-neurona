package co.umariana.neurona.modelo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjetoTest {

    private static Objeto objeto(String codigo, String titulo, String categoria, int caos,
                                   double tamano, String descripcion, String archivo) {
        return new Objeto(codigo, titulo, categoria, caos, tamano, descripcion, archivo);
    }

    private static Objeto valido() {
        return objeto("L001", "Carro rojo", "Objeto", 2, 1.32, "Objeto de ejemplo", "car.webp");
    }

    @Test
    @DisplayName("un objeto correcto es válido")
    void valido_noLanza() {
        assertDoesNotThrow(() -> valido().validar());
    }

    @Test
    @DisplayName("la descripción puede estar vacía")
    void descripcionVaciaEsValida() {
        assertDoesNotThrow(() -> objeto("L1", "T", "C", 5, 1, "", "a.png").validar());
    }

    @Test
    @DisplayName("los límites del caos (1 y 10) son válidos")
    void limitesValidos() {
        assertDoesNotThrow(() -> objeto("L1", "T", "C", 1, 1, "", "a.png").validar());
        assertDoesNotThrow(() -> objeto("L1", "T", "C", 10, 1, "", "a.png").validar());
    }

    @Test
    @DisplayName("el caos fuera de 1..10 es inválida")
    void caosFueraDeRango() {
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T", "C", 0, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T", "C", 11, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T", "C", -3, 1, "", "a.png").validar());
    }

    @Test
    @DisplayName("el tamaño debe ser mayor que 0")
    void tamanoInvalida() {
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T", "C", 5, 0, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T", "C", 5, -1.5, "", "a.png").validar());
    }

    @Test
    @DisplayName("código, título, categoría y archivo no pueden ser nulos ni estar en blanco")
    void textosObligatorios() {
        assertThrows(IllegalArgumentException.class, () -> objeto(null, "T", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("  ", "T", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", null, "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T", " ", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T", "C", 5, 1, "", "").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T", "C", 5, 1, "", null).validar());
    }

    @Test
    @DisplayName("la descripción no puede ser nula")
    void descripcionNula() {
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T", "C", 5, 1, null, "a.png").validar());
    }

    @Test
    @DisplayName("ningún texto puede contener ';' (separa los campos del .txt)")
    void sinPuntoYComa() {
        assertThrows(IllegalArgumentException.class, () -> objeto("L;1", "T", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T;x", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T", "C;x", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T", "C", 5, 1, "a;b", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T", "C", 5, 1, "", "a;.png").validar());
    }

    @Test
    @DisplayName("ningún texto puede contener saltos de línea")
    void sinSaltosDeLinea() {
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T\nx", "C", 5, 1, "", "a.png").validar());
        assertThrows(IllegalArgumentException.class, () -> objeto("L1", "T", "C", 5, 1, "uno\r\ndos", "a.png").validar());
    }

    @Test
    @DisplayName("el mensaje de error dice qué está mal")
    void mensajeDelError() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
                () -> objeto("L1", "T", "C", 11, 1, "", "a.png").validar());
        assertNotNull(e.getMessage());
        assertFalse(e.getMessage().isBlank());
    }
}
