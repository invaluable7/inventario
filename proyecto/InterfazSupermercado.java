package proyecto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class InterfazSupermercado extends JFrame {

    private ArchivoTxt.archivoTxt archivo;
    private Usuario usuarioActual;
    private JPanel panelPrincipal;
    private CardLayout cardLayout;

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public InterfazSupermercado() {
        archivo = new ArchivoTxt.archivoTxt();
        setTitle("Sistema de Supermercado");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);

        panelPrincipal.add(crearPanelLogin(), "login");
        panelPrincipal.add(crearPanelMenuAdmin(), "menuAdmin");
        panelPrincipal.add(crearPanelMenuEmpleado(), "menuEmpleado");
        panelPrincipal.add(crearPanelInventario(), "inventario");

        add(panelPrincipal);
        setVisible(true);
    }

    // ============================================================
    // LOGIN
    // ============================================================
    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitulo = new JLabel("Inicio de Sesión");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField(15);

        JLabel lblContraseña = new JLabel("Contraseña:");
        JPasswordField txtContraseña = new JPasswordField(15);

        JButton btnLogin = crearBotonVerde("Ingresar");
        JLabel lblError = new JLabel("", SwingConstants.CENTER);
        lblError.setForeground(Color.RED);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        panel.add(lblUsuario, gbc);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(lblContraseña, gbc);
        gbc.gridx = 1;
        panel.add(txtContraseña, gbc);

        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        panel.add(btnLogin, gbc);

        gbc.gridy++;
        panel.add(lblError, gbc);

        btnLogin.addActionListener(e -> {
            String user = txtUsuario.getText().trim();
            String pass = new String(txtContraseña.getPassword());

            Usuario encontrado = archivo.buscarUsuario(user, pass);
            if (encontrado != null) {
                usuarioActual = encontrado;
                

                if (usuarioActual.esAdmin()) {
                    cardLayout.show(panelPrincipal, "menuAdmin");
                } else {
                    cardLayout.show(panelPrincipal, "menuEmpleado");
                }
            } else {
                lblError.setText("Usuario o contraseña incorrectos");
            }
        });

        return panel;
    }

    // ============================================================
    // MENÚ ADMINISTRADOR
    // ============================================================
    private JPanel crearPanelMenuAdmin() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

        JLabel lblTitulo = new JLabel("Menú Administrador", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        JButton btnInventario = crearBotonVerde("Gestionar Inventario");
        JButton btnUsuarios = crearBotonVerde("Gestionar Usuarios");
        JButton btnCerrarSesion = crearBotonVerde("Cerrar Sesión");

        panel.add(lblTitulo);
        panel.add(btnInventario);
        panel.add(btnUsuarios);
        panel.add(btnCerrarSesion);

        btnInventario.addActionListener(e -> cardLayout.show(panelPrincipal, "inventario"));
        btnUsuarios.addActionListener(e -> mostrarVentanaUsuarios());
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        return panel;
    }

    // ============================================================
    // MENÚ EMPLEADO
    // ============================================================
    private JPanel crearPanelMenuEmpleado() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

        JLabel lblTitulo = new JLabel("Menú Empleado", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));

        JButton btnInventario = crearBotonVerde("Ver Inventario");
        JButton btnCerrarSesion = crearBotonVerde("Cerrar Sesión");

        panel.add(lblTitulo);
        panel.add(btnInventario);
        panel.add(btnCerrarSesion);

        btnInventario.addActionListener(e -> cardLayout.show(panelPrincipal, "inventario"));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        return panel;
    }

    // ============================================================
    // PANEL INVENTARIO
    // ============================================================
    private JPanel crearPanelInventario() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        modeloTabla = new DefaultTableModel(new Object[]{"Tipo", "Nombre", "Precio", "Stock", "Vencimiento"}, 0);
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaProductos);
        actualizarTabla();

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.WHITE);

        JButton btnAgregar = crearBotonVerde("Agregar");
        JButton btnEditar = crearBotonVerde("Editar");
        JButton btnEliminar = crearBotonVerde("Eliminar");
        JButton btnVolver = crearBotonVerde("Volver");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        btnAgregar.addActionListener(e -> mostrarVentanaAgregar());
        btnEditar.addActionListener(e -> editarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnVolver.addActionListener(e -> {
            if (usuarioActual.esAdmin()) {
                cardLayout.show(panelPrincipal, "menuAdmin");
            } else {
                cardLayout.show(panelPrincipal, "menuEmpleado");
            }
        });

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    // ============================================================
    // MÉTODOS AUXILIARES
    // ============================================================
    private JButton crearBotonVerde(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(new Color(0, 153, 76));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
    }

    private void cerrarSesion() {
        usuarioActual = null;
        cardLayout.show(panelPrincipal, "login");
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<Producto> productos = archivo.listarProductos();

        for (Producto p : productos) {
            if (p instanceof Perecedero) {
                Perecedero per = (Perecedero) p;
                modeloTabla.addRow(new Object[]{
                        "PERECEDERO",
                        p.getNombre(),
                        p.getPrecio(),
                        p.getStock(),
                        per.estaVencido() ? "VENCIDO" : per.toString().split("Vence: ")[1]
                });
            } else {
                modeloTabla.addRow(new Object[]{
                        "NO_PERECEDERO",
                        p.getNombre(),
                        p.getPrecio(),
                        p.getStock(),
                        "-"
                });
            }
        }
    }

    private void mostrarVentanaAgregar() {
        JTextField txtNombre = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtStock = new JTextField();
        JTextField txtFecha = new JTextField();

        Object[] message = {
                "Nombre:", txtNombre,
                "Precio:", txtPrecio,
                "Stock:", txtStock,
                "Fecha de vencimiento (dd/MM/yyyy, opcional):", txtFecha
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Agregar Producto", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String nombre = txtNombre.getText().trim();
                double precio = Double.parseDouble(txtPrecio.getText());
                int stock = Integer.parseInt(txtStock.getText());

                Producto nuevo;
                if (!txtFecha.getText().trim().isEmpty()) {
                    LocalDate fecha = LocalDate.parse(txtFecha.getText(), FORMATO_FECHA);
                    nuevo = new Perecedero(nombre, precio, stock, fecha);
                } else {
                    nuevo = new Producto(nombre, precio, stock);
                }

                List<Producto> productos = archivo.listarProductos();
                productos.add(nuevo);
                archivo.guardarProductos(productos);
                actualizarTabla();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void editarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para editar");
            return;
        }

        String nombre = (String) modeloTabla.getValueAt(fila, 1);
        List<Producto> productos = archivo.listarProductos();
        Producto producto = productos.stream().filter(p -> p.getNombre().equals(nombre)).findFirst().orElse(null);

        if (producto == null) return;

        JTextField txtNombre = new JTextField(producto.getNombre());
        JTextField txtPrecio = new JTextField(producto.getPrecio().toString());
        JTextField txtStock = new JTextField(producto.getStock().toString());
        JTextField txtFecha = new JTextField((producto instanceof Perecedero) ?
                ((Perecedero) producto).toString().split("Vence: ")[1].replace(" (VENCIDO)", "") : "");

        Object[] message = {
                "Nombre:", txtNombre,
                "Precio:", txtPrecio,
                "Stock:", txtStock,
                "Fecha de vencimiento (dd/MM/yyyy, opcional):", txtFecha
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Editar Producto", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                producto.setNombre(txtNombre.getText());
                producto = new Producto(producto.getNombre(), Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtStock.getText()));

                if (!txtFecha.getText().trim().isEmpty()) {
                    LocalDate fecha = LocalDate.parse(txtFecha.getText(), FORMATO_FECHA);
                    producto = new Perecedero(txtNombre.getText(), Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtStock.getText()), fecha);
                }

                productos.set(fila, producto);
                archivo.guardarProductos(productos);
                actualizarTabla();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al editar: " + e.getMessage());
            }
        }
    }

    private void eliminarProducto() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar");
            return;
        }

        String nombre = (String) modeloTabla.getValueAt(fila, 1);
        List<Producto> productos = archivo.listarProductos();
        productos.removeIf(p -> p.getNombre().equals(nombre));
        archivo.guardarProductos(productos);
        actualizarTabla();
    }

    private void mostrarVentanaUsuarios() {
        List<Usuario> usuarios = archivo.listarUsuarios();
        StringBuilder sb = new StringBuilder("Usuarios registrados:\n\n");
        for (Usuario u : usuarios) {
            sb.append("- ").append(u.getUsuario()).append(" (")
                    .append(u.esAdmin() ? "ADMIN" : "EMPLEADO").append(")\n");
        }

        JTextField txtUsuario = new JTextField();
        JTextField txtContraseña = new JTextField();
        JComboBox<String> comboRol = new JComboBox<>(new String[]{"ADMIN", "EMPLEADO"});

        Object[] message = {
                sb.toString(),
                "Nuevo usuario:", txtUsuario,
                "Contraseña:", txtContraseña,
                "Rol:", comboRol
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Gestión de Usuarios", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            archivo.agregarUsuario(txtUsuario.getText(), txtContraseña.getText(), comboRol.getSelectedItem().toString());
        }
    }
}
