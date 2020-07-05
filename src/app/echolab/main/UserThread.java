/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.main;

import app.echolab.properties.ConfigProperties;
import app.echolab.utilities.AdminMenu;
import app.echolab.utilities.Auth;
import app.echolab.utilities.Decrypt;
import app.echolab.utilities.UserMenu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import org.apache.commons.mail.EmailException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Mario
 */
public class UserThread extends Thread{
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UserThread.class);
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final int sessionTimeoutMilis;
    public UserThread(Socket clientSocket, int sessionTimeoutMilis) {
        this.clientSocket = clientSocket;
        this.sessionTimeoutMilis = sessionTimeoutMilis;
    }
    @Override
    public void run(){
        try{
            LOG.info("Inicio de thread de usuario");
            //Establecemos el tiempo de espera del socket, asi como los canales de input y output para comunicarnos con el usuario
            clientSocket.setSoTimeout(sessionTimeoutMilis);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("Bienvenido al sistema de RRHH, #"
                    + "Por favor, ingrese su nombre de usuario: ");
            
            //Obtenemos las credenciales del usuario para probar la conexion a la base de datos
            String username = in.readLine();
            LOG.info("Ingreso de usuario");
            out.println("Por favor, ingrese su contraseña: ");
            String password = in.readLine();
            LOG.info("Ingreso de contraseña");
            
            //Comprobamos el usuario y la contraeña
            boolean isValidUser = false;
            Properties credentialProps = new Properties();
            credentialProps.load(ConfigProperties.getResourceAsInputStream("credentials.properties"));
            try{
                LOG.info("Verificando usuario");
                if(Decrypt.decryptText(credentialProps.getProperty(username)) == null || !Decrypt.decryptText(credentialProps.getProperty(username)).equals(password)){
                    out.println("No ingreso credenciales validas, saldra del programa");
                }else{
                    isValidUser = true;
                }
            } catch(NullPointerException ex){
                LOG.error("No se encontro usuario en fichero de credenciales");
            }
            
            if(isValidUser){
                /*
                LOG.info("Es un usuario valido");
                //Almacenamos el email del usuario para enviarle un codigo de autenticacion
                LOG.info("Ingreso de email del usuario");
                String userEmail = Decrypt.decryptText(credentialProps.getProperty(username+".email"));

                //Generamos el codigo aleatorio
                LOG.info("Generacion del codigo de autenticacion");
                Auth.generateCode();
                try {
                    //Enviamos el codigo al correo del usuario
                    LOG.info("Envio de codigo al correo proporcionado");
                    Auth.sendEmail(userEmail);
                    out.println("Se le envio el codigo al correo electronico #"
                            + "Por favor, digite el codigo");
                } catch (EmailException ex) {
                    //En dado caso el email no sea valido, se procedera a sacar al usuario del sistema
                    out.println("No se encontro el email, saldra del sistema");
                    LOG.error("No se encontro email valido");
                }
                
                //Verificamos que el codigo enviado al correo concuerde con el ingresado por el usuario
                //En caso el usuario ingrese un codigo incorrecto se procedera a sacarlo del sistema
                LOG.info("Ingreso de codigo de autenticacion");     
                if(!in.readLine().equals(Auth.code)) 
                    out.println("El codigo que ingreso no es correcto, saldra del sistema");
                */
                //Luego de las verificaciones correspondientes ingresamos al menu del programa
                boolean isUserAdmin = Decrypt.decryptText(credentialProps.getProperty(username+".rol")).equals("admin");
                LOG.info("El usuario ingresado es admin?: "+isUserAdmin);
                
                String inputLine;
                boolean activeFlag = true;
                while (activeFlag&&clientSocket.isConnected()){
                    LOG.info("Ingreso al menu del programa");
                    if(isUserAdmin){
                        out.println(AdminMenu.displayAdminMenu());
                    } else{
                        out.println(UserMenu.displayUserMenu());
                    }
                    if((inputLine=in.readLine())!= null){
                        if(isUserAdmin){
                            //Menu para el administrador
                            System.out.println("--->"+inputLine);
                            switch(inputLine.trim()){
                                case "1":
                                    out.println("Ha seleccionado Gestion de departamentos");
                                    break;
                                case "2":
                                    out.println("Ha seleccionado Gestion de estados de empleados");
                                    break;
                                case "3":
                                    out.println("Ha seleccionado Gestion de usuarios");
                                    break;
                                case "4":
                                    out.println("Ha seleccionado Gestion de roles");
                                    break;
                                case "5":
                                    out.println("Ha seleccionado Salir del programa #"
                                            + "A continuacion, saldra del programa #"
                                            + "Tenga un buen dia");
                                    activeFlag = false;
                                    break;
                                default:
                                    out.println("La opcion seleccionada es invalida, intente nuevamente");
                            }
                        } else{
                            //Menu para usuario de RRHH
                           System.out.println("--->"+inputLine);
                            switch(inputLine.trim()){
                                case "1":
                                    //out.println("Ha seleccionado Actualización de datos del empleado");
                                    LocalDate modificationDate = LocalDate.now();
                                    UserMenu.updateEmployee(out, in, username, modificationDate);
                                    break;
                                case "2":
                                    out.println("Ha seleccionado Desactivación de empleados por despido");
                                    break;
                                case "3":
                                    //out.println("Ha seleccionado Contratación de empleados");
                                    LocalDate creationDate = LocalDate.now();
                                    UserMenu.hireEmployee(out, in, username, creationDate);
                                    break;
                                case "4":
                                    out.println("Ha seleccionado Asignación de departamento");
                                    break;
                                case "5":
                                    out.println("Ha seleccionado Asignación de Jefaturas");
                                    break;
                                case "6":
                                    out.println("Ha seleccionado Actualización de salario mensual");
                                    break;
                                case "7":
                                    out.println("Ha seleccionado Visualización de pagos generados");
                                    break;
                                case "8":
                                    out.println("Ha seleccionado Generación de pagos en planilla");
                                    break;
                                case "9":
                                    out.println("Ha seleccionado Salir del programa #"
                                            + "A continuacion, saldra del programa #"
                                            + "Tenga un buen dia");
                                    activeFlag = false;
                                    break;
                                default:
                                    out.println("La opcion seleccionada es invalida, intente nuevamente");
                            } 
                        }
                    }
                }
            } else {
                LOG.error("Usuario no encontrado en el fichero de credenciales");
            }
        } catch (SocketException ex) {
            Logger.getLogger(UserThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(UserThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
}
