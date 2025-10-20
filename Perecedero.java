package Inventario.proyecto;
// Clase Perecedero
class Perecedero extends Producto {
    private String fechaVencimiento;

    public Perecedero(String nombre, double precio, int stock, String fechaVencimiento) {
        super(nombre, precio, stock);
        setFechaVencimiento(fechaVencimiento);
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        if (fechaVencimiento == null || fechaVencimiento.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de vencimiento no puede estar vacía");
        }
        // Validación simple del formato (podría mejorarse)
        if (!fechaVencimiento.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use dd/mm/aaaa");
        }
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String toString() {
        return super.toString() + " - Vencimiento: " + fechaVencimiento + " (Perecedero)";
    }
}