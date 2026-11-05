<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="co.umariana.neurona.modelo.Objeto,co.umariana.neurona.web.Html" %>
<%
    Objeto objeto = (Objeto) request.getAttribute("objeto");
    Object total = request.getAttribute("total");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Visor · Neuronario</title>
    <link rel="stylesheet" href="css.css">
</head>
<body class="interna">
<header>
    <h1>▶️ Visor</h1>
    <p>Anterior / Siguiente</p>
</header>
<nav>
    <a href="index.html">Inicio</a>
    <a href="catalogo">Catálogo</a>
    <a href="listar">Objetos</a>
    <a href="registrar">Registrar</a>
</nav>
<main>
    <% if (objeto == null) { %>
        <p class="aviso">No hay objetos para mostrar. Agregue alguno desde el
            <a href="catalogo">catálogo</a> o <a href="registrar">regístrelo a mano</a>.</p>
    <% } else { %>
        <article class="visor">
            <h2><%= Html.esc(objeto.getTitulo()) %> <small><%= Html.esc(objeto.getCodigo()) %></small></h2>
            <img class="grande" src="media/<%= Html.esc(objeto.getArchivo()) %>"
                 alt="<%= Html.esc(objeto.getTitulo()) %>">
            <p>
                <span><%= Html.esc(objeto.getCategoria()) %></span>
                <span>Caos <%= objeto.getCaos() %>/10</span>
                <span><%= objeto.getTamanoCm() %> cm</span>
            </p>
            <p><%= Html.esc(objeto.getDescripcion()) %></p>
            <p class="nota">Total de objetos: <%= total %></p>
        </article>
        <p class="botones">
            <a class="boton" href="visor?accion=anterior">◀ Anterior</a>
            <a class="boton" href="visor?accion=siguiente">Siguiente ▶</a>
        </p>
    <% } %>
</main>
<footer>Proyecto final · Estructuras de Datos I</footer>
</body>
</html>
