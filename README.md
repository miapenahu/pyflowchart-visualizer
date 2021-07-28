## Instrucciones de Compilación

GitHub: https://github.com/miapenahu/pyflowchart-visualizer

Para ejecutar el código fuente después de hacer un clone desde el enlace, se debe
tener en cuenta los siguientes aspectos:

 - El proyecto fue desarrollado en IntelliJ IDEA (por eso los archivos .iml y
  .idea)
 - El proyecto fue desarrollado en el Framework JavaFX y usando el JDK 1.8.0 v277
 - El proyecto tiene una dependencia a ANTLR 4, el cual se puede descargar de la
   siguiente página: https://www.antlr.org/download.html
   (Versión usada para el desarrollo: antlr-4.9.2-complete.jar)
   
Luego, se debe ejecutar la clase Main en el package .app. Si el importe de la dependencia
ANTLR fue realizada exitosamente y la versión de Java es la correcta, debería poder 
ejecutarse sin problema.

## Manual de Uso

El funcionamiento del programa está pensado para ser muy directo y sencillo de usar. La
ventana principal consiste en un menú superior con tres botones, y un editor con 5 botones.
La funcionalidad de cada botón será explicada a continuación:

### Menú superior del Editor
 - *Botón Editor:* Es el primer botón de izquierda a derecha. Tiene como función mostrar/ocultar
 la ventana del editor.
 - *Botón Simulador:* Es el botón del medio, y al presionarse abrirá una nueva ventana con un
    editor simple de diagramas de flujo (en un futuro se piensa escribir código fuente a partir de
    esta ventana)
 - *Botón salir:* Es el botón más a la derecha, y tiene como función salir del programa.

### Editor
Es importante tener en cuenta que una vez se empieza a escribir en el área de edición, éste ejecuta
un análisis léxico y sintáctico, y de hallar un error, el texto del editor se volverá color rojo.
Si todo el análisis fue bien, el color del texto será verde.

 - *Botón Abrir:* Se usa para abrir un script python que se encuentre en alguna ubicación en el equipo.
 - *Botón Guardar:* Guarda el código que haya sido procesado en el área de edición.
 - *Botón Graficar:* Abre una o varias ventanas con la representación correspondiente a el o los
    diagramas de flujo presentes en el código que se encuentre en el editor
 - *Botón Cerrar Diagramas:* Está justo al lado derecho del botón Graficar, y se usa para cerrar todos
    los diagramas que hayan sido abiertos por ese botón.
 - *Botón de Depuración:* En caso de haber un error, la causa del mismo se podrá ver en una ventana
    emergente que saldrá si se presiona este botón.

### Simulador
De arriba hacia abajo, y de derecha a izquierda, el simulador consta de los siguientes botones:
- *Print:* Simula el evento de un elemento IO (Entrada o Salida) y lo imprime en pantalla.
- *Campo de texto para print:* En él se puede escribir el texto que iría dentro del elemento IO.
- *Crear Variable:* Simula el evento de un Proceso y lo imprime en pantalla.
- *Campo de texto para crear variable:* En él se puede escribir el texto que iría dentro del
  elemento Proceso.
- *If:* Simula el evento de una estructura condicional y lo imprime en pantalla.
- *Campo de texto para if:* En él se puede escribir el texto que iría dentro del elemento If.
- *Merge:* Indica el cierre del último elemento condicional abierto.
- *Indicador branch:* Indica en todo momento la rama condicional (true, false, none) en la que se está.
- *Indicador open para if:* Indica el número de ifs abiertos hasta ese punto.
- *While:* Simula el evento de una estructura iterativa y lo imprime en pantalla.
- *Campo de texto para While:* En él se puede escribir el texto que iría dentro del elemento While.
- *End block/End loop:* Indica el final del bloque que se ejecuta indefinidamente mientras se 
    cumpla la condición del ciclo. Al presionarse cambia de nombre a "End loop", y empieza a 
    escribir sobre la rama negativa del ciclo (similar a un else luego de la ejecución del ciclo)
- *Indicador block:* Indica en todo momento el bloque (true, false, none) que se está escribiendo.
- *Indicador open para while:* Indica el número de while abiertos hasta ese punto.
- *Final:* Simula el evento de llegar al final del diagrama y lo imprime en pantalla.
