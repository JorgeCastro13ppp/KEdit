# KEdit

KEdit es un editor de texto multiplataforma desarrollado como proyecto final del ciclo de Desarrollo de Aplicaciones Multiplataforma.

El objetivo principal del proyecto es crear una aplicación moderna, funcional y multiplataforma utilizando Kotlin Multiplatform y Compose Multiplatform. KEdit permite trabajar con documentos de texto en distintas plataformas, manteniendo una interfaz coherente y adaptada a cada entorno.

El proyecto incluye versiones para:

- Desktop JVM
- Android
- Web WASM
- Backend Ktor
- Estructura preparada para iOS

---

## Características principales

### Versión Desktop

La versión de escritorio es la versión principal y más completa del proyecto.

Incluye:

- Creación de documentos.
- Apertura de archivos locales.
- Guardado local.
- Guardar como.
- Gestión de múltiples documentos mediante pestañas.
- Cierre de documentos con aviso de cambios sin guardar.
- Explorador de archivos integrado.
- Selección de carpeta de trabajo.
- Búsqueda interna.
- Reemplazo de texto.
- Reemplazar siguiente.
- Reemplazar todos.
- Sensibilidad a mayúsculas en la búsqueda.
- Scroll automático al resultado encontrado.
- Indicador de línea y columna.
- Contador de líneas y caracteres.
- Tema claro y oscuro.
- Preferencias persistentes.
- Terminal integrada con comandos internos.
- Atajos de teclado.
- Resaltado visual básico.
- Registro e inicio de sesión.
- Guardado remoto de documentos.
- Listado de documentos remotos.
- Apertura de documentos remotos.
- Actualización de documentos remotos.
- Guardado remoto mediante `Ctrl + S` cuando el documento pertenece al backend.

### Versión Android

La versión Android es una adaptación móvil simplificada del editor.

Incluye:

- Creación de documentos.
- Edición de contenido.
- Gestión de pestañas.
- Búsqueda de texto.
- Cambio de tema.
- Barra de estado adaptada.
- Inicio de sesión y registro conectados al backend.

No incluye terminal integrada ni explorador avanzado de archivos, ya que estas funcionalidades están pensadas principalmente para escritorio.

### Versión Web

La versión Web está desarrollada con Compose Multiplatform para Web/WASM.

Incluye:

- Creación de documentos en memoria.
- Edición de texto.
- Gestión de pestañas.
- Búsqueda.
- Reemplazo.
- Sidebar de documentos.
- Cambio de tema.
- Panel de cuenta en modo demostración.
- Acceso visual como versión web del proyecto.

La integración completa con backend en Web queda planteada como mejora futura debido a las limitaciones encontradas con Kotlin/WASM y las peticiones HTTP dentro del tiempo disponible del proyecto.

### Backend

El backend se ha desarrollado con Ktor Server y PostgreSQL.

Permite:

- Registro de usuarios.
- Inicio de sesión.
- Guardado remoto de documentos.
- Listado de documentos de un usuario.
- Apertura de documentos remotos.
- Actualización de documentos existentes.

---

## Tecnologías utilizadas

- Kotlin
- Kotlin Multiplatform
- Compose Multiplatform
- Compose Desktop
- Compose Android
- Compose Web / WASM
- Ktor Server
- Ktor Client
- PostgreSQL
- Exposed
- Gradle
- Docker Compose
- Git
- GitHub

---

## Estructura del proyecto

```text
KEdit/
├── composeApp/
│   └── src/
│       ├── commonMain/
│       ├── androidMain/
│       ├── jvmMain/
│       ├── webMain/
│       └── iosMain/
│
├── shared/
│   └── src/
│       └── commonMain/
│
├── server/
│   └── src/
│       └── main/
│
├── iosApp/
│
├── gradle/
│
├── build.gradle.kts
├── settings.gradle.kts
└── README.md