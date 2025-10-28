package proyecto;

public class Empleado extends Usuario {

    public Empleado(String usuario, String contraseña) {
        super(usuario, contraseña);
    }


    @Override
    public boolean esAdmin() {
        return false;
    }
}