17. Manual de Usuario Breve
17.1. Inicio de la Aplicación
Al iniciar la aplicación, MainFrame carga automáticamente los registros guardados en los archivos .txt de la carpeta src/data/.
El dashboard inicial muestra las métricas de clientes, productos y pedidos vigentes.
17.2. Registro y Gestión de Clientes
Hacer clic en Clientes en el menú lateral.
Ingresar ID, Cédula, Nombre, Apellido y demás datos en el formulario lateral.
Pulsar Registrar. El cliente aparecerá de inmediato en la tabla.
Para modificar: pulsar sobre el cliente en la tabla, corregir el dato deseado en el formulario y pulsar Actualizar.
Para ordenar alfabéticamente: pulsar Ordenar A-Z.
17.3. Administración de Productos
Hacer clic en Productos.
Ingresar Código, Nombre, Precio, Stock y Stock Mínimo.
Pulsar Registrar. El producto queda indexado en la Tabla Hash y en el árbol de categorías.
Para ordenar por precio de menor a mayor: pulsar Ordenar Precio.
17.4. Creación y Despacho de Pedidos
Hacer clic en Pedidos.
Ingresar un ID para el pedido y seleccionar el Cliente en el combo desplegable.
Seleccionar el Producto, indicar la cantidad y pulsar + Añadir Producto. El producto se sumará al carrito temporal descontando preventivamente del stock.
Pulsar Guardar Pedido. La orden entra a la lista general con estado "Pendiente" y se encola en la cola FIFO.
Para despachar pedidos pendientes en estricto orden de llegada: pulsar el botón superior verde "Despachar Siguiente (Cola FIFO)".
17.5. Emisión de Facturas
Seleccionar Facturas en el menú lateral.
Seleccionar el pedido que se desea liquidar y verificar la tasa de IVA (por defecto 19%).
Pulsar Emitir Factura. La factura se listará en la tabla con sus totales calculados.
Pulsar Ver Factura Completa para abrir la representación del comprobante de venta.
17.6. Planificación de Rutas Logísticas
Pulsar Logística y Rutas en el menú lateral.
Seleccionar la Ciudad Origen (ej. Bogotá) y la Ciudad Destino (ej. Cali).
Pulsar Calcular Ruta. El sistema ejecutará el algoritmo BFS y mostrará el itinerario con menor número de tramos intermedios.
17.7. Guardado y Cierre Seguro
En cualquier momento, el usuario puede pulsar el botón verde lateral "Guardar datos" para forzar la persistencia en los .txt.
Al pulsar Salir o cerrar la ventana con la X, SmartStore consultará si desea guardar los cambios antes de salir.
18. Ejecución del Proyecto
Requisitos Previos
Java JDK 17 o superior configurado en las variables de entorno (JAVA_HOME).
Apache Maven 3.9 o superior (mvn -version).
Git para clonar el repositorio.
Comandos de Construcción y Ejecución
Clonar el repositorio:
   git clone https://github.com/coyote385/SmartStore.git
    cd SmartStore

 Compilar el proyecto:
   mvn clean compile

Ejecutar la suite completa de 317 pruebas unitarias con JaCoCo:
   mvn test

 Empaquetar el archivo ejecutable `.jar`:
   mvn package -DskipTests

 Ejecutar la aplicación gráfica:
   mvn exec:java -Dexec.mainClass="com.smartstore.Main"

