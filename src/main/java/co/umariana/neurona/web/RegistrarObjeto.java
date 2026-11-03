package co.umariana.neurona.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * /registrar (tarea de SERVLETS)
 *
 * Contrato:
 * GET: muestra el formulario.
 * POST: registra un objeto en el Neuronario.
 */
@WebServlet("/registrar")
public class RegistrarObjeto extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/WEB-INF/vistas/formulario.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {

            // 1. Obtener los parámetros enviados por el formulario
            String codigo = req.getParameter("codigo");
            String titulo = req.getParameter("titulo");
            String categoria = req.getParameter("categoria");
            String descripcion = req.getParameter("descripcion");
            String archivo = req.getParameter("archivo");

            // 2. Convertir los valores numéricos
            int caos = Integer.parseInt(req.getParameter("caos"));
            double tamano = Double.parseDouble(req.getParameter("tamano"));

            // 3. Crear el objeto
            co.umariana.neurona.modelo.Objeto objeto =
                    new co.umariana.neurona.modelo.Objeto(
                            codigo,
                            titulo,
                            categoria,
                            caos,
                            tamano,
                            descripcion,
                            archivo
                    );

            // 4. Registrar el objeto mediante el servicio
            Contexto.servicio().agregar(objeto);

            // 5. Redireccionar al listado
            resp.sendRedirect("listar");

        } catch (IllegalArgumentException e) {

            // 6. Guardar el mensaje del error
            req.setAttribute("error", e.getMessage());

            // 7. Volver al formulario
            req.getRequestDispatcher("/WEB-INF/vistas/formulario.jsp")
                    .forward(req, resp);
        }
    }
}