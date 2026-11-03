package co.umariana.neurona.web;

import co.umariana.neurona.modelo.Objeto;
import co.umariana.neurona.modelo.ServicioObjetos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Servicio de mentira que anota lo que le piden: permite probar los servlets sin el resto del equipo. */
class ServicioFalso implements ServicioObjetos {

    final List<Objeto> datos = new ArrayList<>();
    final List<Objeto> ranking = new ArrayList<>();
    final Map<String, Double> promedios = new TreeMap<>();
    final List<String> llamadas = new ArrayList<>();
    RuntimeException fallarAlAgregar = null;
    Objeto cursorActual = null;
    Objeto cursorSiguiente = null;
    Objeto cursorAnterior = null;

    @Override public void agregar(Objeto l) {
        llamadas.add("agregar");
        if (fallarAlAgregar != null) throw fallarAlAgregar;
        datos.add(l);
    }
    @Override public Objeto buscar(String codigo) { llamadas.add("buscar"); return null; }
    @Override public boolean eliminar(String codigo) { llamadas.add("eliminar:" + codigo); return true; }
    @Override public List<Objeto> listar() { llamadas.add("listar"); return datos; }
    @Override public List<Objeto> ranking() { llamadas.add("ranking"); return ranking; }
    @Override public Map<String, Double> promedioPorCategoria() { llamadas.add("promedios"); return promedios; }
    @Override public int total() { return datos.size(); }
    @Override public Objeto actual() { llamadas.add("actual"); return cursorActual; }
    @Override public Objeto siguiente() { llamadas.add("siguiente"); return cursorSiguiente; }
    @Override public Objeto anterior() { llamadas.add("anterior"); return cursorAnterior; }
}
