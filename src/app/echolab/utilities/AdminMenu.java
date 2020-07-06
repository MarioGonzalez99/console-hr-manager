/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.utilities;

import app.echolab.dao.DepartmentDAO;
import app.echolab.dao.StateDAO;
import app.echolab.entities.Department;
import app.echolab.entities.State;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mario
 */
public class AdminMenu {
    private static final Logger LOG = LoggerFactory.getLogger(AdminMenu.class);
    
    public static String displayAdminMenu(){
        return "Ha ingresado correctamente al sistema como Administrador #"
                                + "Que accion deseas realizar? #"
                                + "1. Gestion de departamentos #"
                                + "2. Gestion de estados de empleados #"
                                + "3. Gestion de empleados #"
                                + "4. Salir del programa";
    }
    
    public static void departmentManager(PrintWriter out, BufferedReader in, String username, LocalDate creationDate) throws IOException, ClassNotFoundException, SQLException{
        DepartmentDAO departmentDAO = new DepartmentDAO();
        out.println("Ha seleccionado Gestion de departamentos #"
                + "Digite la opcion que desea realizar #"
                + "1. Creacion de nuevo departamento #"
                + "2. Eliminacion de departamento");
        String resp = in.readLine();
        if(resp.equals("1")){
            out.println("Digite el nombre del nuevo departamento: ");
            String depName = in.readLine();
            out.println("Se agregara un nuevo departamento de nombre: "+depName+" #"
                    + "Desea proceder? (Si/No)");
            if(in.readLine().toUpperCase().startsWith("No"))
                return;
            
            Department d = new Department(depName, creationDate, username);
            departmentDAO.insertData(d);
        } else if(resp.equals("2")){
            out.println("Digite el nombre departamento que desea eliminar: ");
            String depName = in.readLine();
            
            out.println("Se eliminara el departamento "+depName+" #"
                    + "Desea proceder? (Si/No)");
            if(in.readLine().toUpperCase().startsWith("N"))
                return;
            
            departmentDAO.deleteData(depName);
            
        }
    }
    
    public static void stateEmployeeManager(PrintWriter out, BufferedReader in, String username, LocalDate creationDate) throws IOException, ClassNotFoundException, SQLException{
        StateDAO stateDAO = new StateDAO();
        out.println("Ha seleccionado Gestion de Estados de empleados #"
                + "Digite la opcion que desea realizar #"
                + "1. Creacion de nuevo Estado #"
                + "2. Eliminacion de un Estado");
        String resp = in.readLine();
        if(resp.equals("1")){
            out.println("Digite el id del nuevo Estado: ");
            int idState = Integer.parseInt(in.readLine());
            out.println("Digite el nombre del nuevo Estado: ");
            String nameState = in.readLine();
            out.println("Se agregara un nuevo Estado de nombre: "+nameState+" #"
                    + "Desea proceder? (Si/No)");
            if(in.readLine().toUpperCase().startsWith("No"))
                return;
            
            State s = new State(idState, nameState, creationDate, username);
            stateDAO.insertData(s);
        } else if(resp.equals("2")){
            out.println("Digite el id del Estado que desea eliminar: ");
            int idState = Integer.parseInt(in.readLine());
            
            out.println("Se eliminara el Estado con id "+idState+" #"
                    + "Desea proceder? (Si/No)");
            if(in.readLine().toUpperCase().startsWith("N"))
                return;
            
            stateDAO.deleteData(idState);
            
        }
    }
}
