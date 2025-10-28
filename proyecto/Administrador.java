package proyecto;

public class Administrador extends Usuario {

    public Administrador(String usuario, String contraseña) {
        super(usuario, contraseña);
    }

    @Override
    public boolean esAdmin() {
        return true;
    }


}