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

/**
 *
 * @author Mario
 */
public class UserThread extends Thread{
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
            clientSocket.setSoTimeout(sessionTimeoutMilis);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out.println("Bienvenido al sistema de RRHH, #"
                    + "Por favor, ingrese su nombre de usuario: ");
            String username = in.readLine();
            out.println("Por favor, ingrese su contraseña: ");
            String password = in.readLine();
            
            try {
                Connection conn = ConnectionDB.openConnection(username, password);
                out.println("Se accedio correctamente al Sistema #"
                        + "Se le enviara un codigo a su correo para poder verificar su identidad #"
                        + "Por favor, digite su correo electronico");
                ConnectionDB.closeConnection(conn);
            } catch (SQLException ex) {
                out.println("No se pudo acceder al usuario, asegurese de haber ingresado correctamente el usuario y la contraseña, saldra del Sistema");
                Logger.getLogger(UserThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UserThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            String userEmail = in.readLine();
            Auth.generateCode();
            try {
                Auth.sendEmail(userEmail);
                out.println("Se le envio el codigo al correo electronico #"
                        + "Por favor, digite el codigo");
            } catch (EmailException ex) {
                out.println("No se encontro el email, saldra del sistema");
                Logger.getLogger(UserThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(!in.readLine().equals(Auth.code)) out.println("El codigo que ingreso no es correcto, saldra del sistema");
            
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
