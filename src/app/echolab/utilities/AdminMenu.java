/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.echolab.utilities;

/**
 *
 * @author Mario
 */
public class AdminMenu {
    public static String displayAdminMenu(){
        return "Ha ingresado correctamente al sistema como Administrador #"
                                + "Que accion deseas realizar? #"
                                + "1. Gestion de departamentos #"
                                + "2. Gestion de estados de empleados #"
                                + "3. Gestion de usuarios #"
                                + "4. Gestion de roles #"
                                + "5. Salir del programa";
    }
}
