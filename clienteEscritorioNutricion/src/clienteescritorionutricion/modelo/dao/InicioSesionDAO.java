/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion.modelo.dao;

import clienteescritorionutricion.modelo.ConexionWS;
import clienteescritorionutricion.modelo.pojo.RespuestaHTTP;
import clienteescritorionutricion.modelo.pojo.RespuestaLogin;
import clienteescritorionutricion.utils.Constantes;
import com.google.gson.Gson;
import java.net.HttpURLConnection;

/**
 *
 * @author babyalpuche
 */
public class InicioSesionDAO {
    public static RespuestaLogin iniciarSesion(String numeroPersonal, String password){
        RespuestaLogin respuesta = new RespuestaLogin();
        String url = Constantes.URL_WS + "autenticacion/loginEscritorio";
        String parametros = String.format("numeroPersonal=%s&password=%S", numeroPersonal, password);
        
        RespuestaHTTP respuestaPeticion = ConexionWS.peticionPOST(url, parametros);
        if(respuestaPeticion.getCodigoRespuesta() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            respuesta = gson.fromJson(respuestaPeticion.getContenido(), RespuestaLogin.class);
           
        }else{
            respuesta.setError(Boolean.TRUE);
            respuesta.setMensaje("Hubo un error al procesar la solicitud, por favor intentelo más tarde");
        }
        return respuesta;
    }
}
