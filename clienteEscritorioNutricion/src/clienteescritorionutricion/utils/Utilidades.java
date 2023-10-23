/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion.utils;

import javafx.scene.control.Alert;

/**
 *
 * @author babyalpuche
 */
public class Utilidades {
    public static void mostrarAlertaSimple(String titulo, String mensaje, Alert.AlertType tipo){
        Alert dialogoAlerta = new Alert(tipo);
        dialogoAlerta.setTitle(titulo);
        dialogoAlerta.setHeaderText(null);
        dialogoAlerta.setContentText(mensaje);
        dialogoAlerta.showAndWait();
    }
}
