package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import TADS.*;
import MSJ.*;
import BaseDatos.*;

public class Principal {

    JTextField jtfUsuario = new JTextField(10);
    JTextField jtfClave = new JPasswordField(10);
    Image logo = new ImageIcon(getClass().getResource("/Imagenes/Logo.png"))
            .getImage().getScaledInstance(220, 180, 10);
    ImageIcon logoIcon = new ImageIcon(logo);
    final JPanel contenido = new JPanel(new GridBagLayout());
    JLabel jlImagen = new JLabel(logoIcon, JLabel.CENTER);
    Contenido cont = new Contenido();
    Image logo2 = new ImageIcon(getClass().getResource("/Imagenes/telef.gif"))
            .getImage().getScaledInstance(72, 72, 10);
    Image logo3 = new ImageIcon(getClass().getResource("/Imagenes/tarifas.jpg"))
            .getImage().getScaledInstance(500, 500, 10);
    Image banner = new ImageIcon(getClass().getResource("/Imagenes/banner.gif"))
            .getImage().getScaledInstance(468, 60, 10);
    Image imtelefono = new ImageIcon(getClass().getResource("/Imagenes/fijo.gif"))
            .getImage().getScaledInstance(146, 81, 10);
    ImageIcon logoIcon3 = new ImageIcon(logo3);
    ImageIcon icon1 = new ImageIcon(logo2);
    ImageIcon bannericon = new ImageIcon(banner);
    ImageIcon telefono = new ImageIcon(imtelefono);
    JLabel fondo1 = new JLabel(icon1, JLabel.CENTER);
    JLabel iniciobanner = new JLabel(bannericon, JLabel.CENTER);
    JLabel fondo2 = new JLabel(logoIcon3, JLabel.CENTER);
    JLabel iniciofoto = new JLabel(telefono, JLabel.CENTER);
    JButton jbEntrar;
    JPanel ventana;
    JFrame telefonia;
    BaseDatos bd;
    Cliente gc;
    Admin ga;
    private static final int GAP = 5;
    private static final int GAPGRANDE = 20;

