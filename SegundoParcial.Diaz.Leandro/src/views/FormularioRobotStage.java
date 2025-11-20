package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Robot;
import models.RobotDomestico;
import models.RobotIndustrial;

public class FormularioRobotStage {
    // Atributos
    private Stage stage;
    private Robot robotResultado = null;
    private Robot robotExistente;
    
    // Controles
    private TextField txtNombre;
    private TextField txtEnergia;
    private TextField txtSerie;
    private TextField txtDatoExtra;
    private ComboBox<String> cbTipo;
    private Label lblDatoExtra;

    public FormularioRobotStage(Stage owner, Robot robotExistente) {
        this.robotExistente = robotExistente;
        
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(owner);
        String titulo;

        if (robotExistente == null) {
            titulo = "Nuevo Robot";
        } else {
            titulo = "Editar Robot";
        }
        
        stage.setTitle(titulo);
        
        VBox root = construirUI();
        stage.setScene(new Scene(root, 350, 400));
        if (robotExistente != null){
            rellenarDatos();
        }
    }
    
    private VBox construirUI() {
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        
        // Incializamos controles
        txtNombre = new  TextField();
        txtEnergia = new TextField();
        txtSerie = new TextField();
        txtDatoExtra = new TextField();
        
        cbTipo = new ComboBox<>();
        cbTipo.getItems().addAll("DOMESTICO", "INDUSTRIAL");
        cbTipo.setValue("DOMESTICO");
        
        lblDatoExtra = new Label("Cant. Tareas:");
        
        // Evento del Combo
        cbTipo.setOnAction(e -> {
            if (cbTipo.getValue().equals("DOMESTICO")) {
                lblDatoExtra.setText("Cant. Tareas:");
            } else {
                lblDatoExtra.setText("Carga (kg):");
            }
        });
        
        // Armar Grid
        grid.add(new Label("Tipo:"), 0, 0);
        grid.add(cbTipo, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(txtNombre, 1, 1);
        grid.add(new Label("Serie:"), 0, 2);
        grid.add(txtSerie, 1, 2);
        grid.add(new Label("Energía:"), 0, 3);
        grid.add(txtEnergia, 1, 3);
        grid.add(lblDatoExtra, 0, 4);
        grid.add(txtDatoExtra, 1, 4);
        
        // Botones
        Button btnGuardar = new Button("Guardar");
        Button btnCancelar = new Button("Cancelar");
        
        btnGuardar.setOnAction(e -> guardar());
        btnCancelar.setOnAction(e -> stage.close());
        
        HBox botones = new HBox(10, btnGuardar, btnCancelar);
        botones.setAlignment(Pos.CENTER);
        
        VBox root = new VBox(20, grid, botones);
        root.setPadding(new Insets(20));
        return root;
    }
    
    private void rellenarDatos() {
        txtNombre.setText(robotExistente.getNombre());
        txtEnergia.setText(String.valueOf(robotExistente.getNivelEnergia()));
        txtSerie.setText(String.valueOf(robotExistente.getNroSerie()));
        txtSerie.setDisable(true); // Serie no se edita
        cbTipo.setDisable(true);   // Tipo no se edita

        // Detecta la subclase y ponemos el dato extra correspondiente
        if (robotExistente instanceof RobotDomestico robotDomestico) {
            cbTipo.setValue("DOMESTICO");
            txtDatoExtra.setText(String.valueOf(robotDomestico.getCantidadTareas()));
            
        } else if (robotExistente instanceof RobotIndustrial robotIndustrial) {
            cbTipo.setValue("INDUSTRIAL");
            txtDatoExtra.setText(String.valueOf(robotIndustrial.getCapacidadCarga()));
        }
    }
    
    private void guardar() {
        try {
            String nombre = txtNombre.getText();
            int energia = Integer.parseInt(txtEnergia.getText());
            int serie = Integer.parseInt(txtSerie.getText());
            String tipo = cbTipo.getValue();
            
            // Si no existe, creamos uno nuevo dependiendo del tipo
            if (robotExistente == null) {
                if (tipo.equals("DOMESTICO")) {
                    int tareas = Integer.parseInt(txtDatoExtra.getText());
                    robotResultado = new RobotDomestico(nombre, energia, serie, tareas);
                    
                } else {
                    double carga = Double.parseDouble(txtDatoExtra.getText());
                    robotResultado = new RobotIndustrial(nombre, energia, serie, carga);
                }
                
            } else {
                // // Si existía, modificamos sus campos
                robotExistente.setNombre(nombre);
                robotExistente.setNivelEnergia(energia);
                
                // Actualizamos el campo específico según la subclase
                if (robotExistente instanceof RobotDomestico robotDomestico) {
                    robotDomestico.setCantidadTareas(Integer.parseInt(txtDatoExtra.getText()));
                    
                } else if (robotExistente instanceof RobotIndustrial robotIndustrial) {
                    robotIndustrial.setCapacidadCarga(Double.parseDouble(txtDatoExtra.getText()));
                }
                
                robotResultado = robotExistente; // Lo devolvemos
            }
            
            stage.close();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ingrese números válidos.");
            alert.show();
        }
    }
    
    public Robot showAndWait() {
        stage.showAndWait();
        return robotResultado;
    }
}
