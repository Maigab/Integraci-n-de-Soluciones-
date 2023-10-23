/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.HashMap;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import modelo.pojo.Mensaje;
import modelo.pojo.Paciente;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

@Path("pacientes")
public class PacienteWS {
   
    @Context
    private UriInfo context;

    @GET
    @Path("prueba")
    @Produces(MediaType.APPLICATION_JSON)
    public String obtenerPaciente(){
        return "Hola mundo";
    }
    
    //busqueda por idMedico
    @GET
    @Path("listaPacientes/{idMedico}") //pathparam
    @Produces(MediaType.APPLICATION_JSON)
    public List<Paciente> getPacientes(@PathParam("idMedico") int idMedico){
        List<Paciente> paciente = null;
        SqlSession conexionDB = MyBatisUtil.getSession();
        if (conexionDB != null) {
            try{
                paciente = conexionDB.selectList("paciente.buscarPacienteIdMedico", idMedico);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conexionDB.close();
            }
        }
        return paciente;
    }
    
    
    //Registrar Paciente
    @POST
    @Path("registroPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje registroPaciente(
                    @FormParam("nombre") String nombre,
                    @FormParam("apellidoPaterno") String apellidoPaterno,
                    @FormParam("apellidoMaterno") String apellidoMaterno,
                    @FormParam("fechaNacimiento") String fechaNacimiento,
                    @FormParam("sexo") String sexo,
                    @FormParam("peso") float peso,
                    @FormParam("estatura") int estatura,
                    @FormParam("talla") int talla,
                    @FormParam("email") String email,
                    @FormParam("telefono") String telefono,
                    @FormParam("password") String password,
                    @FormParam("idMedico") int idMedico){
        
        Paciente paciente = new Paciente();
        Paciente correo = new Paciente();
        paciente.setNombre(nombre);
        paciente.setApellidoPaterno(apellidoPaterno);
        paciente.setApellidoMaterno(apellidoMaterno);
        paciente.setFechaNacimiento(fechaNacimiento);
        paciente.setSexo(sexo);
        paciente.setPeso(peso);
        paciente.setEstatura(estatura);
        paciente.setTalla(talla);
        paciente.setEmail(email);
        paciente.setTelefono(telefono);
        paciente.setPassword(password);
        paciente.setIdMedico(idMedico);
        
        Mensaje msj = new Mensaje();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if (conexionDB != null) {
            try{
                correo = conexionDB.selectOne("paciente.comprobarCorreo", email);
                if (correo == null){  
                    int filasAfectadas = conexionDB.insert("paciente.registroPaciente", paciente);
                    conexionDB.commit();
                    if(filasAfectadas > 0){
                        msj.setError(false);
                        msj.setMensaje("el paciente se registro correctamente, " + filasAfectadas + " filas");
                    } else {
                        msj.setError(true);
                        msj.setMensaje("hubo un error al guardar los datos del paciente," + filasAfectadas);
                    }
                } else {
                    msj.setError(true);
                    msj.setMensaje("el correo introducido ya existe en la base de datos");
                }
            } catch (Exception e) {
                msj.setError(true);
                msj.setMensaje("Error: " + e.getMessage());
            } finally {
                conexionDB.close();
            }
        } else {
            msj.setError(true);
            msj.setMensaje("no hay conexion establecida con la base de datos para guardar el paciente, intente de nuevo mas tarde");
        }
        return msj;
    }
    
    
    //modificar Paciente
    //se puede cambiar la manera de modificar el paciente con el id: @FormParam("idPaciente") String idPaciente
    //de igual se puede comprobar con el id y el email si se encuentra el paciente con esos dos parametros
    @PUT
    @Path("editarPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje editarPaciente(@FormParam("email") String email,
                    @FormParam("nombre") String nombre,
                    @FormParam("apellidoPaterno") String apellidoPaterno,
                    @FormParam("apellidoMaterno") String apellidoMaterno,
                    @FormParam("fechaNacimiento") String fechaNacimiento,
                    @FormParam("sexo") String sexo,
                    @FormParam("peso") float peso,
                    @FormParam("estatura") int estatura,
                    @FormParam("talla") int talla,
                    @FormParam("telefono") String telefono,
                    @FormParam("password") String password,
                    @FormParam("idMedico") int idMedico){
        
        HashMap<String,Object> parametros = new HashMap<>();
        parametros.put("email", email);
        parametros.put("nombre", nombre);
        parametros.put("apellidoPaterno", apellidoPaterno);
        parametros.put("apellidoMaterno", apellidoMaterno);
        parametros.put("fechaNacimiento", fechaNacimiento);
        parametros.put("sexo", sexo);
        parametros.put("peso", peso);
        parametros.put("estatura", estatura);
        parametros.put("talla", talla);
        parametros.put("telefono", telefono);
        parametros.put("password", password);
        parametros.put("idMedico", idMedico);
        
        Mensaje msj = new Mensaje();
        SqlSession conexionDB = MyBatisUtil.getSession();
        
        if(conexionDB != null){
            try{
                Paciente correo = conexionDB.selectOne("paciente.comprobarCorreo", email);
                if (correo == null){ 
                    msj.setError(true);
                    msj.setMensaje("el correo no existe en la base de datos, revisa el correo e intentalo nuevamente");
                } else {
                    int filasAfectadas = conexionDB.update("paciente.editarPaciente", parametros);
                    conexionDB.commit();
                    if (filasAfectadas > 0){
                        msj.setError(false);
                        msj.setMensaje("se ha modificado el paciente correctamente");
                    } else {
                        msj.setError(true);
                        msj.setMensaje("no se ha podido modificar el paciente con ese email");
                    }
                }      
            } catch (Exception e){
                msj.setError(true);
                msj.setMensaje("Error: " + e.getMessage());
            } finally{
                conexionDB.close();
            }
        } else {
            msj.setError(true);
            msj.setMensaje("no hay conexion con la base de datos");
        }     
        return msj;
    }
    
    //eliminar paciente
    @DELETE
    @Path("eliminarPaciente")
    @Produces(MediaType.APPLICATION_JSON)
    public Mensaje eliminarPAciente(@FormParam("email") String email){
        Mensaje msj = new Mensaje();
        SqlSession conexionDB = MyBatisUtil.getSession();
        if (email.isEmpty()){
            msj.setError(true);
            msj.setMensaje("por favor introduce un email para eliminar");
        } else {
            if (conexionDB != null) {
                try{
                    Paciente emailExiste = conexionDB.selectOne("paciente.comprobarCorreo", email);
                    if(emailExiste == null){
                        msj.setError(true);
                        msj.setMensaje("El email introducido no existe, por favor revise el email");
                    } else {
                        int filasAfectadas = conexionDB.delete("paciente.eliminarPaciente", email);
                        conexionDB.commit();
                        if(filasAfectadas > 0){
                            msj.setError(false);
                            msj.setMensaje("se ha eliminado el paciente, " + filasAfectadas);
                        } else {
                            msj.setError(true);
                            msj.setMensaje("hubo un error al eliminar los datos del paciente," + filasAfectadas);
                        }
                    }
                } catch (Exception e) {
                    msj.setError(true);
                    msj.setMensaje("Error: " + e.getMessage());
                } finally {
                    conexionDB.close();
                }
            } else {
                msj.setError(true);
                msj.setMensaje("no hay conexion establecida con la base de datos para eliminar un paciente");
            }
        }
        return msj;
    }  
}
