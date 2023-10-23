/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteescritorionutricion.modelo.pojo;

/**
 *
 * @author babyalpuche
 */
public class RespuestaLogin {
    private Boolean error;
    private String mensaje;
    private Medico medicoSesion;

    public RespuestaLogin(Boolean error, String mensaje, Medico medicoSesion) {
        this.error = error;
        this.mensaje = mensaje;
        this.medicoSesion = medicoSesion;
    }

    public RespuestaLogin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Medico getMedicoSesion() {
        return medicoSesion;
    }

    public void setMedicoSesion(Medico medicoSesion) {
        this.medicoSesion = medicoSesion;
    }
    
    
}
