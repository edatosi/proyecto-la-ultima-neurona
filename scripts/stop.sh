#!/usr/bin/env bash
# Cierra una ejecución anterior de la aplicación que haya quedado abierta (ocupando el puerto).
#
#   ./scripts/stop.sh          cierra lo que use el puerto 8080
#   ./scripts/stop.sh 8081     cierra lo que use el puerto 8081
#
# Solo cierra procesos de Java, para no tocar otros programas.
set -uo pipefail
PUERTO="${1:-8080}"
cerrados=0

case "$(uname -s)" in
    MINGW*|MSYS*|CYGWIN*)   # Git Bash en Windows
        pids=$(netstat -ano | tr -d '\r' | awk -v p=":$PUERTO" '$2 ~ p"$" && $4 == "LISTENING" { print $5 }' | sort -u)
        for pid in $pids; do
            if tasklist //FI "PID eq $pid" 2>/dev/null | grep -qi "java"; then
                taskkill //PID "$pid" //F >/dev/null && { echo "Cerrado el proceso Java $pid (puerto $PUERTO)."; cerrados=1; }
            fi
        done ;;
    *)                      # Linux y macOS
        if command -v lsof >/dev/null 2>&1; then
            pids=$(lsof -ti "tcp:$PUERTO" -sTCP:LISTEN 2>/dev/null | sort -u)
        elif command -v fuser >/dev/null 2>&1; then
            pids=$(fuser "$PUERTO/tcp" 2>/dev/null)
        else
            echo "No se encontró 'lsof' ni 'fuser'. Cierre el proceso Java manualmente." >&2
            exit 1
        fi
        for pid in $pids; do
            if ps -p "$pid" -o comm= 2>/dev/null | grep -qi "java"; then
                kill "$pid" && { echo "Cerrado el proceso Java $pid (puerto $PUERTO)."; cerrados=1; }
            fi
        done ;;
esac

if [ "$cerrados" -eq 0 ]; then
    echo "No había ninguna aplicación Java usando el puerto $PUERTO."
fi
