package co.umariana.neurona.estructura;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Lista DOBLEMENTE enlazada del equipo (tarea de ESTRUCTURA).
 *
 * <p>Cada nodo conoce al nodo {@code siguiente} y al {@code anterior}. Implemente la lista con sus
 * propios nodos: no se permite usar {@code ArrayList}, {@code LinkedList} ni ninguna otra colección
 * de {@code java.util} para guardar los elementos (las pruebas lo revisan).
 *
 * <p>Se espera que guarde la {@code cabeza} y la {@code cola}, de modo que agregar al final y
 * retroceder el cursor sean operaciones inmediatas (sin recorrer toda la lista).
 *
 * <p>Comportamiento del cursor: se DETIENE en los extremos. {@link #avanzar()} desde el último
 * elemento devuelve {@code false} y el cursor no se mueve; {@link #retroceder()} desde el primero
 * también devuelve {@code false}. En una lista vacía ambos devuelven {@code false}.
 *
 * <p>Al eliminar el elemento donde está el cursor, el cursor pasa al siguiente; si no había
 * siguiente, pasa al anterior.
 */
public class ListaDoble<T> implements Lista<T> {

    /** Nodo propio de la lista: guarda el elemento y los enlaces en ambos sentidos. */
    private class Nodo {
        T elemento;
        Nodo siguiente;
        Nodo anterior;

        Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    private Nodo cabeza;
    private Nodo cola;
    private Nodo cursor;
    private int tamano;

    @Override
    public void agregar(T elemento) {
        Nodo nuevo = new Nodo(elemento);
        if (cola == null) {
            cabeza = nuevo;
            cola = nuevo;
            cursor = nuevo;
        } else {
            nuevo.anterior = cola;
            cola.siguiente = nuevo;
            cola = nuevo;
        }
        tamano++;
    }

    @Override
    public void insertar(int indice, T elemento) {
        if (indice < 0 || indice > tamano) {
            throw new IndexOutOfBoundsException("Índice: " + indice + ", tamaño: " + tamano);
        }
        if (indice == tamano) {
            agregar(elemento);
            return;
        }
        Nodo siguiente = nodoEn(indice);
        Nodo nuevo = new Nodo(elemento);
        nuevo.siguiente = siguiente;
        nuevo.anterior = siguiente.anterior;
        if (siguiente.anterior == null) {
            cabeza = nuevo;
        } else {
            siguiente.anterior.siguiente = nuevo;
        }
        siguiente.anterior = nuevo;
        tamano++;
    }

    @Override
    public T obtener(int indice) {
        verificarIndice(indice);
        return nodoEn(indice).elemento;
    }

    @Override
    public T eliminar(int indice) {
        verificarIndice(indice);
        Nodo nodo = nodoEn(indice);

        if (nodo == cursor) {
            cursor = (nodo.siguiente != null) ? nodo.siguiente : nodo.anterior;
        }

        if (nodo.anterior == null) {
            cabeza = nodo.siguiente;
        } else {
            nodo.anterior.siguiente = nodo.siguiente;
        }
        if (nodo.siguiente == null) {
            cola = nodo.anterior;
        } else {
            nodo.siguiente.anterior = nodo.anterior;
        }

        T elemento = nodo.elemento;
        nodo.siguiente = null;
        nodo.anterior = null;
        nodo.elemento = null;
        tamano--;
        return elemento;
    }

    @Override
    public int tamano() {
        return tamano;
    }

    @Override
    public boolean estaVacia() {
        return tamano == 0;
    }

    @Override
    public void vaciar() {
        cabeza = null;
        cola = null;
        cursor = null;
        tamano = 0;
    }

    @Override
    public T actual() {
        return (cursor == null) ? null : cursor.elemento;
    }

    @Override
    public boolean avanzar() {
        if (cursor == null || cursor.siguiente == null) {
            return false;
        }
        cursor = cursor.siguiente;
        return true;
    }

    @Override
    public boolean retroceder() {
        if (cursor == null || cursor.anterior == null) {
            return false;
        }
        cursor = cursor.anterior;
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo siguiente = cabeza;

            @Override
            public boolean hasNext() {
                return siguiente != null;
            }

            @Override
            public T next() {
                if (siguiente == null) {
                    throw new NoSuchElementException();
                }
                T elemento = siguiente.elemento;
                siguiente = siguiente.siguiente;
                return elemento;
            }
        };
    }

    // ---------- auxiliares ----------

    private void verificarIndice(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Índice: " + indice + ", tamaño: " + tamano);
        }
    }

    /** Busca el nodo desde el extremo más cercano (cabeza o cola). Requiere índice válido. */
    private Nodo nodoEn(int indice) {
        Nodo actual;
        if (indice < tamano / 2) {
            actual = cabeza;
            for (int i = 0; i < indice; i++) {
                actual = actual.siguiente;
            }
        } else {
            actual = cola;
            for (int i = tamano - 1; i > indice; i--) {
                actual = actual.anterior;
            }
        }
        return actual;
        
    }
}
