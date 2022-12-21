package com.example.parquejurasico;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static utilidades.CRUD_Dinosaurios.*;

public class EditorController implements Initializable {

    @FXML
    AnchorPane moverDino;

    @FXML
    private Label errorVacio;
    @FXML
    private Label errorRecinto;
    @FXML
    private Label errorNumero;
    @FXML
    private Label errorDinosaurio;
    @FXML
    private Label errorTraslado;
    @FXML
    private Label nDinoTraslado;

    @FXML
    private TextField id;
    @FXML
    private TextField raza;
    @FXML
    private TextField nombre;
    @FXML
    private TextField altura;
    @FXML
    private TextField peso;

    @FXML
    private Button nuevo;
    @FXML
    private Button trasladar;
    @FXML
    private Button borrar;
    @FXML
    private Button meteo;
    @FXML
    private Button lazaro;
    @FXML
    private Button volver;
    @FXML
    private Button limpiar;
    @FXML
    private Button vale;
    @FXML
    private Button cancel;


    private ArrayList<String> dietas = new ArrayList<>();
    @FXML
    private ComboBox<String> dieta;

    private ArrayList<String> recintos = new ArrayList<>();
    @FXML
    private ComboBox<String> recinto;
    @FXML
    private ComboBox<String> recTraslado;

    @FXML
    private ListView<String> listDinos;


    @FXML
    private void switchToPrimary() throws IOException {
        HelloApplication.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rellenarDieta(dietas);
        for (int i = 0; i < dietas.size(); i++) {
            dieta.getItems().add(dietas.get(i));
        }

        llenarRecinto(recintos);
        for (int j = 0; j < recintos.size(); j++) {
            recinto.getItems().add(recintos.get(j));
        }

        for (int j = 0; j < recintos.size(); j++) {
            recTraslado.getItems().add(recintos.get(j));
        }

    }

    @FXML
    private void addDino() {
        if (comprobarCampos() == true) { //LLEVO A CABO COMPROBACIÓN DE CAMPOS (NULOS, DÍGITOS NUMÉRICOS)
            mostrarErrores();
        } else {
            Recinto rec = buscarRecinto(recinto.getValue());
            String name = nombre.getText();
            String rz = raza.getText();
            int alt = Integer.parseInt(altura.getText());
            int ps = Integer.parseInt(peso.getText());
            String diet = dieta.getValue();

            Dinosaurio dino = new Dinosaurio(rec, name, rz, diet, alt, ps, true);

            guardarDinosaurio(dino); //GUARDO EL DINOSAURIO EN LA BASE DE DATOS
            selNatural(dino.getDieta(), dino.getRecinto().getIdRecinto()); //COMPRUEBO SI HAY HERVÍBOROS
            limpiarCampos();
            actualizarLista();
            errorVacio.setVisible(false);
            errorRecinto.setVisible(false);
            errorNumero.setVisible(false);
        }
    }

    @FXML
    private void deleteDino() {
        if (recinto.getValue() == null) {
            errorRecinto.setText("Selecciona un recinto primero");
            errorRecinto.setVisible(true);
        } else if (listDinos.getSelectionModel().getSelectedItem() == null) {
            errorRecinto.setVisible(false);
            errorDinosaurio.setVisible(true);
        } else {
            String nDino = (String) this.listDinos.getSelectionModel().getSelectedItem();
            System.out.println(nDino);
            eliminarDinosaurio(nDino);
            actualizarLista();
            errorRecinto.setVisible(false);
            errorDinosaurio.setVisible(false);
        }
    }

    @FXML
    private void limpiarCampos() {
        nombre.setText("");
        raza.setText("");
        altura.setText("");
        peso.setText("");
        dieta.setValue(null);
    }


