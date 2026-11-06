# Proyecto final: Neuronario 🧠

## Prerequisitos

1. **Git**, **Visual Studio Code** y una cuenta de GitHub, como en el LAB-10. En VS Code debe tener **instalada y habilitada para el espacio de trabajo** (*Enable (Workspace)*) la extensión **Extension Pack for Java**. También puede abrir el proyecto con NetBeans.
2. **JDK 17 o superior** (no basta con un JRE). [Link de Descarga](https://adoptium.net/) (Temurin 17). En una Mac con chip Apple, la versión **aarch64**; en Windows, active *Set JAVA_HOME* y *Add to PATH*. Compruébelo con `javac -version`.
3. En Windows, use **Git Bash** para los comandos de este documento.

## Descripción

El **Neuronario** es el inventario de las cosas que todavía caben en la última neurona. Cada **objeto** es una imagen con su nombre, su categoría, un nivel de caos (de 1 a 10) y su tamaño en centímetros. La aplicación permite guardar los objetos, listarlos, hacer un ranking por caos, y recorrerlos con un visor hacia adelante y hacia atrás.

El proyecto se hace **en equipo** y tiene **cinco tareas**. Cuando las cinco estén mergeadas en `main`, la aplicación funciona completa y **todas las pruebas quedan en verde**.

### Lo que ya viene hecho

- Las **3 imágenes** (carpeta `src/main/webapp/media`) y la página **Catálogo** (`/catalogo`), que muestra las imágenes y tiene un botón **Agregar** para guardar cada objeto en el Neuronario.
- La página de inicio, en HTML plano y **sin estilos**: ponerle estilos (CSS) es opcional y queda a su gusto.
- Todas las **interfaces** y las pruebas. **No se modifican.**

### Las cinco tareas

Cada tarea tiene un **ID** (`NEU-1` a `NEU-5`). Ese ID es el que va al principio del título de su Pull Request (por ejemplo, `NEU-1: estructura de datos de Juan Pérez`).

**Son libres de repartirse el trabajo como prefieran:** una tarea por integrante, una persona con dos tareas, o como mejor se acomoden según el tamaño del equipo. Lo único que se pide es que cada Pull Request lleve el ID de su tarea.

> ⚠️ **Todos los integrantes del equipo deben abrir al menos un Pull Request.** Quien no participe tendrá una nota de **0.0**, y a **los demás integrantes del equipo se les restarán 0.5** puntos.

| ID | Tarea | Qué hace | Archivos que modifica | Pruebas |
|---|---|---|---|---|
| **NEU-1** | **ESTRUCTURA** | Implementa la **lista doblemente enlazada** (con nodos propios que guardan el `siguiente` y el `anterior`, sin `ArrayList` ni `LinkedList`) | `estructura/ListaDoble.java` | `./scripts/test.sh estructura` |
| **NEU-2** | **MODELO** | Reglas de negocio: validar un objeto, agregar sin repetir códigos, buscar, eliminar, ranking, promedios y el cursor | `modelo/Objeto.java` (solo `validar()`) y `modelo/GestorObjetos.java` | `./scripts/test.sh modelo` |
| **NEU-3** | **PERSISTENCIA** | Guarda y lee los objetos en un archivo `.txt` | `persistencia/RepositorioTxt.java` | `./scripts/test.sh persistencia` |
| **NEU-4** | **SERVLETS** | Los 4 servlets: reciben la petición, usan el servicio y reenvían a la página | `web/ListarObjetos.java`, `RegistrarObjeto.java`, `EliminarObjeto.java`, `Visor.java` | `./scripts/test.sh web` |
| **NEU-5** | **UI** | Las 3 páginas JSP (y, si quieren, los estilos CSS). **No tiene pruebas**: se revisa en el navegador | en `src/main/webapp/WEB-INF/vistas/`: `listar.jsp`, `formulario.jsp`, `visor.jsp` | (ninguna) |

Cada clase que debe completar tiene en su comentario (Javadoc) **el contrato exacto** de lo que debe hacer: léalo con calma, las pruebas verifican justamente eso. Los métodos pendientes dicen `TODO`.

**Qué debe tener cada página (NEU-5):**

- `listar.jsp`: recorre el atributo `objetos`, muestra el atributo `promedios` y tiene, por cada objeto, un formulario `POST` que envía a `eliminar`.
- `formulario.jsp`: un formulario `POST` que envía a `registrar`, con los campos `codigo`, `titulo`, `categoria`, `caos`, `tamano`, `descripcion` y `archivo`, y que muestra el atributo `error` si existe.
- `visor.jsp`: muestra el objeto actual con el visor de imágenes (etiqueta `<img>`, con el archivo de `media/`) y los enlaces **Anterior** (`visor?accion=anterior`) y **Siguiente** (`visor?accion=siguiente`).

**Cada integrante modifica SOLO los archivos de su tarea.** Así cada Pull Request toca archivos distintos. No modifique las interfaces (`Lista`, `Repositorio`, `ServicioObjetos`), las pruebas, la carpeta `.github` ni las clases ya hechas.

**Las tareas con pruebas son independientes:** las pruebas de cada una usan objetos "de mentira" para las demás, así que puede terminar y probar la suya sin esperar a sus compañeros. La UI (NEU-5) puede avanzar a la par: solo necesita los servlets para verse funcionando en el navegador.

## Ejecutar el proyecto

Desde una terminal **dentro de la carpeta del proyecto**:

| Acción | Comando |
|---|---|
| Iniciar la aplicación web | `./scripts/start.sh` y abrir <http://localhost:8080/> (si el puerto está ocupado, el script usa el siguiente libre y lo muestra) |
| Cerrar una ejecución que quedó abierta | `./scripts/stop.sh` (o `./scripts/stop.sh 8081` para otro puerto) |
| Todas las pruebas | `./scripts/test.sh` |
| Solo las de una tarea | `./scripts/test.sh estructura` (o `modelo`, `persistencia`, `web`) |

La primera vez tarda unos minutos porque descarga lo necesario. Para detener la aplicación pulse `Ctrl + C`. Los datos se guardan en el archivo `objetos.txt` de la raíz del proyecto (una línea por objeto con **7 campos** separados por `;`: `codigo;titulo;categoria;caos;tamanoCm;descripcion;archivo`; se crea solo si no existe).

**¿Cuándo funciona la aplicación completa?** El botón **Agregar** del catálogo necesita las tareas ESTRUCTURA, MODELO y PERSISTENCIA ya mergeadas en `main`; las páginas Lista, Registrar y Visor necesitan además SERVLETS y UI. Mientras falte algo, la aplicación arranca igual y le avisa qué falta.

## Cómo trabajar (igual que en el LAB-10)

Haga un *fork* del repositorio del equipo en GitHub. Luego siga estos tres bloques de pasos.

### Una sola vez

1. Clone **su fork** (descarga el proyecto a su computador):

   ```bash
   git clone <URL-DE-SU-FORK>
   ```

2. Abra la carpeta del proyecto con VS Code o con NetBeans.
3. Dentro del proyecto (en bash), agregue el repositorio del equipo como un segundo remoto llamado `canon`. Así podrá traer lo que sus compañeros vayan mergeando:

   ```bash
   git remote add canon <URL-DEL-REPOSITORIO-DEL-EQUIPO>
   ```

### Antes de empezar a trabajar

Repita estos pasos al comenzar y **cada vez que se mergee el Pull Request de un compañero**:

1. Cree su rama y cámbiese a ella (nunca trabaje directamente en `main`). **Una rama por tarea**. El nombre de la rama es el prefijo del equipo, un guion y un nombre corto de la tarea: `NEU-nombre-tarea`. Por ejemplo, para la tarea de estructura:

   ```bash
   git checkout -b NEU-estructura
   ```

   > **Importante:** esta vez el nombre de la rama **ya no es** `rama-nombre-apellido` como en los laboratorios anteriores, porque ahora se trabaja por tareas, no por personas.

2. Descargue lo último del repositorio del equipo (no cambia sus archivos, solo trae la información):

   ```bash
   git fetch canon
   ```

3. Ponga su trabajo encima de lo último de `main` del equipo, para partir siempre de la versión más reciente:

   ```bash
   git rebase canon/main
   ```

### Agregar sus cambios y hacer commits

1. Marque los archivos que va a guardar (solo los de su parte):

   ```bash
   git add <sus archivos>
   ```

2. Guarde esos cambios en un *commit* con un mensaje **descriptivo** (en infinitivo, diciendo qué hizo):

   ```bash
   git commit -m "Implementar insertar y eliminar en la lista doble"
   ```

3. Suba su rama a su fork en GitHub (use el nombre de **su** rama):

   ```bash
   git push origin NEU-estructura
   ```

### Pull Request y revisión

1. En GitHub, abra un **Pull Request** de su rama (en su fork) hacia `main` del repositorio del equipo.
   - **Título:** empieza con el **ID de la tarea** (`NEU-1` a `NEU-5`), por ejemplo `NEU-1: estructura de datos de Juan Pérez`.
   - **Descripción:** su nombre en la línea `Nombre:`, qué tarea hizo y cómo la probó. La captura de pantalla es opcional; es recomendable en la tarea de UI (pegue una imagen de la página en el navegador).
2. **Revise y apruebe** los Pull Requests de sus compañeros: se necesitan **2 aprobaciones** (1 si el equipo es de dos personas). No se puede aprobar el propio Pull Request.

### Las pruebas en GitHub

En cada Pull Request se ejecutan **cuatro grupos de pruebas**: `estructura` (NEU-1), `modelo` (NEU-2), `persistencia` (NEU-3) y `web` (NEU-4). La tarea de UI (NEU-5) no tiene grupo de pruebas. Si el grupo de su tarea está en rojo, **todavía puede hacer merge al Pull Request** (solo se exigen las aprobaciones), pero lo ideal es que esté en verde antes de pedir la revisión. Puede volver a empujar cambios a su rama y se vuelven a ejecutar solas.

**Meta del equipo:** que, con todos los Pull Requests mergeados, los **cuatro grupos estén en verde** en `main`. Esa es la entrega.

## Pistas

- Pruebe su tarea primero con `./scripts/test.sh <área>`: el mensaje de cada prueba que falla dice qué se esperaba.
- ESTRUCTURA: dibuje los nodos en papel, con sus dos flechas. En una lista doble el cursor **se detiene** en los extremos (`avanzar()` y `retroceder()` devuelven `false`), y como cada nodo conoce al anterior, retroceder no necesita recorrer la lista desde el principio.
- PERSISTENCIA: `String.split(";", -1)` conserva los campos vacíos al final de una línea.
- UI: empiece con HTML plano y haga que funcione; los estilos (CSS) son lo último. Puede usar `Html.esc(...)` para mostrar texto de forma segura. Para verla funcionar necesita los servlets (NEU-4) en `main`, o pruebe con ellos en su rama.

## Créditos

- Carro rojo: <https://pngmaker.io/tag/Red-Car>
- Pollo: <https://pngtree.com/so/chicken>
- Árbol: <https://clipartpng.com/?2890,tree-png-clip-art>
