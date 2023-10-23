/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.HashMap;
import modelo.pojo.Medico;
import modelo.pojo.RespuestaLoginEscritorio;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;



public class AutenticacionDAO {
    

    public static RespuestaLoginEscritorio verificarSesionEscritorio(
            String numeroPersonal,
            String password){
        
        RespuestaLoginEscritorio respuesta = new RespuestaLoginEscritorio();
        SqlSession conexionBD = MyBatisUtil.getSession();
        
        if(conexionBD !=  null){
            try{ 
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("numeroPersonal", numeroPersonal);
                parametros.put("password", password);
                Medico medicoSesion = conexionBD.selectOne("autenticacion.loginEscritorio", parametros);
                
                if(medicoSesion != null){
                    respuesta.setError(false);
                    respuesta.setMensaje("Bienvenido(a)" + medicoSesion.getNombre() + "al sistema de nutricion");
                    respuesta.setMedicoSesion(medicoSesion);
                } else {
                    respuesta.setError(true);
                    respuesta.setMensaje("El numero de personal y/o la contraseña son incorrectas, "
                            + " favor de verificar e intertarlo nuemavente");
                }
            } catch (Exception e){
                respuesta.setMensaje("error" + e.getMessage());
            }
        } else {
            respuesta.setError(true);
            respuesta.setMensaje("Error de conexion a la base de datos.");
        }
        return respuesta;
    } 

}