/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.utilities;

import app.echolab.dao.DepartmentDAO;
import app.echolab.entities.Department;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
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
                                + "3. Gestion de usuarios #"
                                + "4. Gestion de roles #"
                                + "5. Salir del programa";
    }
    
    public static void departmentManager(PrintWriter out, BufferedReader in, String username, LocalDate creationDate) throws IOException, ClassNotFoundException, SQLException{
        DepartmentDAO departmentDAO = new DepartmentDAO();
//        List<Department> employees;
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
}
