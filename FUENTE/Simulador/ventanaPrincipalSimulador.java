package Simulador;

import BaseDatos.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import TADS.*;
import java.util.ArrayList;
import GUI.*;

public class ventanaPrincipalSimulador extends JFrame {

    JFrame tienda;
    JPanel ventana, norte, centro, jpLlamadas, jpSMS, jpMMS, jpDatos;
    JComboBox lstLineas = new JComboBox();
    JScrollPane lineas = new JScrollPane(lstLineas);
    JPanelOpciones op = new JPanelOpciones();
    private static final int GAP = 5;
    private static final int GAPGRANDE = 50;
    private static final int WINDOWBORDER = 10;
    JScrollPane jspLlamadas;
    JScrollPane jspSMS;
    JScrollPane jspDatos;
    JScrollPane jspMMS;
    BaseDatos bd;

    public ventanaPrincipalSimulador(BaseDatos DB) {
        final JTabbedPane paneles = new JTabbedPane();
        bd = DB;
        tienda = new JFrame("Simulador");
        ventana = new JPanel(new BorderLayout());
        norte = new JPanel(new GridBagLayout());
        norte.setBorder(BorderFactory.createEmptyBorder(WINDOWBORDER,
                WINDOWBORDER, WINDOWBORDER, WINDOWBORDER));//new JPanel(new FlowLayout());
        centro = new JPanel(new BorderLayout());
        Linea l = new Linea(bd);
        final GestorSimulador si = new GestorSimulador(bd);
        Image logo = new ImageIcon(getClass().getResource("/Imagenes/Logo.png")).getImage().getScaledInstance(100, 80, 10);
        ImageIcon logoIcon = new ImageIcon(logo);
        JPanel norte1 = new JPanel(new GridBagLayout());
        JPanel norte2 = new JPanel(new GridBagLayout());
        AyudaGB pos = new AyudaGB();

        ArrayList list = l.listarTodasLineasActivas();
        //Añadimos las lineas existentes
        int n = list.size();
        for (int i = 0; i < n; i++) {
            lstLineas.addItem(Integer.toString(((Linea_VO) list.get(i)).getNumtlf()));
        }
        if (n != 0) {
            jpLlamadas = (op.jpVOZ(Integer.parseInt(lstLineas.getSelectedItem().toString()), si));
            jpSMS = (op.jpSMS(Integer.parseInt(lstLineas.getSelectedItem().toString()), si));
            jpMMS = (op.jpMMS(Integer.parseInt(lstLineas.getSelectedItem().toString()), si));
            jpDatos = (op.jpDATOS(Integer.parseInt(lstLineas.getSelectedItem().toString()), si));

            jspLlamadas = new JScrollPane(jpLlamadas);
            jspSMS = new JScrollPane(jpSMS);
            jspMMS = new JScrollPane(jpMMS);
            jspDatos = new JScrollPane(jpDatos);

            jpSMS = new JPanel();
            jpLlamadas = new JPanel();
            jpDatos = new JPanel();
            jpMMS = new JPanel();
            lstLineas.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    paneles.removeAll();
                    jpLlamadas = (op.jpVOZ(Integer.parseInt(lstLineas.getSelectedItem().toString()), si));
                    jpSMS = (op.jpSMS(Integer.parseInt(lstLineas.getSelectedItem().toString()), si));
                    jpMMS = (op.jpMMS(Integer.parseInt(lstLineas.getSelectedItem().toString()), si));
                    jpDatos = (op.jpDATOS(Integer.parseInt(lstLineas.getSelectedItem().toString()), si));
                    jspLlamadas = new JScrollPane(jpLlamadas);
                    jspSMS = new JScrollPane(jpSMS);
                    jspMMS = new JScrollPane(jpMMS);
                    jspDatos = new JScrollPane(jpDatos);
                    paneles.addTab("Llamadas", jspLlamadas);
                    paneles.addTab("SMS", jspSMS);
                    paneles.addTab("MMS", jspMMS);
                    paneles.addTab("Datos", jspDatos);
                }
            });
        }
        else{
            lstLineas.addItem("No hay lineas activas");
        }
        lstLineas.setToolTipText("Seleccione la linea sobre la que desea realizar el cargo");
        norte1.add(new JLabel(logoIcon));

        norte2.add(new JLabel("Lineas de Clientes de JES-Mobile"), pos.nextRow());
        norte2.add(new Gap(GAP), pos.nextRow());
        norte2.add(lineas, pos.nextRow());

        norte.add(new Gap(GAP), pos.nextRow());
        norte.add(norte1, pos.nextRow());
        norte.add(new Gap(GAPGRANDE), pos.nextCol());
        norte.add(norte2, pos.nextCol());


        paneles.setVisible(true);
        paneles.addTab("Llamadas", jspLlamadas);
        paneles.addTab("SMS", jspSMS);
        paneles.addTab("MMS", jspMMS);
        paneles.addTab("Datos", jspDatos);
        centro.add(paneles);

        ventana.add(norte, BorderLayout.NORTH);
        ventana.add(centro, BorderLayout.CENTER);
        tienda.addWindowListener(new FrameListener());
        tienda.setLocation(450, 150);
        tienda.setContentPane(ventana);

        tienda.setSize(550, 400);
        tienda.setVisible(true);
    }

    class FrameListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            tienda.setVisible(false);
            Principal v = new Principal(bd);
        }
    }
}
