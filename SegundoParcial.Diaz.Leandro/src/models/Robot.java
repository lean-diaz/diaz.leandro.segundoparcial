package models;

public abstract class Robot {
    // Atributos
    protected String tipo; // Para GSON
    private String nombre;
    private int nivelEnergia;
    private int nroSerie;

    // Constructor
    public Robot(String nombre, int nivelEnergia, int nroSerie, String tipo) {
        this.nombre = nombre;
        this.nivelEnergia = nivelEnergia;
        this.nroSerie = nroSerie;
        this.tipo = tipo;
    }

    // Constructor para GSON
    public Robot() {
    }
    
    // Getters
    public String getTipo() {
        return this.tipo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public int getNivelEnergia() {
        return this.nivelEnergia;
    }

    public int getNroSerie() {
        return this.nroSerie;
    }
    
    // Setters
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNivelEnergia(int nivelEnergia) {
        this.nivelEnergia = nivelEnergia;
    }

    public void setNroSerie(int nroSerie) {
        this.nroSerie = nroSerie;
    }
    
    // Metodos
    @Override
    public String toString(){
        return this.nombre + " (" + this.tipo + ") - Energia: " + this.nivelEnergia + "%";  
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Robot robot = (Robot) obj;
        return nroSerie == robot.nroSerie;
    }
}
