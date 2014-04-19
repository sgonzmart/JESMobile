/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BaseDatos.*;
import TADS.*;
import Simulador.*;
import javax.swing.*;
import java.awt.*;
import MSJ.*;

public class Inicio {

    static BaseDatos BD;
    static JDialog dialogo;
    static Cliente gc;
    static GestorSimulador s;

    public static void main(String[] args) {
        JProgressBar progressBar = new JProgressBar(30, 400);

        // Configuramos la barra de progreso:
        progressBar.setString("Conectando con base de datos");
        progressBar.setStringPainted(true);
        progressBar.setIndeterminate(true);

        dialogo = new JDialog();
        // Resto de la construcción del JDialog.
        dialogo.setTitle("Espere");

        dialogo.setLayout(new FlowLayout());
        dialogo.getContentPane().add(progressBar);

        dialogo.pack();
        // Se hace visible el JDialog.
        dialogo.setVisible(true);
        dialogo.setLocation(450, 300);

        BD = new BaseDatos();
        try {
            System.out.print("Conexion a la base de datos ");
            BD.conectarBD();
            System.out.println("realizada con éxito");

            System.out.println("Creacion de la base de datos ");
            BD.creaBD();
            System.out.println("realizada con éxito.");
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.err.println("incorrecta");
        }


        dialogo.setVisible(false);
        try {
            gc = new Cliente(BD);
            // Registramos el administrador
            gc.registrarCliente("admin", "admin", "", "", "", -1, "", "", "", "",
                    "", "", "", "", -1, "", "", "", "", "", -1, "");
            
            // Registramos Clientes
            gc.registrarCliente(
                    "554072",
                    "1234",
                    "Sergio",
                    "Gonzalez Martinez",
                    "sergio_goma22@hotmail.com",
                    976240150,
                    "Calle",
                    "Pascasio Escoriaza",
                    "8",
                    "6º",
                    "B",
                    "",
                    "Zaragoza",
                    "Zaragoza",
                    50010,
                    "Domiciliacion Bancaria",
                    "",
                    "",
                    "",
                    "Sergio Gonzalez Martinez",
                    0,
                    "20541234567890123456");
            gc.registrarCliente(
                    "578107",
                    "1234",
                    "Elena",
                    "Olivan Salvador",
                    "578107@unizar.es",
                    976328283,
                    "Avenida",
                    "Madrid",
                    "271",
                    "2º",
                    "Izda",
                    "2ª",
                    "Zaragoza",
                    "Zaragoza",
                    50017,
                    "Domiciliacion Bancaria",
                    "",
                    "",
                    "",
                    "Elena Olivan Salvador",
                    0,
                    "20851234567890123456");
            gc.registrarCliente(
                    "575285",
                    "1234",
                    "Sandra",
                    "Escudero Sanchez",
                    "575285@unizar.es",
                    976735145,
                    "Calle",
                    "Miguel de Unamuno",
                    "33",
                    "1º",
                    "A",
                    "",
                    "Zaragoza",
                    "Zaragoza",
                    50018,
                    "Domiciliacion Bancaria",
                    "",
                    "",
                    "",
                    "Sandra Escudero Sanchez",
                    0,
                    "20850987653920123234");
            gc.registrarCliente(
                    "574358",
                    "1234",
                    "Javier",
                    "Balbas Vaquero",
                    "574358@unizar.es",
                    976452464,
                    "Paseo",
                    "las Damas",
                    "34",
                    "3º",
                    "B",
                    "",
                    "Zaragoza",
                    "Zaragoza",
                    50006,
                    "Domiciliacion Bancaria",
                    "",
                    "",
                    "",
                    "Javier Balbas Vaquero",
                    0,
                    "20853423233920123234");
            
            Principal v = new Principal(BD);

        } catch (Exception e) {
            System.out.println("Ventana sin salir");
            e.printStackTrace();
            System.exit(0);
        }
    }
}
