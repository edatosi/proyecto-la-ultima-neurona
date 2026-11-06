<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.List,java.util.Locale,java.util.Map,co.umariana.neurona.modelo.Objeto,co.umariana.neurona.web.Html" %>
<%
    @SuppressWarnings("unchecked")
    List<Objeto> objetos = (List<Objeto>) request.getAttribute("objetos");
    @SuppressWarnings("unchecked")
    Map<String, Double> promedios = (Map<String, Double>) request.getAttribute("promedios");
    boolean ranking = "ranking".equals(request.getParameter("orden"));
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Objetos · Neuronario</title>
    <link rel="stylesheet" href="css.css">
</head>
<body class="interna">
<header>
    <h1>📋 Objetos</h1>
    <p>Lista, ranking y promedios</p>
</header>
<nav>
    <a href="index.html">Inicio</a>
    <a href="catalogo">Catálogo</a>
    <a href="registrar">Registrar</a>
    <a href="visor">Visor</a>
</nav>
<main class="ancha">
    <p class="orden">
        Orden:
        <% if (ranking) { %>
            <a href="listar">Lista</a> · <strong>Ranking por caos</strong>
        <% } else { %>
            <strong>Lista</strong> · <a href="listar?orden=ranking">Ranking por caos</a>
        <% } %>
    </p>

    <% if (objetos == null || objetos.isEmpty()) { %>
        <p class="aviso">Todavía no hay objetos. Agregue alguno desde el
            <a href="catalogo">catálogo</a> o <a href="registrar">regístrelo a mano</a>.</p>
    <% } else { %>
        <div class="tabla-scroll">
        <table>
            <thead>
            <tr>
                <% if (ranking) { %><th>#</th><% } %>
                <th>Imagen</th><th>Código</th><th>Título</th><th>Categoría</th>
                <th>Caos</th><th>Tamaño (cm)</th><th>Descripción</th><th></th>
            </tr>
            </thead>
            <tbody>
            <% int posicion = 0;
               for (Objeto o : objetos) { posicion++; %>
            <tr>
                <% if (ranking) { %><td><%= posicion %></td><% } %>
                <td><img class="mini" src="media/<%= Html.esc(o.getArchivo()) %>"
                         alt="<%= Html.esc(o.getTitulo()) %>"></td>
                <td><%= Html.esc(o.getCodigo()) %></td>
                <td><%= Html.esc(o.getTitulo()) %></td>
                <td><%= Html.esc(o.getCategoria()) %></td>
                <td><%= o.getCaos() %>/10</td>
                <td><%= o.getTamanoCm() %></td>
                <td><%= Html.esc(o.getDescripcion()) %></td>
                <td>
                    <form method="post" action="eliminar"
                          onsubmit="return confirm('¿Eliminar este objeto?');">
                        <input type="hidden" name="codigo" value="<%= Html.esc(o.getCodigo()) %>">
                        <button type="submit" class="peligro">Eliminar</button>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        </div>
    <% } %>

    <h2>Promedio de caos por categoría</h2>
    <% if (promedios == null || promedios.isEmpty()) { %>
        <p class="aviso">Sin datos todavía.</p>
    <% } else { %>
        <table class="compacta">
            <thead><tr><th>Categoría</th><th>Caos promedio</th></tr></thead>
            <tbody>
            <% for (Map.Entry<String, Double> e : promedios.entrySet()) { %>
            <tr>
                <td><%= Html.esc(e.getKey()) %></td>
                <td><%= String.format(Locale.US, "%.2f", e.getValue()) %></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    <% } %>
</main>
<footer>Proyecto final · Estructuras de Datos I</footer>
</body>
</html>
