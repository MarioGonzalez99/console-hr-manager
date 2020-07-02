/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.main;

import app.echolab.connection.ConnectionDB;
import app.echolab.utilities.Auth;
import java.sql.Connection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            try {
                //Prueba de conexion a la base de datos
                LOG.info("Prueba de conexion a la base de datos");
                ConnectionDB.loadConnection(username, password);
                Connection conn = ConnectionDB.openConnection();
                out.println("Se accedio correctamente al Sistema #"
                        + "Se le enviara un codigo a su correo para poder verificar su identidad #"
                        + "Por favor, digite su correo electronico");
                ConnectionDB.closeConnection(conn);
            } catch (SQLException ex) {
                //En caso que no se pueda conectar a la base de datos se procede a sacar al usuario del sistema
                out.println("No se pudo acceder al usuario, asegurese de haber ingresado correctamente el usuario y la contraseña, saldra del Sistema");
                LOG.warn("Credenciales incorrectas para el ingreso a la base de datos");
            } catch (ClassNotFoundException ex) {
                LOG.error("No se encontro la clase del driver");
            }
            //Almacenamos el email del usuario para enviarle un codigo de autenticacion
            LOG.info("Ingreso de email del usuario");
            String userEmail = in.readLine();
            LOG.info("Generacion del codigo de autenticacion");
            //Generamos el codigo aleatorio
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
            if(!in.readLine().equals(Auth.code)) out.println("El codigo que ingreso no es correcto, saldra del sistema");
            
            //Luego de las verificaciones correspondientes ingresamos al menu del programa
            LOG.info("Ingreso al menu del programa");
            out.println("Ha ingresado correctamente al sistema, que operacion desea realizar?");
            String inputLine;
            boolean activeFlag = true;
            while (activeFlag&&clientSocket.isConnected()){
                if((inputLine=in.readLine())!= null){
                    System.out.println("--->"+inputLine);
                    switch(inputLine.trim()){
                        case "hello server":
                            out.println("hello client");
                            break;
                        case ".":
                            out.println("good bye");
                            activeFlag = false;
                            break;
                        case ".Exit":
                            out.println("good bye");
                            activeFlag = false;
                            break;
                        default:
                            out.println("unrecognized command");
                    }
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(UserThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
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
