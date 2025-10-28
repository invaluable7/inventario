//package proyecto;
//
//import java.util.ArrayList;
//import java.util.List;
//import javax.swing.JOptionPane;
//
//public class SistemaSupermercado {
//
//    private List<Usuario> usuarios = new ArrayList<>();
//    private List<Producto> inventario = new ArrayList<>();
//    private Usuario usuarioActual;
//    private final ArchivoTxt.archivoTxt archivoTxt = new ArchivoTxt.archivoTxt();
//
//    // Constructor
//    public SistemaSupermercado() {
//        inicializarSistema();
//    }
//
//    // Inicia la app
//    public void iniciar() {
//        java.awt.EventQueue.invokeLater(() -> {
//            new inventario_Login(this).setVisible(true);
//        });
//    }
//
//    // Carga usuarios y productos
//    private void inicializarSistema() {
//        usuarios = archivoTxt.listarUsuarios();
//        if (usuarios.isEmpty()) {
//            archivoTxt.agregarUsuario("admin", "admin123", "ADMIN");
//            usuarios = archivoTxt.listarUsuarios();
//            JOptionPane.showMessageDialog(null,
//                    "Administrador por defecto 'admin' con contraseña 'admin123' creado.");
//        }
//        inventario = archivoTxt.listarProductos();
//    }
//
//    // Login desde interfaz
//    public void login(String usuario, String contrasena) {
//        usuarioActual = archivoTxt.buscarUsuario(usuario, contrasena);
//
//        if (usuarioActual != null) {
//            usuarioActual.setSistema(this);
//            JOptionPane.showMessageDialog(null, "Bienvenido " + usuarioActual.getUsuario() + "!");
//
//            if (usuarioActual.esAdmin()) {
//                abrirVentanaAdministrador();
//            } else {
//                abrirVentanaEmpleado();
//            }
//        } else {
//            JOptionPane.showMessageDialog(null,
//                    "Usuario o contraseña incorrectos.",
//                    "Error de inicio de sesión",
//                    JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    // Abre ventana administrador
//    public void abrirVentanaAdministrador() {
//        java.awt.EventQueue.invokeLater(() -> {
//            new inventario_interfaz(this, usuarioActual).setVisible(true);
//        });
//    }
//
//    // Abre ventana empleado
//    public void abrirVentanaEmpleado() {
//        java.awt.EventQueue.invokeLater(() -> {
//            new inventario_interfazEmpl(this, usuarioActual).setVisible(true);
//        });
//    }
//
//    // Abre gestor de productos
//    public void abrirGestorProductos() {
//        java.awt.EventQueue.invokeLater(() -> {
//            new Gestionar_Productos(this).setVisible(true);
//        });
//    }
//
//    // Abre gestor de empleados
//    public void abrirGestorEmpleados() {
//        java.awt.EventQueue.invokeLater(() -> {
//            new Gestor_Empleados(this).setVisible(true);
//        });
//    }
//
//    // Abre gestor de administradores
//    public void abrirGestorAdministradores() {
//        java.awt.EventQueue.invokeLater(() -> {
//            new Gestor_Administradores(this).setVisible(true);
//        });
//    }
//
//    // Inventario y usuarios
//    public List<Producto> getInventario() {
//        return inventario;
//    }
//
//    public void guardarProductos() {
//        archivoTxt.guardarProductos(inventario);
//    }
//
//    public void agregarProducto(Producto p) {
//        inventario.add(p);
//        guardarProductos();
//    }
//
//    public void eliminarProducto(Producto p) {
//        inventario.remove(p);
//        guardarProductos();
//    }
//
//    public Usuario getUsuarioActual() {
//        return usuarioActual;
//    }
//
//    public void cerrarSesion() {
//        usuarioActual = null;
//        iniciar();
//    }
//}
