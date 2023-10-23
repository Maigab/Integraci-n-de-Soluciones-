/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion;

import clienteescritorionutricion.modelo.dao.InicioSesionDAO;
import clienteescritorionutricion.modelo.pojo.RespuestaLogin;
import clienteescritorionutricion.utils.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author babyalpuche
 */
public class FXMLClienteEscritorioNutricionController implements Initializable {
    
    @FXML
    private Label labelErrorPersonal;
    private Label labelErrorPassword;
    private TextField tfNumPersonal;
    private PasswordField tfPassword;
    private Button btnInicioSesion;
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    private void btnIniciarSesion(ActionEvent event) {
        String numPersonal = tfNumPersonal.getText();
        String password = tfPassword.getText();
        labelErrorPersonal.setText(" ");
        labelErrorPassword.setText(" ");
        Boolean isValido = true;
        if(numPersonal.isEmpty()){
            labelErrorPersonal.setText("El número de personal es obligatorio");
        }
        if(password.isEmpty()){
            labelErrorPassword.setText("La contraseña es obligatoria");
        }
        if(isValido){
            verificarCredenciales(numPersonal, password);
        }
    }

    private void verificarCredenciales(String numPersonal, String password) {
        RespuestaLogin respuesta = InicioSesionDAO.iniciarSesion(numPersonal, password);
        
        if(respuesta.getError()== false){
            Utilidades.mostrarAlertaSimple("Credenciales correctas", respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            irPantallaPrincipal();
        }else{
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void irPantallaPrincipal() {
        Stage stageActual = (Stage)tfNumPersonal.getScene().getWindow();
        try {
            Parent vista = FXMLLoader.load(getClass().getResource("FXMLHome.fxml"));
            Scene escena = new Scene(vista);
            stageActual.setScene(escena);
            stageActual.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
