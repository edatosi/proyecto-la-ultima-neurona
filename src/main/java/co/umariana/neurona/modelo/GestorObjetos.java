package co.umariana.neurona.modelo;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import co.umariana.neurona.estructura.Lista;
import co.umariana.neurona.persistencia.Repositorio;

/**
 * Reglas de negocio del Neuronario (tarea de MODELO)
 *
 * <p>Guarda los objetos en una {@link Lista} (la estructura de datos del equipo) y, después de
 * cada cambio, los guarda con el {@link Repositorio}. No debe usar {@code ArrayList} ni otra
 * colección de Java para guardar los datos: eso lo hace la {@code Lista}.
 */
public class GestorObjetos implements ServicioObjetos {

    private final Lista<Objeto> lista;
    private final Repositorio repositorio;

    /**
     * Al crearse, carga con el repositorio los objetos ya guardados y los agrega a la lista.
     * Si el repositorio lanza {@link java.io.IOException}, se relanza como {@link java.io.UncheckedIOException}.
     */
    public GestorObjetos(Lista<Objeto> lista, Repositorio repositorio) {
        this.lista = lista;
        this.repositorio = repositorio;
        try {
            for (Objeto objeto : repositorio.cargar()) {
                lista.agregar(objeto);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void agregar(Objeto objeto) {
        if (objeto == null) {
            throw new IllegalArgumentException("El objeto no puede ser nulo");
        }
        objeto.validar();
        if (indiceDe(objeto.getCodigo()) >= 0) {
            throw new IllegalArgumentException("Ya existe un objeto con el código " + objeto.getCodigo());
        }
        lista.agregar(objeto);
        guardar();
    }

    @Override
    public Objeto buscar(String codigo) {
        int indice = indiceDe(codigo);
        return indice >= 0 ? lista.obtener(indice) : null;
    }

    @Override
    public boolean eliminar(String codigo) {
        int indice = indiceDe(codigo);
        if (indice < 0) {
            return false;
        }
        lista.eliminar(indice);
        guardar();
        return true;
    }

    @Override
    public List<Objeto> listar() {
        return copiaEnOrden();
    }

    @Override
    public List<Objeto> ranking() {
        List<Objeto> copia = copiaEnOrden();
        copia.sort(Comparator.comparingInt(Objeto::getCaos).reversed()
                .thenComparing(Objeto::getCodigo));
        return copia;
    }

    @Override
    public Map<String, Double> promedioPorCategoria() {
        Map<String, Double> sumas = new TreeMap<>();
        Map<String, Integer> cantidades = new TreeMap<>();
        for (Objeto objeto : lista) {
            sumas.merge(objeto.getCategoria(), (double) objeto.getCaos(), Double::sum);
            cantidades.merge(objeto.getCategoria(), 1, Integer::sum);
        }
        Map<String, Double> promedios = new TreeMap<>();
        for (Map.Entry<String, Double> e : sumas.entrySet()) {
            promedios.put(e.getKey(), e.getValue() / cantidades.get(e.getKey()));
        }
        return promedios;
    }

    @Override
    public int total() {
        return lista.tamano();
    }

    @Override
    public Objeto actual() {
        return lista.actual();
    }

    @Override
    public Objeto siguiente() {
        lista.avanzar();
        return lista.actual();
    }

    @Override
    public Objeto anterior() {
        lista.retroceder();
        return lista.actual();
    }

    // ---------- Auxiliares ---------

    /** Posición del objeto con ese código en la lista, o -1 si no existe. */
    private int indiceDe(String codigo) {
        if (codigo == null) {
            return -1;
        }
        for (int i = 0; i < lista.tamano(); i++) {
            if (codigo.equals(lista.obtener(i).getCodigo())) {
                return i;
            }
        }
        return -1;
    }

    /** Copia temporal solo para devolver resultados; los datos viven en la Lista. */
    private List<Objeto> copiaEnOrden() {
        List<Objeto> copia = new ArrayList<>();
        for (Objeto objeto : lista) {
            copia.add(objeto);
        }
        return copia;
    }

    private void guardar() {
        try {
            repositorio.guardar(copiaEnOrden());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}