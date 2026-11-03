package co.umariana.neurona.web;

import co.umariana.neurona.modelo.ServicioObjetos;

/**
 * Punto único de acceso al modelo desde los servlets. NO se modifica.
 * Los servlets hacen {@code Contexto.servicio().listar()}, etc.
 */
public final class Contexto {

    private static volatile ServicioObjetos servicio;

    private Contexto() {
    }

    public static ServicioObjetos servicio() {
        ServicioObjetos s = servicio;
        if (s == null) {
            throw new IllegalStateException("El modelo no está disponible todavía. Revise la consola del servidor: "
                    + "probablemente falta implementar alguna clase (GestorObjetos, ListaDoble o RepositorioTxt).");
        }
        return s;
    }

    /** Lo usa {@link AppListener} al arrancar y las pruebas para poner un servicio de mentira. */
    public static void usar(ServicioObjetos nuevo) {
        servicio = nuevo;
    }
}
