#!/usr/bin/env bash
# Inicia la aplicación web. Se detiene con Ctrl + C. Se puede ejecutar todas las veces que quiera.
#
#   ./scripts/start.sh            usa el puerto 8080 (o el siguiente libre si está ocupado)
#   PUERTO=9090 ./scripts/start.sh
#
# Si una ejecución anterior quedó abierta, ciérrela con:  ./scripts/stop.sh
set -euo pipefail
source "$(dirname "${BASH_SOURCE[0]}")/_comun.sh"
exigir_java

PUERTO="${PUERTO:-8080}"

# ¿Hay algo escuchando en ese puerto? (funciona en Linux, macOS y Git Bash de Windows)
puerto_ocupado() {
    (exec 3<>"/dev/tcp/127.0.0.1/$1") 2>/dev/null
}

if puerto_ocupado "$PUERTO"; then
    pedido="$PUERTO"
    for candidato in $(seq $((pedido + 1)) $((pedido + 20))); do
        if ! puerto_ocupado "$candidato"; then
            PUERTO="$candidato"
            break
        fi
    done
    if puerto_ocupado "$PUERTO"; then
        echo "ERROR: los puertos $pedido a $((pedido + 20)) están ocupados. Cierre lo que los usa con ./scripts/stop.sh" >&2
        exit 1
    fi
    echo "AVISO: el puerto $pedido está ocupado (seguramente una ejecución anterior que no se cerró)."
    echo "       Se usará el puerto $PUERTO. Para cerrar la anterior ejecute:  ./scripts/stop.sh $pedido"
fi

echo "Iniciando la aplicación (la primera vez tarda unos minutos)..."
echo "Cuando el texto deje de avanzar, abra http://localhost:$PUERTO/"

# Maven corre en segundo plano para poder cerrarlo SIEMPRE al terminar (Ctrl + C o cerrar la terminal).
# Así no queda un Java vivo ocupando el puerto, que era lo que hacía fallar la segunda ejecución.
./mvnw -B jetty:run -Djetty.http.port="$PUERTO" "$@" &
PID=$!

detener() {
    trap - INT TERM EXIT
    kill "$PID" 2>/dev/null || true
    wait "$PID" 2>/dev/null || true
}
trap detener INT TERM EXIT

codigo=0
wait "$PID" || codigo=$?
exit "$codigo"
