package utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import models.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GestorJson {
    private final String ARCHIVO = "robots.json";
    private Gson gson;

    public GestorJson() {
        // Usamos registerTypeAdapter para indicar que hacer con la clase Robot
        this.gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Robot.class, new RobotAdapter()).create();
    }

    // Gson toma la lista y la convierte a texto en el archivo.
    public void guardar(List<Robot> lista) {
        try (FileWriter writer = new FileWriter(ARCHIVO)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public List<Robot> cargar() {
        List<Robot> lista = new ArrayList<>();
        
        try (FileReader reader = new FileReader(ARCHIVO)) {
            // Definimos que queremos cargar una lista de robots
            Type tipoLista = new TypeToken<ArrayList<Robot>>(){}.getType();
            lista = gson.fromJson(reader, tipoLista); // Lee el archivo y lo transforma
            
            if (lista == null) { // Por si el archivo estaba vacío
                lista = new ArrayList<>();
            } 
            
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe aún. Se crea lista vacía.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Decide si un robot es Doméstico o Industrial al leer el JSON.
    private class RobotAdapter implements JsonDeserializer<Robot>, JsonSerializer<Robot> {
        // Convierte un objeto Robot en un json
        @Override
        public JsonElement serialize(Robot src, Type typeOfSrc, JsonSerializationContext context) {
            return context.serialize(src);
        }

        // Convierte JSON en un objeto Robot porque Robot es abstracto
        @Override
        public Robot deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject(); // Convertimos el dato a un gson objecto
            String tipo = jsonObject.get("tipo").getAsString(); // Leemos el campo "tipo" del json
            
            // Decidimos qué clase crear
            if (tipo.equals("DOMESTICO")) { // Si es Domestico, creamos un RobotDomestico
                return context.deserialize(jsonObject, RobotDomestico.class);
                
            } else if (tipo.equals("INDUSTRIAL")) { // Si es Industrial, creamos un RobotIndustrial
                return context.deserialize(jsonObject, RobotIndustrial.class);
            }
            
            throw new JsonParseException("Tipo de robot desconocido: " + tipo);
        }
    }
}