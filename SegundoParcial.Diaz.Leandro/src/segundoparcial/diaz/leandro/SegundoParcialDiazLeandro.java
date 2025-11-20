package segundoparcial.diaz.leandro;

import exceptions.ValidacionRobotException;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Robot;
import services.LaboratorioService;
import views.FormularioRobotStage;

public class SegundoParcialDiazLeandro extends Application {
    // Atributos
    private LaboratorioService servicio;
    private ListView<Robot> listView;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        servicio = new LaboratorioService(); // Instanciamos el servicio

        BorderPane root = new BorderPane(); // Layout principal
        root.setPadding(new Insets(10));
        
        // Configuramos la lista visual
        listView = new ListView<>();
        listView.getItems().addAll(servicio.getInventario()); // Agregamos la lista de robots
        root.setCenter(listView);
        
        // Botones
        Button btnAgregar = new Button("Agregar");
        Button btnModificar = new Button("Modificar");
        Button btnEliminar = new Button("Eliminar");
        
        HBox botones = new HBox(10, btnAgregar, btnModificar, btnEliminar);
        botones.setAlignment(Pos.CENTER);
        botones.setPadding(new Insets(10));
        root.setBottom(botones);
        
        // Logica de Agregar
        btnAgregar.setOnAction(e -> {
            //Creamos la ventana del formulario con 'null' porque es un robot nuevo
            FormularioRobotStage form = new FormularioRobotStage(primaryStage, null);
            // Mostramos la ventana, pausa la ejecucion y devuelve el robot nuevo (o null) cuando se cierra la ventana
            Robot nuevo = form.showAndWait(); 
            
            // Si no es nulo, lo agregamos al servicio y actualizamos la lista
            if (nuevo != null) {
                try {
                    servicio.agregarRobot(nuevo);
                    refrescar();
                } catch (ValidacionRobotException ex) {
                    mostrarError(ex.getMessage());
                }
            }
        });
        
        // Logica de Modificar
        btnModificar.setOnAction(e -> {
            Robot selec = listView.getSelectionModel().getSelectedItem();
            if (selec == null) { // Si no se selleciona nada, retornamos
                return;
            }
            
            int indice = listView.getSelectionModel().getSelectedIndex();

            FormularioRobotStage form = new FormularioRobotStage(primaryStage, selec);
            Robot editado = form.showAndWait();
            
            // Si no es nulo, se modifica y se actualiza
            if (editado != null) {
                try {
                    servicio.modificarRobot(indice, editado);
                    refrescar();
                } catch (ValidacionRobotException ex) {
                     mostrarError(ex.getMessage());
                }
            }
        });
        
        // Logica de Eliminar
        btnEliminar.setOnAction(e -> {
            // Obtenemos el robot seleccionado de la lista visual
            Robot selec = listView.getSelectionModel().getSelectedItem();
            
            if (selec != null) {
                // Creamos la alerta de tipo confirmacion 
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Eliminación");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro que deseas eliminar al robot: " + selec.getNombre() + "?");
                
                // Mostramos y esperamos a que se toque un botón
                Optional<ButtonType> resultado = alert.showAndWait();
                
                // Verificamos si la caja tiene algo, y si lo tiene; si es el boton ok
                if (resultado.isPresent() && resultado.get() == javafx.scene.control.ButtonType.OK) {
                    servicio.eliminarRobot(selec);
                    refrescar();
                }
            } else {
                mostrarError("Selecciona un robot para eliminar.");
            }
        });
        
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Gestion de Robots (Gson)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void refrescar(){
        listView.getItems().clear();
        listView.getItems().addAll(servicio.getInventario());
    }
    
    private void mostrarError(String msj){
        new Alert(Alert.AlertType.ERROR, msj).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
