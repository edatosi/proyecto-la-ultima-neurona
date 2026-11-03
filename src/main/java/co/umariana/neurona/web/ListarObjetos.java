package co.umariana.neurona.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * GET /listar (tarea de SERVLETS)
 *
 * Muestra los objetos registrados y sus promedios.
 */
@WebServlet("/listar")
public class ListarObjetos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. Obtener el parámetro de orden
        String orden = req.getParameter("orden");

        // 2. Consultar los objetos según el orden solicitado
        if ("ranking".equals(orden)) {
            req.setAttribute("objetos", Contexto.servicio().ranking());
        } else {
            req.setAttribute("objetos", Contexto.servicio().listar());
        }

        // 3. Obtener los promedios por categoría
        req.setAttribute(
                "promedios",
                Contexto.servicio().promedioPorCategoria()
        );

        // 4. Enviar los datos a la página JSP
        req.getRequestDispatcher("/WEB-INF/vistas/listar.jsp")
                .forward(req, resp);
    }
}