    @FXML
    private boolean comprobarCampos() {
        if (nombre.getText().equalsIgnoreCase("")) {
            return true;
        } else if (raza.getText().equalsIgnoreCase("")) {
            return true;
        } else if (dieta.getValue() == null) {
            return true;
        } else if (altura.getText().equalsIgnoreCase("")) {
            return true;
        } else if (peso.getText().equalsIgnoreCase("")) {
            return true;
        } else if (recinto.getValue() == null) {
            return true;
        } else if (altura.getText().matches("\\d")) {
            return true;
        } else if (peso.getText().matches("\\d")) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    private void mostrarErrores() {
        errorVacio.setVisible(false);
        errorRecinto.setVisible(false);
        errorNumero.setVisible(false);
        ArrayList<String> campos = new ArrayList<>();
        /* En este array se irán añadiendo el nombre de las errores
        que puedan surgir durante la comprobación de campos, de manera que,
        cuando salga el mensaje de error el usuario puede ver qué falla y dónde
         */


        if (nombre.getText().equalsIgnoreCase("")) {
            campos.add("Nombre");
        }
        if (raza.getText().equalsIgnoreCase("")) {
            campos.add("Raza");
        }
        if (dieta.getValue() == null) {
            campos.add("Dieta");
        }
        if (altura.getText().equalsIgnoreCase("")) {
            campos.add("Altura");
        }
        if (peso.getText().equalsIgnoreCase("")) {
            campos.add("Peso");
        }

        if (!campos.isEmpty()) {

            errorVacio.setText("Error, no puedes dejar estos campos vacíos: " + campos.toString());
            errorVacio.setVisible(true);
        }

        campos.clear();

        if (!altura.getText().matches("\\d")) {
            errorNumero.setText("La altura y el peso tienen que ser valores numéricos");
            errorNumero.setVisible(true);
        }

        if (!peso.getText().matches("\\d")) {
            errorNumero.setText("La altura y el peso tienen que ser valores numéricos");
            errorNumero.setVisible(true);
        }

        if (recinto.getValue() == null) {
            errorRecinto.setText("Debes seleccionar un recinto");
            errorRecinto.setVisible(true);
        }

    }

    @FXML
    private void actualizarLista() {
        listDinos.setItems(rellenarList(recinto.getValue()));
    }

    @FXML
    private void extintion() {
        meteo();
    }

    @FXML
    private void lazaro() {
        if (recinto.getValue() == null) {
            errorRecinto.setText("Selecciona un recinto primero");
            errorRecinto.setVisible(true);
        } else if (listDinos.getSelectionModel().getSelectedItem() == null) {
            errorRecinto.setVisible(false);
            errorDinosaurio.setVisible(true);
        } else {
            revivir(listDinos.getSelectionModel().getSelectedItem());
            errorRecinto.setVisible(false);
            errorDinosaurio.setVisible(false);
        }
    }

    @FXML
    private void mostrarTraslado(){
        if (recinto.getValue() == null) {
            errorRecinto.setText("Selecciona un recinto primero");
            errorRecinto.setVisible(true);
        } else if (listDinos.getSelectionModel().getSelectedItem() == null) {
            errorRecinto.setVisible(false);
            errorDinosaurio.setVisible(true);
        } else {
            nDinoTraslado.setText(listDinos.getSelectionModel().getSelectedItem());
            /*
            Aunque no lo parezca, nDinoTraslado es un label a parte que recoge únicamente el nombre
            del dinosaurio seleccionado en el ListView, de manera que aunque el método en el que se ejecuta
            el traslado es el de abajo, no perdemos la referencia del nombre del dinosaurio que enviaremos
             */
            moverDino.setVisible(true);
            errorRecinto.setVisible(false);
            errorDinosaurio.setVisible(false);
        }
    }

    @FXML
    private void trasladar(){
        Dinosaurio dino = encontrarDino(nDinoTraslado.getText());
        Recinto rec = buscarRecinto(recTraslado.getValue());
        if (dino.getRecinto().getIdRecinto() == rec.getIdRecinto()) {
            errorTraslado.setText(dino.getNombre() + " ya está en este recinto");
            errorTraslado.setVisible(true);
        }else {
            trasladarDinosaurio(dino, recTraslado.getValue());
            selNatural(dino.getDieta(), dino.getRecinto().getIdRecinto());
            //Aquí también hay que aplicar el método de selección natural, como en el de inserción de nuevos dinos
            moverDino.setVisible(false);
            actualizarLista();
            errorTraslado.setVisible(false);
        }
    }
    @FXML
    private void cancelarTraslado(){
        moverDino.setVisible(false);
        errorTraslado.setVisible(false);
    }


}
