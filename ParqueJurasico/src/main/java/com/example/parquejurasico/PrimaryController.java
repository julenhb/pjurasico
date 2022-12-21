package com.example.parquejurasico;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utilidades.CRUD_Dinosaurios.rellenarTabla;


public class PrimaryController implements Initializable {
    @FXML
    private Button irEditor;

    @FXML
    private TableView<Dinosaurio> tablaPrincipal;
    @FXML
    TableColumn<String, Dinosaurio> id_recinto;
    @FXML
    TableColumn<String, Dinosaurio> id_dino;
    @FXML
    TableColumn<String, Dinosaurio> nombre;
    @FXML
    TableColumn<String, Dinosaurio> raza;
    @FXML
    TableColumn<String, Dinosaurio> dieta;
    @FXML
    TableColumn<String, Dinosaurio> altura;
    @FXML
    TableColumn<String, Dinosaurio> peso;
    @FXML
    TableColumn<String, Dinosaurio> vivo;



    @FXML
    private void switchToSecondary() throws IOException {
        HelloApplication.setRoot("editor");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_recinto.setCellValueFactory(new PropertyValueFactory<>("recinto"));
        id_dino.setCellValueFactory(new PropertyValueFactory<>("idDino"));
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        raza.setCellValueFactory(new PropertyValueFactory<>("raza"));
        dieta.setCellValueFactory(new PropertyValueFactory<>("dieta"));
        altura.setCellValueFactory(new PropertyValueFactory<>("altura"));
        peso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        vivo.setCellValueFactory(new PropertyValueFactory<>("vivo"));
        tablaPrincipal.setItems(rellenarTabla());
    }


}
