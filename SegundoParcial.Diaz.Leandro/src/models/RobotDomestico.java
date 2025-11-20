package models;

public class RobotDomestico extends Robot {
    // Atributo
    private int cantidadTareas;
    
    // Constructor
    public RobotDomestico(String nombre, int nivelEnergia, int nroSerie, int cantidadTareas) {
        super(nombre, nivelEnergia, nroSerie, "DOMESTICO");
        this.cantidadTareas = cantidadTareas;
    }
    
    // Constructor vacío
    public RobotDomestico() { 
        super();
    }
    
    // Getter y Setter
    public int getCantidadTareas() {
        return this.cantidadTareas;
    }
    
    public void setCantidadTareas(int cantidadTareas) {
        this.cantidadTareas = cantidadTareas;
    }

    // Metodo
    @Override
    public String toString() {
        return super.toString() + " [Tareas: " + cantidadTareas + "]";
    }

}
