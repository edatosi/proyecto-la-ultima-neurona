package co.umariana.neurona.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import co.umariana.neurona.modelo.Objeto;

/**
 * GET /visor
 *
 * Permite recorrer los objetos registrados.
 */
@WebServlet("/visor")
public class Visor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. Obtener la acción solicitada
        String accion = req.getParameter("accion");

        // 2. Obtener el servicio
        co.umariana.neurona.modelo.ServicioObjetos servicio =
                Contexto.servicio();

        // 3. Obtener el objeto según la acción
        Objeto objeto;

        if ("siguiente".equals(accion)) {

            objeto = servicio.siguiente();

        } else if ("anterior".equals(accion)) {

            objeto = servicio.anterior();

        } else {

            objeto = servicio.actual();
        }

        // 4. Enviar el objeto a la página
        req.setAttribute("objeto", objeto);

        // 5. Enviar la cantidad total
        req.setAttribute("total", servicio.total());

        // 6. Reenviar a la vista
        req.getRequestDispatcher("/WEB-INF/vistas/visor.jsp")
                .forward(req, resp);
    }
}