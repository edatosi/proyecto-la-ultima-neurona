package co.umariana.neurona.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * POST /eliminar (tarea de SERVLETS)
 *
 * Elimina un objeto del Neuronario.
 */
@WebServlet("/eliminar")
public class EliminarObjeto extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. Obtener el código del objeto
        String codigo = req.getParameter("codigo");

        // 2. Eliminar el objeto mediante el servicio
        Contexto.servicio().eliminar(codigo);

        // 3. Redireccionar al listado
        resp.sendRedirect("listar");
    }
}