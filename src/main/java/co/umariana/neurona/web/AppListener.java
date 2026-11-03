package co.umariana.neurona.web;

import co.umariana.neurona.estructura.ListaDoble;
import co.umariana.neurona.modelo.GestorObjetos;
import co.umariana.neurona.modelo.Objeto;
import co.umariana.neurona.persistencia.RepositorioTxt;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.nio.file.Path;

/** Arma el modelo al arrancar el servidor (lista + archivo + gestor). NO se modifica. */
@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent evento) {
        Path archivo = Path.of(System.getProperty("objetos.archivo", "objetos.txt"));
        try {
            Contexto.usar(new GestorObjetos(new ListaDoble<Objeto>(), new RepositorioTxt(archivo)));
            evento.getServletContext().log("Neuronario listo. Datos en: " + archivo.toAbsolutePath());
        } catch (Throwable e) {
            // Mientras el equipo no termine sus clases, la aplicación arranca igual pero sin modelo.
            evento.getServletContext().log("El modelo todavía no está disponible: " + e);
            Contexto.usar(null);
        }
    }
}
