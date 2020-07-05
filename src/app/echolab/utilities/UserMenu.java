/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.utilities;

import app.echolab.dao.EmployeeDAO;
import app.echolab.entities.Employee;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mario
 */
public class UserMenu {
    private static final Logger LOG = LoggerFactory.getLogger(UserMenu.class);
    private static final EmployeeDAO employeeDAO = new EmployeeDAO();
    private static List<Employee> employees;

    public static String displayUserMenu(){
        return "Ha ingresado correctamente al sistema como agente de RRHH #"
                                + "Que accion deseas realizar? #"
                                + "1. Actualización de datos del empleado #"
                                + "2. Desactivación de empleados por despido #"
                                + "3. Contratación de empleados #"
                                + "4. Asignación de departamento #"
                                + "5. Asignación de Jefaturas #"
                                + "6. Actualización de salario mensual #"
                                + "7. Visualización de pagos generados #"
                                + "8. Generación de pagos en planilla#"
                                + "9. Salir del sistema";
    }
    
    public static void hireEmployee(PrintWriter out, BufferedReader in, String username, LocalDate creationDate){
        try {
            boolean isAddingUser = true;
            do{
                out.println("Ha seleccionado Contratación de empleados #"
                + "Por favor, ingrese el ID del empleado: ");
                int idEmployee = Integer.parseInt(in.readLine());
                out.println("Por favor, ingrese el Nombre del empleado: ");
                String firstName = in.readLine();
                out.println("Por favor, ingrese el Apellido del empleado: ");
                String lastName = in.readLine();
                out.println("Por favor, ingrese el Genero del empleado(M/F)");
                String gender = in.readLine();
                out.println("Por favor, ingrese el Email del empleado ");
                String email = in.readLine();
                out.println("Por favor, ingrese la fecha de Nacimiento del empleado #"
                        + "Año(yyyy): ");
                int bYear = Integer.parseInt(in.readLine());
                out.println("Mes(MM): ");
                int bMonth = Integer.parseInt(in.readLine());
                out.println("Dia(dd): ");
                int bDay = Integer.parseInt(in.readLine());
                LocalDate birthDate = LocalDate.of(bYear, bMonth, bDay);
                out.println("Por favor, ingrese la fecha de Contratacion del empleado #"
                        + "Año(yyyy): ");
                int hYear = Integer.parseInt(in.readLine());
                out.println("Mes(MM): ");
                int hMonth = Integer.parseInt(in.readLine());
                out.println("Dia(dd): ");
                int hDay = Integer.parseInt(in.readLine());
                LocalDate hireDate = LocalDate.of(hYear, hMonth, hDay);
                out.println("Por favor, ingrese el ID del estado del empleado ");
                int idState = Integer.parseInt(in.readLine());
                out.println("Por favor, ingrese el nombre del departamento del empleado ");
                String departmentName = in.readLine();
                out.println("Por favor, ingrese el ID del jefe del empleado ");
                int idSuper = Integer.parseInt(in.readLine());
                out.println("Por favor, ingrese el Salario Base del empleado ");
                int salary = Integer.parseInt(in.readLine());
                
                Employee e = new Employee(idEmployee, firstName, lastName, gender, email, birthDate, hireDate, idState, departmentName, idSuper, creationDate, username);
                out.println("El empleado sera registrado como: #"
                            + e + " #"
                            + "Desea continuar?(Si/No)");
                if(in.readLine().toUpperCase().startsWith("S"))
                    employeeDAO.insertData(e);
                out.println("Desea agregar un nuevo empleado? (Si/No)");
                if(in.readLine().toUpperCase().startsWith("N"))
                    isAddingUser = false;
            }while(isAddingUser);
        } catch(NumberFormatException | IOException | ClassNotFoundException | SQLException ex){
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void updateEmployee(PrintWriter out, BufferedReader in, String username, LocalDate modificationDate) throws ClassNotFoundException, SQLException{
        employees = employeeDAO.getAllData();
        int numOfEmployees = employees.size()-1;
        out.println("Ha seleccionado Actualización de datos del empleado #"
                + "Por favor, ingrese el ID (1-"+numOfEmployees+") del empleado que desea modificar: ");
        boolean isUpdating = true;
        int idEmployee;
        try {
            do{
                idEmployee = Integer.parseInt(in.readLine());
                if(idEmployee==-1) return;
                Employee e = employees.get(idEmployee);
                LOG.info("Actualizando al empleado "+e);
                out.println("El empleado que has seleccionado es "+e.getFirstName()+" "+e.getLastName()+", con ID "+e.getIdEmployee()+" #"
                        + "Deseas continuar? (Si/No)");
                if(in.readLine().toUpperCase().startsWith("N"))
                    return;
                boolean isModifying = true;
                do{
                    out.println("Que campo del empleado desea modificar? #"
                            + "1. Nombre #"
                            + "2. Apellido #"
                            + "3. Genero #"
                            + "4. Email #"
                            + "5. Fecha de nacimiento #"
                            + "6. Fecha de contratacion #"
                            + "7. Id del estado #"
                            + "8. Nombre del departamento #"
                            + "9. ID del jefe #"
                            + "q. Salir de la operacion");
                    switch(in.readLine()){
                        case "1":
                            out.println("Ingrese el nuevo nombre");
                            String updatedFirstName = in.readLine();
                            e.setFirstName(updatedFirstName);
                            break;
                        case "2":
                            out.println("Ingrese el nuevo apellido");
                            String updatedLastName = in.readLine();
                            e.setLastName(updatedLastName);
                            break;
                        case "3":
                            out.println("Ingrese el nuevo genero");
                            String updatedGender = in.readLine();
                            e.setGender(updatedGender);
                            break;
                        case "4":
                            out.println("Ingrese el nuevo email");
                            String updatedEmail = in.readLine();
                            e.setEmailEmployee(updatedEmail);
                            break;
                        case "5":
                            try {
                                out.println("Ingrese la nueva fecha de nacimiento #"
                                        + "Ingrese el año(yyyy): ");
                                int year = Integer.parseInt(in.readLine());
                                out.println("Ingrese el mes(MM): ");
                                int month = Integer.parseInt(in.readLine());
                                out.println("Ingrese el dia(dd): ");
                                int day = Integer.parseInt(in.readLine());
                                LocalDate eBirthDate = LocalDate.of(year, month, day);
                                e.setBirthDate(eBirthDate);
                            } catch (NumberFormatException ex) {
                                LOG.error("Numero invalido");
                                out.println("Fecha invalida");
                            }
                            break;
                        case "6":
                            try {
                                out.println("Ingrese la nueva fecha de contratacion #"
                                        + "Ingrese el año(yyyy): ");
                                int year = Integer.parseInt(in.readLine());
                                out.println("Ingrese el mes(MM): ");
                                int month = Integer.parseInt(in.readLine());
                                out.println("Ingrese el dia(dd): ");
                                int day = Integer.parseInt(in.readLine());
                                LocalDate eHireDate = LocalDate.of(year, month, day);
                                e.setHireDate(eHireDate);
                            } catch (NumberFormatException ex) {
                                LOG.error("Numero invalido");
                                out.println("Fecha invalida");
                            }
                            break;
                        case "7":
                            try{
                                out.println("Ingrese el nuevo ID del estado del empleado");
                                e.setIdState(Integer.parseInt(in.readLine()));
                            }catch(NumberFormatException ex){
                                LOG.error("Numero invalido");
                                out.println("No ingreso un numero valido");
                            }
                            break;
                        case "8":
                            break;
                        case "9":
                            try{
                                out.println("Ingrese el nuevo ID del jefe del empleado");
                                e.setIdSuper(Integer.parseInt(in.readLine()));
                            }catch(NumberFormatException ex){
                                LOG.error("Numero invalido");
                                out.println("No ingreso un numero valido, ingrese cualquier tecla para continua");
                                in.readLine();
                            }
                            break;
                        case "q":
                            return;
                        default:
                            out.println("No selecciono una opcion valida, ingrese cualquier tecla para continuar");
                            in.readLine();
                        }
                        out.println("El usuario modificado contiene los siguientes campos: #"
                                + e.toString() + " #"
                                + "Desea modificar otro campo?(Si/No)");
                                if(in.readLine().toUpperCase().startsWith("N"))
                                    isModifying = false;
                    }while(isModifying);
                    out.println("Esta seguro de ingresar los cambios al empleado? (Si/No)");
                    if(in.readLine().toUpperCase().startsWith("S"))
                        employeeDAO.updateData(e, username, modificationDate);
                    
                    out.println("Desea modificar otro empleado? (Si/No)");
                    if(in.readLine().toUpperCase().startsWith("N"))
                        isUpdating = false;

            }while(isUpdating);
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            java.util.logging.Logger.getLogger(UserMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
