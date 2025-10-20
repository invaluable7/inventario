package Inventario.proyecto;

import java.util.ArrayList;
import java.util.Scanner;

// Clase principal que contiene el metodo main
public class Main {
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static ArrayList<Producto> inventario = new ArrayList<>();
    private static Usuario usuarioActual = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarSistema();
        mostrarMenuPrincipal();
    }

    // Inicializa el sistema con un admin por defecto
    private static void inicializarSistema() {
        usuarios.add(new Administrador("admin", "admin123"));
    }

    // Menú principal del sistema
    private static void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n=== SISTEMA DE SUPERMERCADO ===");
            System.out.println("1. Iniciar sesión");
            System.out.println("2. Salir");
            System.out.print("Selección: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        iniciarSesion();
                        break;
                    case 2:
                        System.out.println("Saliendo del sistema...");
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }

    // Metodo para iniciar sesión
    private static void iniciarSesion() {
        System.out.println("\n=== INICIO DE SESIÓN ===");

        while (true) {
            try {
                System.out.print("Usuario: ");
                String usuario = scanner.nextLine();

                System.out.print("Contraseña: ");
                String contrasena = scanner.nextLine();

                usuarioActual = validarCredenciales(usuario, contrasena);

                if (usuarioActual != null) {
                    System.out.println("\nBienvenido, " + usuarioActual.getUsuario() + "!");
                    if (usuarioActual instanceof Administrador) {
                        mostrarMenuAdministrador();
                    } else {
                        mostrarMenuEmpleado();
                    }
                    return;
                } else {
                    System.out.println("Credenciales incorrectas. Intente nuevamente o presione 0 para volver.");
                    String opcion = scanner.nextLine();
                    if (opcion.equals("0")) return;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Valida las credenciales del usuario
    private static Usuario validarCredenciales(String usuario, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario) && u.getContrasena().equals(contrasena)) {
                return u;
            }
        }
        return null;
    }

    // Menu para administradores
    private static void mostrarMenuAdministrador() {
        while (true) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Gestionar productos");
            System.out.println("2. Gestionar empleados");
            System.out.println("3. Cerrar sesión");
            System.out.print("Selección: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        gestionarProductos();
                        break;
                    case 2:
                        gestionarEmpleados();
                        break;
                    case 3:
                        usuarioActual = null;
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }

    // Menú para empleados
    private static void mostrarMenuEmpleado() {
        while (true) {
            System.out.println("\n=== MENÚ EMPLEADO ===");
            System.out.println("1. Ver inventario");
            System.out.println("2. Buscar producto");
            System.out.println("3. Cerrar sesión");
            System.out.print("Selección: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        verInventario();
                        break;
                    case 2:
                        buscarProducto();
                        break;
                    case 3:
                        usuarioActual = null;
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }

    // Métodos para gestión de productos
    private static void gestionarProductos() {
        while (true) {
            System.out.println("\n=== GESTIÓN DE PRODUCTOS ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Editar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Ver inventario");
            System.out.println("5. Volver");
            System.out.print("Selección: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        agregarProducto();
                        break;
                    case 2:
                        editarProducto();
                        break;
                    case 3:
                        eliminarProducto();
                        break;
                    case 4:
                        verInventario();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }

    private static void agregarProducto() {
        System.out.println("\n=== AGREGAR PRODUCTO ===");

        while (true) {
            try {
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();

                System.out.print("Precio: ");
                double precio = Double.parseDouble(scanner.nextLine());

                System.out.print("Stock: ");
                int stock = Integer.parseInt(scanner.nextLine());

                System.out.print("Tipo (1. Perecedero, 2. No perecedero): ");
                int tipo = Integer.parseInt(scanner.nextLine());

                Producto producto;
                if (tipo == 1) {
                    System.out.print("Fecha vencimiento (dd/mm/aaaa): ");
                    String fechaVencimiento = scanner.nextLine();
                    producto = new Perecedero(nombre, precio, stock, fechaVencimiento);
                } else {
                    producto = new Producto(nombre, precio, stock);
                }

                inventario.add(producto);
                System.out.println("Producto agregado exitosamente!");
                return;
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese valores numéricos válidos para precio y stock.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("¿Desea intentar nuevamente? (s/n): ");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                return;
            }
        }
    }

    private static void editarProducto() {
        System.out.println("\n=== EDITAR PRODUCTO ===");
        verInventario();

        if (inventario.isEmpty()) return;

        while (true) {
            try {
                System.out.print("Ingrese el número del producto a editar (0 para cancelar): ");
                int indice = Integer.parseInt(scanner.nextLine()) - 1;

                if (indice == -1) return;
                if (indice < 0 || indice >= inventario.size()) {
                    System.out.println("Número de producto inválido.");
                    continue;
                }

                Producto producto = inventario.get(indice);

                System.out.print("Nuevo nombre (" + producto.getNombre() + "): ");
                String nombre = scanner.nextLine();
                if (!nombre.isEmpty()) producto.setNombre(nombre);

                System.out.print("Nuevo precio (" + producto.getPrecio() + "): ");
                String precioStr = scanner.nextLine();
                if (!precioStr.isEmpty()) producto.setPrecio(Double.parseDouble(precioStr));

                System.out.print("Nuevo stock (" + producto.getStock() + "): ");
                String stockStr = scanner.nextLine();
                if (!stockStr.isEmpty()) producto.setStock(Integer.parseInt(stockStr));

                if (producto instanceof Perecedero) {
                    Perecedero perecedero = (Perecedero) producto;
                    System.out.print("Nueva fecha vencimiento (" + perecedero.getFechaVencimiento() + "): ");
                    String fecha = scanner.nextLine();
                    if (!fecha.isEmpty()) perecedero.setFechaVencimiento(fecha);
                }

                System.out.println("Producto actualizado exitosamente!");
                return;
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese valores numéricos válidos.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("¿Desea intentar nuevamente? (s/n): ");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                return;
            }
        }
    }

    private static void eliminarProducto() {
        System.out.println("\n=== ELIMINAR PRODUCTO ===");
        verInventario();

        if (inventario.isEmpty()) return;

        while (true) {
            try {
                System.out.print("Ingrese el número del producto a eliminar (0 para cancelar): ");
                int indice = Integer.parseInt(scanner.nextLine()) - 1;

                if (indice == -1) return;
                if (indice < 0 || indice >= inventario.size()) {
                    System.out.println("Número de producto inválido.");
                    continue;
                }

                Producto producto = inventario.remove(indice);
                System.out.println("Producto '" + producto.getNombre() + "' eliminado.");
                return;
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }

            System.out.print("¿Desea intentar nuevamente? (s/n): ");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                return;
            }
        }
    }

    private static void verInventario() {
        System.out.println("\n=== INVENTARIO ===");

        if (inventario.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
            return;
        }

        for (int i = 0; i < inventario.size(); i++) {
            System.out.println((i + 1) + ". " + inventario.get(i));
        }
    }

    private static void buscarProducto() {
        System.out.println("\n=== BUSCAR PRODUCTO ===");
        System.out.print("Ingrese nombre o parte del nombre: ");
        String busqueda = scanner.nextLine().toLowerCase();

        boolean encontrado = false;
        for (Producto p : inventario) {
            if (p.getNombre().toLowerCase().contains(busqueda)) {
                System.out.println(p);
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se encontraron productos.");
        }
    }

    // Metodos para gestion de empleados
    private static void gestionarEmpleados() {
        while (true) {
            System.out.println("\n=== GESTIÓN DE EMPLEADOS ===");
            System.out.println("1. Agregar empleado");
            System.out.println("2. Listar empleados");
            System.out.println("3. Eliminar empleado");
            System.out.println("4. Volver");
            System.out.print("Selección: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        agregarEmpleado();
                        break;
                    case 2:
                        listarEmpleados();
                        break;
                    case 3:
                        eliminarEmpleado();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número.");
            }
        }
    }

    private static void agregarEmpleado() {
        System.out.println("\n=== AGREGAR EMPLEADO ===");

        while (true) {
            try {
                System.out.print("Nombre de usuario: ");
                String usuario = scanner.nextLine();

                if (usuarioExiste(usuario)) {
                    System.out.println("El usuario ya existe.");
                    continue;
                }

                System.out.print("Contraseña: ");
                String contrasena = scanner.nextLine();

                usuarios.add(new Empleado(usuario, contrasena));
                System.out.println("Empleado agregado exitosamente!");
                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("¿Desea intentar nuevamente? (s/n): ");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                return;
            }
        }
    }

    private static boolean usuarioExiste(String usuario) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuario)) {
                return true;
            }
        }
        return false;
    }

    private static void listarEmpleados() {
        System.out.println("\n=== LISTA DE EMPLEADOS ===");

        if (usuarios.size() <= 1) { // Solo admin
            System.out.println("No hay empleados registrados.");
            return;
        }

        for (int i = 1; i < usuarios.size(); i++) { // Empezar desde 1 para omitir admin
            System.out.println(i + ". " + usuarios.get(i).getUsuario());
        }
    }

    private static void eliminarEmpleado() {
        listarEmpleados();

        if (usuarios.size() <= 1) return; // Solo admin

        while (true) {
            try {
                System.out.print("Ingrese el número del empleado a eliminar (0 para cancelar): ");
                int indice = Integer.parseInt(scanner.nextLine());

                if (indice == 0) return;
                if (indice < 1 || indice >= usuarios.size()) {
                    System.out.println("Número de empleado inválido.");
                    continue;
                }

                Usuario empleado = usuarios.remove(indice);
                System.out.println("Empleado '" + empleado.getUsuario() + "' eliminado.");
                return;
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }

            System.out.print("¿Desea intentar nuevamente? (s/n): ");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("s")) {
                return;
            }
        }
    }
}