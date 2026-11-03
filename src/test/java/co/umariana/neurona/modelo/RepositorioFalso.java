package co.umariana.neurona.modelo;

import co.umariana.neurona.persistencia.Repositorio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Repositorio de mentira (en memoria) para probar el modelo sin depender del archivo .txt. */
class RepositorioFalso implements Repositorio {

    List<Objeto> guardados = new ArrayList<>();
    int vecesGuardado = 0;
    boolean fallar = false;

    @Override
    public List<Objeto> cargar() throws IOException {
        if (fallar) throw new IOException("fallo simulado");
        return new ArrayList<>(guardados);
    }

    @Override
    public void guardar(List<Objeto> objetos) throws IOException {
        if (fallar) throw new IOException("fallo simulado");
        guardados = new ArrayList<>(objetos);
        vecesGuardado++;
    }
}
