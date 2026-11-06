<%@ page contentType="text/html;charset=UTF-8" language="java" import="co.umariana.neurona.web.Html" %>
<%
    String error = (String) request.getAttribute("error");
    // Si hubo un error, se conservan los valores que escribió la persona.
    String codigo = request.getParameter("codigo");
    String titulo = request.getParameter("titulo");
    String categoria = request.getParameter("categoria");
    String caos = request.getParameter("caos");
    String tamano = request.getParameter("tamano");
    String descripcion = request.getParameter("descripcion");
    String archivo = request.getParameter("archivo");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registrar · Neuronario</title>
    <link rel="stylesheet" href="css.css">
</head>
<body class="interna">
<header>
    <h1>➕ Registrar objeto</h1>
    <p>Agregar un objeto a mano</p>
</header>
<nav>
    <a href="index.html">Inicio</a>
    <a href="catalogo">Catálogo</a>
    <a href="listar">Objetos</a>
    <a href="visor">Visor</a>
</nav>
<main>
    <% if (error != null) { %>
        <p class="error">✖ <%= Html.esc(error) %></p>
    <% } %>

    <form method="post" action="registrar" accept-charset="UTF-8" class="formulario">
        <label>Código
            <input type="text" name="codigo" required maxlength="30" value="<%= Html.esc(codigo) %>">
        </label>
        <label>Título
            <input type="text" name="titulo" required maxlength="100" value="<%= Html.esc(titulo) %>">
        </label>
        <label>Categoría
            <input type="text" name="categoria" required maxlength="50" value="<%= Html.esc(categoria) %>">
        </label>
        <label>Caos (1 a 10)
            <input type="number" name="caos" required min="1" max="10" step="1" value="<%= Html.esc(caos) %>">
        </label>
        <label>Tamaño (cm)
            <input type="number" name="tamano" required min="0.01" step="any" value="<%= Html.esc(tamano) %>">
        </label>
        <label>Descripción
            <textarea name="descripcion" rows="3" maxlength="300"><%= Html.esc(descripcion) %></textarea>
        </label>
        <label>Archivo de imagen (carpeta <code>media/</code>)
            <input type="text" name="archivo" required list="imagenes" placeholder="car.webp"
                   value="<%= Html.esc(archivo) %>">
            <datalist id="imagenes">
                <option value="car.webp">
                <option value="chicken.png">
                <option value="tree.png">
            </datalist>
        </label>
        <p class="nota">Los textos no pueden contener el carácter <code>;</code>.</p>
        <button type="submit">Registrar</button>
    </form>
</main>
<footer>Proyecto final · Estructuras de Datos I</footer>
</body>
</html>
