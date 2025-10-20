package Inventario.proyecto;

// Clase Administrador
class Administrador extends Usuario {
    public Administrador(String usuario, String contrasena) {
        super(usuario, contrasena);
    }

    @Override
    public boolean esAdmin() {
        return true;
    }
}