package models;

public class RobotIndustrial extends Robot {
    // Atributo
    private double capacidadCarga;
    
    // Constructor
    public RobotIndustrial(String nombre, int nivelEnergia, int nroSerie, double capacidadCarga) {
        super(nombre, nivelEnergia, nroSerie, "INDUSTRIAL");
        this.capacidadCarga = capacidadCarga;
    }
    
    // Constructor vacio
    public RobotIndustrial() { 
        super(); 
    }
    
    // Getter y Setter
    public double getCapacidadCarga() {
        return this.capacidadCarga;
    }

    public void setCapacidadCarga(double capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }
    
    // Metodo
    @Override
    public String toString() {
        return super.toString() + " [Carga: " + capacidadCarga + "kg]";
    }
}