    public Principal(BaseDatos db) {
        telefonia = new JFrame("JES - Mobile");
        bd = db;
        ventana = new JPanel(new BorderLayout());
        JPanel titulo = new JPanel(new BorderLayout());
        JPanel menu = new JPanel(new GridBagLayout());
        jbEntrar = new JButton("Entrar");
        jbEntrar.addActionListener(new EscuchadorEntrar());
        JScrollPane scroll = new JScrollPane();

        gc = new Cliente(db);
        final AyudaGB posInicio = new AyudaGB();
        contenido.add(iniciobanner);
        contenido.add(new Gap(GAPGRANDE), posInicio.nextRow());
        contenido.add(iniciofoto, posInicio.nextRow());

        //Definimos las etiquetas del menu y su comportamiento
        JLabel jlInicio = new JLabel("Inicio");
        jlInicio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlInicio.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(iniciobanner);
                contenido.add(new Gap(GAPGRANDE), posInicio.nextRow());
                contenido.add(iniciofoto, posInicio.nextRow());
                contenido.updateUI();

            }
        });
        JLabel jlTarifas = new JLabel("Tarifas");
        jlTarifas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlTarifas.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(fondo2);
                contenido.updateUI();
            }
        });
        JLabel jlRegistrar = new JLabel("Registrarse como Cliente");
        jlRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlRegistrar.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(cont.registrarse(gc));
                contenido.updateUI();
            }
        });


        AyudaGB posMenu = new AyudaGB();
        AyudaGB posAcceder = new AyudaGB();

        menu.setBackground(Color.LIGHT_GRAY);

        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(jlInicio, posMenu.nextCol());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextCol());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(jlRegistrar, posMenu.nextCol());
        menu.add(new Gap(GAP), posMenu.nextCol());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(jlTarifas, posMenu.nextCol());
        menu.add(new Gap(GAP), posMenu.nextCol());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(fondo1, posMenu.nextRow().expandW());


        // Capturamos la pulsacion de la tecla enter y realizamos la misma funcion que el boton entrar
        jtfClave.addActionListener(new EscuchadorEntrar());

        JPanel acceder = new JPanel(new GridBagLayout());
        acceder.setBackground(Color.ORANGE);
        acceder.add(new Gap(GAP), posAcceder.nextRow());
        acceder.add(new Gap(GAP), posAcceder.nextRow());
        acceder.add(new JLabel("NIF"), posAcceder.nextCol());
        acceder.add(new Gap(GAP), posAcceder.nextCol());
        acceder.add(new Gap(GAP), posAcceder.nextRow());
        acceder.add(new Gap(GAP), posAcceder.nextRow());
        acceder.add(jtfUsuario, posAcceder.nextCol().expandW());
        acceder.add(new Gap(GAP), posAcceder.nextCol());
        acceder.add(new Gap(GAP), posAcceder.nextRow());
        acceder.add(new Gap(GAP), posAcceder.nextRow());
        acceder.add(new JLabel("Clave"), posAcceder.nextCol());
        acceder.add(new Gap(GAP), posAcceder.nextRow());
        acceder.add(new Gap(GAP), posAcceder.nextRow());
        acceder.add(jtfClave, posAcceder.nextCol().expandW());
        acceder.add(new Gap(GAP), posAcceder.nextRow());
        acceder.add(new Gap(GAP), posAcceder.nextRow());
        acceder.add(jbEntrar, posAcceder.nextCol());


        titulo.setBackground(Color.ORANGE);
        titulo.add(new Gap(GAP), BorderLayout.WEST);
        titulo.add(jlImagen, BorderLayout.WEST);

        titulo.add(acceder, BorderLayout.EAST);

        // Norte
        ventana.add(titulo, BorderLayout.NORTH);

        // Oeste
        ventana.add(menu, BorderLayout.WEST);

        // Centro
        contenido.setBackground(Color.WHITE);
        scroll.setViewportView(contenido);

        ventana.add(scroll, BorderLayout.CENTER);

        // Barras de scroll para ventana principal
        //scroll.setViewportView(ventana);

        telefonia.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        telefonia.setLocation(350, 0);
        telefonia.setContentPane(ventana);
        telefonia.pack();
        telefonia.setSize(800, 700);
        telefonia.setVisible(true);
    }

    class EscuchadorEntrar implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (jtfUsuario.getText().isEmpty() & jtfClave.getText().isEmpty()) {
                JOptionPane.showMessageDialog(telefonia,
                        "Error; Debe introducir un usuario y una contraseña.\n",
                        "Error al Registrarse",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                if (jtfUsuario.getText().equals("admin")) {
                    int v = gc.loginCliente(jtfUsuario.getText(), jtfClave.getText());
                    if (MSJ.OK == v) {
                        PrincipalAdmin ven = new PrincipalAdmin(telefonia, gc, ga, bd);
                    } else {
                        JOptionPane.showMessageDialog(telefonia, MSJ.Mensaje(v), "ERROR", JOptionPane.ERROR_MESSAGE);
                        jtfUsuario.addFocusListener(new FullSelectorListener());
                        jtfClave.setText("");
                    }
                } else {
                    int v = gc.loginCliente(jtfUsuario.getText(), jtfClave.getText());
                    if (MSJ.OK == v) {

                        //gc.facturar();
                        PrincipalUser ven = new PrincipalUser(telefonia, gc, bd);
                    } else {
                        JOptionPane.showMessageDialog(telefonia, MSJ.Mensaje(v), "ERROR", JOptionPane.ERROR_MESSAGE);
                        jtfUsuario.addFocusListener(new FullSelectorListener());
                        jtfClave.setText("");
                    }
                }
            }
        }
    }

    public class FullSelectorListener extends java.awt.event.FocusAdapter {

        @Override
        public void focusGained(java.awt.event.FocusEvent evt) {
            Object o = evt.getSource();
            if (o instanceof javax.swing.JTextField) {
                javax.swing.JTextField txt = (javax.swing.JTextField) o;
                txt.setSelectionStart(0);
                txt.setSelectionEnd(txt.getText().length());
            }
        }
    }
}
