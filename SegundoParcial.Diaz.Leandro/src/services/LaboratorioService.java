package services;

import exceptions.ValidacionRobotException;
import java.util.List;
import models.Robot;
import utils.GestorJson;

public class LaboratorioService {
    // Atributos
    private List<Robot> inventario;
    private GestorJson gestorJson;

    // Constructor
    public LaboratorioService() {
        this.gestorJson = new GestorJson();
        this.inventario = gestorJson.cargar(); // Cargamos automáticamente al iniciar el servicio
    }

    // Getters
    public List<Robot> getInventario() {
        return inventario;
    }
    
    // Metodos
    public void agregarRobot(Robot nuevo) throws ValidacionRobotException{
        if (nuevo.getNivelEnergia() < 0 || nuevo.getNivelEnergia() > 100){ // Valimos que esté dentro del rango
            throw new ValidacionRobotException("Energía fuera de rango (0-100).");
        }
        if (nuevo.getNroSerie() <= 0){ // Error por si el numero es negativo
            throw new ValidacionRobotException("El numero de serie debe ser positivo");
        }
        if (inventario.contains(nuevo)){ // Error si num de serie ya existe
            throw new ValidacionRobotException("Numero de serie duplicado");
        }
        inventario.add(nuevo);
        guardarCambios(); // Guardado automatico
    }
    
    
    public void eliminarRobot(Robot robot){
        inventario.remove(robot);
        guardarCambios();
    }
    
    public void modificarRobot(int indice, Robot modificado) throws ValidacionRobotException{
        if (modificado.getNivelEnergia() < 0 || modificado.getNivelEnergia() > 100){ // Valimos que este en el rango
            throw new ValidacionRobotException("Energia fuera de rango (0 - 100). ");
        }
        // Reemplazamos la lista
        inventario.set(indice, modificado);
        guardarCambios();
    }
    
    private void guardarCambios() {
        gestorJson.guardar(inventario);
    }
}
