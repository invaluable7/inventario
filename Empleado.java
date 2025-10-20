package Inventario.proyecto;

// Clase Empleado
class Empleado extends Usuario {
    public Empleado(String usuario, String contrasena) {
        super(usuario, contrasena);
    }

    @Override
    public boolean esAdmin() {
        return false;
    }
}