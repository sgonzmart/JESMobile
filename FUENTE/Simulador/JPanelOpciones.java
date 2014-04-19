/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Simulador;

import MSJ.MSJ;
import BaseDatos.Servicio_VO;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import GUI.*;

class JPanelOpciones {

    private static final int WINDOWBORDER = 10;
    private static final int BORDER = 1;  // Color border in pixels.
    private static final int GAP = 5;
    private static final Font DEFAULTFONT = new java.awt.Font("Comic Sans MS", 3, 15);

    public JPanel jpSMS(int num, GestorSimulador s) {
        final GestorSimulador si = s;
        final int origen = num;

        final JPanel jpborderS;

        final String sjtfnumdestinoSMS = "Teléfono del destinatario";
        final String sjtfano = "aaaa";
        final String sjtfmes = "mm";
        final String sjtfdia = "dd";
        final String sjtfhora = "hh";
        final String sjtfmin = "mm";
        final String sjtasms = "Escriba aqui el mensaje";

        JLabel jlfecha = new JLabel("Fecha del SMS:");
        JLabel jlnumdestino = new JLabel("Telefono destino:", JLabel.LEFT);
        JLabel jlsms = new JLabel("Mensaje SMS:");

        final JTextField jtfano = new JTextField(sjtfano, 6);
        final JTextField jtfmes = new JTextField(sjtfmes, 2);
        final JTextField jtfdia = new JTextField(sjtfdia, 2);
        final JTextField jtfhora = new JTextField(sjtfhora, 2);
        final JTextField jtfmin = new JTextField(sjtfmin, 2);
        final JTextArea jtasms = new JTextArea(sjtasms, 30, 20);
        final JTextField jtfnumdestinoSMS = new JTextField(sjtfnumdestinoSMS, 10);
        JButton jbenviar = new JButton("Enviar SMS");
        JButton jbteclado;
        JButton jbBorrar;

        // Añadimos un foco para seleccionar todo el texto
        jtfano.addFocusListener(new FullSelectorListener());
        jtfmes.addFocusListener(new FullSelectorListener());
        jtfdia.addFocusListener(new FullSelectorListener());
        jtfhora.addFocusListener(new FullSelectorListener());
        jtfmin.addFocusListener(new FullSelectorListener());
        jtfnumdestinoSMS.addFocusListener(new FullSelectorListener());

        JScrollPane jspsms = new JScrollPane(jtasms);
        JPanel jpTeclado = new JPanel(new GridBagLayout());
        JPanel jpsms = new JPanel(new GridBagLayout());
        JPanel jpSMS = new JPanel(new GridBagLayout());
        jpsms.setBorder(BorderFactory.createEmptyBorder(WINDOWBORDER,
                WINDOWBORDER, WINDOWBORDER, WINDOWBORDER));
        jtfnumdestinoSMS.setEditable(true);
        jtasms.setLineWrap(true);


        AyudaGB posTec = new AyudaGB();
        jpTeclado.add(new Gap(GAP), posTec.nextRow());
        jpTeclado.add(new Gap(GAP), posTec.nextCol());
        jpTeclado.add(new Gap(GAP), posTec.nextCol());
        jpTeclado.add(new Gap(GAP), posTec.nextCol());
        jbBorrar = new JButton("C");
        jbBorrar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                jtfnumdestinoSMS.setText("");
            }
        });
        jpTeclado.add(jbBorrar, posTec.nextCol());
        jpTeclado.add(new Gap(GAP), posTec.nextRow());
        jpTeclado.add(new Gap(GAP), posTec.nextCol());
        for (int i = 1; i < 13; i++) {
            String tecla = Integer.toString(i);
            if (i == 10) {
                tecla = "*";
            }
            if (i == 11) {
                tecla = "0";
            }
            if (i == 12) {
                tecla = "#";
            }
            jbteclado = new JButton(tecla);
            final String aux = tecla;
            jbteclado.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    if (jtfnumdestinoSMS.getText().equals(sjtfnumdestinoSMS)) {
                        jtfnumdestinoSMS.setText(aux);
                    } else {
                        jtfnumdestinoSMS.setText(jtfnumdestinoSMS.getText() + aux);
                    }
                }
            });
            jpTeclado.add(jbteclado, posTec.nextCol());
            if (i % 3 == 0) {
                jpTeclado.add(new Gap(GAP), posTec.nextRow());
                jpTeclado.add(new Gap(GAP), posTec.nextCol());
            }
        }

        AyudaGB pos = new AyudaGB();
        jpsms.add(jlnumdestino, pos);
        jpsms.add(new Gap(GAP), pos.nextCol());
        jpsms.add(jtfnumdestinoSMS, pos.nextCol().expandW().width(9));

        jpsms.add(new Gap(GAP), pos.nextRow().width(11).expandW());

        jpsms.add(jlfecha, pos.nextRow().expandW());
        jpsms.add(new Gap(GAP), pos.nextCol());
        jtfano.setMinimumSize(new Dimension(20, jtfano.getHeight()));
        jpsms.add(jtfano, pos.nextCol().expandW());
        jpsms.add(new JLabel("/ "), pos.nextCol());
        jpsms.add(jtfmes, pos.nextCol().expandW());
        jpsms.add(new JLabel("/ "), pos.nextCol());
        jpsms.add(jtfdia, pos.nextCol().expandW());
        jpsms.add(new JLabel("  "), pos.nextCol());
        jpsms.add(jtfhora, pos.nextCol().expandW());
        jpsms.add(new JLabel(": "), pos.nextCol());
        jpsms.add(jtfmin, pos.nextCol().expandW());

        jpsms.add(new Gap(GAP), pos.nextRow().width(11).expandW());

        jpsms.add(jlsms, pos.nextRow().width(11).expandW().align(AyudaGB.WEST));

        jpsms.add(new Gap(GAP), pos.nextRow().width(11).expandW());

        jpsms.add(jspsms, pos.nextRow().width(11).height(2).expandW().expandH());

        jpsms.add(new Gap(GAP), pos.nextRow().nextRow().width(11).expandW());

        jpsms.add(jbenviar, pos.nextRow().width(11).align(AyudaGB.EAST).expandW());

        jpsms.setPreferredSize(new Dimension(350, 180));

        jpborderS = new JPanel();
        jpborderS.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                "Envio de SMS",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        AyudaGB posSMS = new AyudaGB();
        jpSMS.add(new Gap(GAP), posSMS.nextRow());
        jpSMS.add(jpTeclado, posSMS.nextRow().height(4));
        jpSMS.add(jpsms, posSMS.nextCol());

        jpborderS.add(jpSMS);

        jpborderS.setPreferredSize(new Dimension(300, 220));

        final Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        jtfano.setText(Integer.toString(cal.get(Calendar.YEAR)));
        jtfmes.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
        jtfdia.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
        jtfhora.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
        jtfmin.setText(Integer.toString(cal.get(Calendar.MINUTE)));
        jtfano.setEditable(true);
        jtfmes.setEditable(true);
        jtfdia.setEditable(true);
        jtfhora.setEditable(true);
        jtfmin.setEditable(true);

        jbenviar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                // Comprobar datos correctos:
                String cad = ((jtfnumdestinoSMS.getText().startsWith("+"))
                        ? jtfnumdestinoSMS.getText().substring(1) : jtfnumdestinoSMS.getText());
                try {
                    if ((Integer.parseInt(cad) < 0)
                            || (Integer.parseInt(jtfano.getText()) < 0)
                            || (Integer.parseInt(jtfmes.getText()) < 0)
                            || (Integer.parseInt(jtfdia.getText()) < 0)
                            || (Integer.parseInt(jtfhora.getText()) < 0)
                            || (Integer.parseInt(jtfmin.getText()) < 0)) {
                        // Lanzamos una NumberFormatException
                        Integer.parseInt("NumberFormatException");
                    }
                    String fecha = jtfano.getText() + "/";
                    if (Integer.parseInt(jtfmes.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfmes.getText() + "/";
                    if (Integer.parseInt(jtfdia.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfdia.getText() + "/";

                    if (Integer.parseInt(jtfhora.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfhora.getText() + "/";
                    if (Integer.parseInt(jtfmin.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfmin.getText();
                    //registrarServicio (int nselec,int codserv, int numdest, String fech,int dur,int d){
                    int v = si.registrarServicio(origen, Servicio_VO.SMS, Integer.parseInt(jtfnumdestinoSMS.getText()), fecha, -1, -1);
                    jtfnumdestinoSMS.setText("");
                    JOptionPane.showMessageDialog(jpborderS,
                            MSJ.Mensaje(v), "App - SMS enviado",
                            JOptionPane.INFORMATION_MESSAGE);
                    jtasms.setText("");
                    cal.setTime(new Date());
                    jtfano.setText(Integer.toString(cal.get(Calendar.YEAR)));
                    jtfmes.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
                    jtfdia.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
                    jtfhora.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
                    jtfmin.setText(Integer.toString(cal.get(Calendar.MINUTE)));

                } catch (NumberFormatException e) {
                    // Error de datos!
                    JOptionPane.showMessageDialog(jpborderS,
                            "Los datos introducidos no son correctos", "App - Error",
                            JOptionPane.ERROR_MESSAGE);
                    cal.setTime(new Date());
                    jtfano.setText(Integer.toString(cal.get(Calendar.YEAR)));
                    jtfmes.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
                    jtfdia.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
                    jtfhora.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
                    jtfmin.setText(Integer.toString(cal.get(Calendar.MINUTE)));
                }
            }
        });

        jtfnumdestinoSMS.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfnumdestinoSMS.getText().equals(sjtfnumdestinoSMS)) {
                    jtfnumdestinoSMS.setEditable(true);
                    jtfnumdestinoSMS.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfnumdestinoSMS.getText().equals("")) {
                    jtfnumdestinoSMS.setEditable(false);
                    jtfnumdestinoSMS.setText(sjtfnumdestinoSMS);
                }
            }
        });

        jtasms.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtasms.getText().equals(sjtasms)) {
                    jtasms.setEditable(true);
                    jtasms.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
            }
        });
        return (jpborderS);
    }

    public JPanel jpMMS(int num, GestorSimulador s) {
        final int origen = num;
        final GestorSimulador si = s;
        final JPanel jpborderM;

        final String sjtamms = "Escriba aqui el mensaje";
        final String sjtfdatos = "Datos (en KB)";

        JLabel jlnumdestino = new JLabel("Telefono destino:", JLabel.LEFT);
        JLabel jldatos = new JLabel("Cantidad de Kb:", JLabel.LEFT);
        JLabel jlfecha = new JLabel("Fecha del MMS:");

        final String sjtfano = "aaaa";
        final String sjtfmes = "mm";
        final String sjtfdia = "dd";
        final String sjtfhora = "hh";
        final String sjtfmin = "mm";
        final String sjtfnumdestinoMMS = "Teléfono del destinatario";
        final JTextField jtfano = new JTextField(sjtfano, 6);
        final JTextField jtfmes = new JTextField(sjtfmes, 2);
        final JTextField jtfdia = new JTextField(sjtfdia, 2);
        final JTextField jtfhora = new JTextField(sjtfhora, 2);
        final JTextField jtfmin = new JTextField(sjtfmin, 2);
        final JTextField jtfdatos = new JTextField(sjtfdatos, 10);
        final JTextArea jtamms = new JTextArea(sjtamms, 30, 20);
        final JTextField jtfnumdestinoMMS = new JTextField(sjtfnumdestinoMMS, 10);

        JScrollPane jspmms = new JScrollPane(jtamms);

        // Añadimos un foco para seleccionar todo el texto
        jtfano.addFocusListener(new FullSelectorListener());
        jtfmes.addFocusListener(new FullSelectorListener());
        jtfdia.addFocusListener(new FullSelectorListener());
        jtfhora.addFocusListener(new FullSelectorListener());
        jtfmin.addFocusListener(new FullSelectorListener());
        jtfnumdestinoMMS.addFocusListener(new FullSelectorListener());
        jtfdatos.addFocusListener(new FullSelectorListener());

        JLabel jlmms = new JLabel("Mensaje MMS:");
        JButton jbenviar = new JButton("Enviar MMS");
        JButton jbteclado;
        JButton jbBorrar;
        JPanel jpTeclado = new JPanel(new GridBagLayout());
        JPanel jpMMS = new JPanel(new GridBagLayout());

        JPanel jpmms = new JPanel(new GridBagLayout());
        jpmms.setBorder(BorderFactory.createEmptyBorder(WINDOWBORDER,
                WINDOWBORDER, WINDOWBORDER, WINDOWBORDER));
        jtfnumdestinoMMS.setEditable(true);
        jtfdatos.setEditable(false);
        jtamms.setLineWrap(true);

        AyudaGB posTec = new AyudaGB();
        jpTeclado.add(new Gap(GAP), posTec.nextRow());
        jpTeclado.add(new Gap(GAP), posTec.nextCol());
        jpTeclado.add(new Gap(GAP), posTec.nextCol());
        jpTeclado.add(new Gap(GAP), posTec.nextCol());
        jbBorrar = new JButton("C");

        jbBorrar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                jtfnumdestinoMMS.setText("");
            }
        });
        jpTeclado.add(jbBorrar, posTec.nextCol());
        jpTeclado.add(new Gap(GAP), posTec.nextRow());
        jpTeclado.add(new Gap(GAP), posTec.nextCol());
        for (int i = 1; i < 13; i++) {
            String tecla = Integer.toString(i);
            if (i == 10) {
                tecla = "*";
            }
            if (i == 11) {
                tecla = "0";
            }
            if (i == 12) {
                tecla = "#";
            }
            jbteclado = new JButton(tecla);
            final String aux = tecla;

            jbteclado.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    if (jtfnumdestinoMMS.getText().equals(sjtfnumdestinoMMS)) {
                        jtfnumdestinoMMS.setText(aux);
                    } else {
                        jtfnumdestinoMMS.setText(jtfnumdestinoMMS.getText() + aux);
                    }
                }
            });
            jpTeclado.add(jbteclado, posTec.nextCol());
            if (i % 3 == 0) {
                jpTeclado.add(new Gap(GAP), posTec.nextRow());
                jpTeclado.add(new Gap(GAP), posTec.nextCol());
            }
        }
        AyudaGB pos = new AyudaGB();

        jpmms.add(jlnumdestino, pos);
        jpmms.add(new Gap(GAP), pos.nextCol());
        jpmms.add(jtfnumdestinoMMS, pos.nextCol().width(9).expandW());
        jpmms.add(new Gap(GAP), pos.nextRow().width(3).expandW());

        jpmms.add(jlfecha, pos.nextRow().expandW());
        jpmms.add(new Gap(GAP), pos.nextCol());
        jtfano.setMinimumSize(new Dimension(20, jtfano.getHeight()));
        jpmms.add(jtfano, pos.nextCol().expandW());
        jpmms.add(new JLabel("/ "), pos.nextCol());
        jpmms.add(jtfmes, pos.nextCol().expandW());
        jpmms.add(new JLabel("/ "), pos.nextCol());
        jpmms.add(jtfdia, pos.nextCol().expandW());
        jpmms.add(new JLabel("  "), pos.nextCol());
        jpmms.add(jtfhora, pos.nextCol().expandW());
        jpmms.add(new JLabel(": "), pos.nextCol());
        jpmms.add(jtfmin, pos.nextCol().expandW());
        jpmms.add(new Gap(GAP), pos.nextRow().width(3).expandW());

        jpmms.add(jldatos, pos.nextRow());
        jpmms.add(new Gap(GAP), pos.nextCol());
        jpmms.add(jtfdatos, pos.nextCol().width(9).expandW());
        jpmms.add(new Gap(GAP), pos.nextRow().width(3).expandW());

        jpmms.add(jlmms, pos.nextRow());
        jpmms.add(new Gap(GAP), pos.nextCol().width(2).expandW());

        jpmms.add(new Gap(GAP), pos.nextRow().width(3));

        jpmms.add(jspmms, pos.nextRow().width(12).height(2).expandW().expandH());

        jpmms.add(new Gap(GAP), pos.nextRow().nextRow().width(3).expandW());

        jpmms.add(new Gap(GAP), pos.nextRow());
        jpmms.add(new Gap(GAP), pos.nextRow().width(4).align(AyudaGB.WEST));
        jpmms.add(new Gap(GAP), pos.nextCol());
        jpmms.add(jbenviar, pos.nextCol().nextCol().width(9).align(AyudaGB.EAST));
        final Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        jtfano.setText(Integer.toString(cal.get(Calendar.YEAR)));
        jtfmes.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
        jtfdia.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
        jtfhora.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
        jtfmin.setText(Integer.toString(cal.get(Calendar.MINUTE)));
        jtfano.setEditable(true);
        jtfmes.setEditable(true);
        jtfdia.setEditable(true);
        jtfhora.setEditable(true);
        jtfmin.setEditable(true);

        jpmms.setPreferredSize(new Dimension(350, 180));

        jpborderM = new JPanel();
        jpborderM.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                "Envio de MMS",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));
        AyudaGB posMMS = new AyudaGB();
        jpMMS.add(new Gap(GAP), posMMS.nextRow());
        jpMMS.add(jpTeclado, posMMS.nextRow().height(4));
        jpMMS.add(jpmms, posMMS.nextCol());


        jpborderM.add(jpMMS);

        //jpborder.setPreferredSize(new Dimension(300, 220));
        jpborderM.setPreferredSize(new Dimension(300, 220));

        jbenviar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                // Comprobar datos correctos:
                String cad = ((jtfnumdestinoMMS.getText().startsWith("+"))
                        ? jtfnumdestinoMMS.getText().substring(1) : jtfnumdestinoMMS.getText());

                try {
                    if (Integer.parseInt(cad) < 0
                            || (Integer.parseInt(jtfdatos.getText()) < 0)) {
                        // Lanzamos una NumberFormatException
                        Integer.parseInt("NumberFormatException");
                    }//registrarServicio (int nselec,int codserv, int numdest, String fech,int dur,int d){
                    String fecha = jtfano.getText() + "/";
                    if (Integer.parseInt(jtfmes.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfmes.getText() + "/";
                    if (Integer.parseInt(jtfdia.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfdia.getText() + "/";

                    if (Integer.parseInt(jtfhora.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfhora.getText() + "/";
                    if (Integer.parseInt(jtfmin.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfmin.getText();
                    int v = si.registrarServicio(origen, Servicio_VO.MMS,
                            Integer.parseInt(jtfnumdestinoMMS.getText()),
                            fecha, -1, Integer.parseInt(jtfdatos.getText()));
                    jtfnumdestinoMMS.setText("");
                    cal.setTime(new Date());
                    jtfano.setText(Integer.toString(cal.get(Calendar.YEAR)));
                    jtfmes.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
                    jtfdia.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
                    jtfhora.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
                    jtfmin.setText(Integer.toString(cal.get(Calendar.MINUTE)));
                    JOptionPane.showMessageDialog(jpborderM,
                            MSJ.Mensaje(v), "App - MMS enviado",
                            JOptionPane.INFORMATION_MESSAGE);
                    jtamms.setText("");
                } catch (NumberFormatException e) {
                    // Error de datos!
                    JOptionPane.showMessageDialog(jpborderM,
                            "Los datos introducidos no son correctos", "App - Error",
                            JOptionPane.ERROR_MESSAGE);
                    cal.setTime(new Date());
                    jtfano.setText(Integer.toString(cal.get(Calendar.YEAR)));
                    jtfmes.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
                    jtfdia.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
                    jtfhora.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
                    jtfmin.setText(Integer.toString(cal.get(Calendar.MINUTE)));
                }
            }
        });

        jtfnumdestinoMMS.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfnumdestinoMMS.getText().equals(sjtfnumdestinoMMS)) {
                    jtfnumdestinoMMS.setEditable(true);
                    jtfnumdestinoMMS.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfnumdestinoMMS.getText().equals("")) {
                    jtfnumdestinoMMS.setEditable(false);
                    jtfnumdestinoMMS.setText(sjtfnumdestinoMMS);
                }
            }
        });

        jtamms.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtamms.getText().equals(sjtamms)) {
                    jtamms.setEditable(true);
                    jtamms.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
            }
        });

        jtfdatos.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfdatos.getText().equals(sjtfdatos)) {
                    jtfdatos.setEditable(true);
                    jtfdatos.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfdatos.getText().equals("")) {
                    jtfdatos.setEditable(false);
                    jtfdatos.setText(sjtfdatos);
                }
            }
        });

        return (jpborderM);
    }

    public JPanel jpVOZ(int num, GestorSimulador s) {

        final int origen = num;
        final GestorSimulador si = s;
        final String sjtfano = "aaaa";
        final String sjtfmes = "mm";
        final String sjtfdia = "dd";
        final String sjtfhora = "hh";
        final String sjtfmin = "mm";
        final String sjtfduracion = "Duracion (en segundos)";
        final String sjtfnumdestinoVOZ = "Teléfono del destinatario";


        JLabel jlfecha = new JLabel("Fecha de la llamada:");
        final JTextField jtfano = new JTextField(sjtfano, 5);
        final JTextField jtfmes = new JTextField(sjtfmes, 2);
        final JTextField jtfdia = new JTextField(sjtfdia, 2);
        final JTextField jtfhora = new JTextField(sjtfhora, 2);
        final JTextField jtfmin = new JTextField(sjtfmin, 2);
        final JTextField jtfnumdestinoVOZ = new JTextField(sjtfnumdestinoVOZ, 10);

        JLabel jlduracion = new JLabel("Duracion(s) de la llamada:");
        final JTextField jtfduracion = new JTextField(sjtfduracion, 10);
        JButton jbllamar = new JButton("Realizar Llamada");
        JButton jbteclado;
        JButton jbBorrar;
        JPanel jpTeclado = new JPanel(new GridBagLayout());
        JPanel jpVOZ = new JPanel(new GridBagLayout());
        final JPanel jpborderL;
        JLabel jlnumdestino = new JLabel("Telefono destino:", JLabel.LEFT);

        // Añadimos un foco para seleccionar todo el texto
        jtfano.addFocusListener(new FullSelectorListener());
        jtfmes.addFocusListener(new FullSelectorListener());
        jtfdia.addFocusListener(new FullSelectorListener());
        jtfhora.addFocusListener(new FullSelectorListener());
        jtfmin.addFocusListener(new FullSelectorListener());
        jtfnumdestinoVOZ.addFocusListener(new FullSelectorListener());
        jtfduracion.addFocusListener(new FullSelectorListener());

        JPanel jpvoz = new JPanel(new GridBagLayout());
        jpvoz.setBorder(BorderFactory.createEmptyBorder(WINDOWBORDER,
                WINDOWBORDER, WINDOWBORDER, WINDOWBORDER));
        jtfano.setEditable(true);
        jtfmes.setEditable(true);
        jtfdia.setEditable(true);
        jtfhora.setEditable(true);
        jtfmin.setEditable(true);
        final Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        jtfano.setText(Integer.toString(cal.get(Calendar.YEAR)));
        jtfmes.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
        jtfdia.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
        jtfhora.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
        jtfmin.setText(Integer.toString(cal.get(Calendar.MINUTE)));

        jtfnumdestinoVOZ.setEditable(true);
        jtfduracion.setEditable(false);

        AyudaGB posTec = new AyudaGB();
        jpTeclado.add(new Gap(GAP), posTec.nextRow());
        jpTeclado.add(new Gap(GAP), posTec.nextCol());
        jpTeclado.add(new Gap(GAP), posTec.nextCol());
        jpTeclado.add(new Gap(GAP), posTec.nextCol());
        jbBorrar = new JButton("C");

        jbBorrar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                jtfnumdestinoVOZ.setText("");
            }
        });
        jpTeclado.add(jbBorrar, posTec.nextCol());
        jpTeclado.add(new Gap(GAP), posTec.nextRow());
        jpTeclado.add(new Gap(GAP), posTec.nextCol());
        for (int i = 1; i < 13; i++) {
            String tecla = Integer.toString(i);
            if (i == 10) {
                tecla = "*";
            }
            if (i == 11) {
                tecla = "0";
            }
            if (i == 12) {
                tecla = "#";
            }
            jbteclado = new JButton(tecla);
            final String aux = tecla;

            jbteclado.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    if (jtfnumdestinoVOZ.getText().equals(sjtfnumdestinoVOZ)) {
                        jtfnumdestinoVOZ.setText(aux);
                    } else {
                        jtfnumdestinoVOZ.setText(jtfnumdestinoVOZ.getText() + aux);
                    }
                }
            });
            jpTeclado.add(jbteclado, posTec.nextCol());
            //  norte.add(new Gap(GAP), pos.nextCol());
            if (i % 3 == 0) {

                jpTeclado.add(new Gap(GAP), posTec.nextRow());
                jpTeclado.add(new Gap(GAP), posTec.nextCol());

            }
        }
        AyudaGB pos = new AyudaGB();
        jpvoz.add(jlnumdestino, pos);
        jpvoz.add(new Gap(GAP), pos);
        jpvoz.add(jtfnumdestinoVOZ, pos.nextCol().expandW().width(10));
        jpvoz.add(new Gap(GAP), pos.nextRow().width(11).expandW());
        jpvoz.add(jlfecha, pos.nextRow().expandW());
        jpvoz.add(new Gap(GAP), pos.nextCol());
        jtfano.setMinimumSize(new Dimension(38, jtfano.getHeight()));
        jpvoz.add(jtfano, pos.nextCol().expandW());
        jpvoz.add(new JLabel("/ "), pos.nextCol());
        jpvoz.add(jtfmes, pos.nextCol().expandW());
        jpvoz.add(new JLabel("/ "), pos.nextCol());
        jpvoz.add(jtfdia, pos.nextCol().expandW());
        jpvoz.add(new JLabel("  "), pos.nextCol());
        jpvoz.add(jtfhora, pos.nextCol().expandW());
        jpvoz.add(new JLabel(": "), pos.nextCol());
        jpvoz.add(jtfmin, pos.nextCol().expandW());
        jpvoz.add(new Gap(GAP), pos.nextRow().width(11).expandW());
        jpvoz.add(jlduracion, pos.nextRow());
        jpvoz.add(new Gap(GAP), pos.nextCol());
        jpvoz.add(jtfduracion, pos.nextCol().expandW().width(9).expandW());
        jpvoz.add(new Gap(GAP), pos.nextRow().width(11).expandW());
        jpvoz.add(new Gap(GAP), pos.nextRow().align(AyudaGB.WEST));
        jpvoz.add(new Gap(GAP), pos.nextCol());
        jpvoz.add(jbllamar, pos.nextCol().nextCol().align(AyudaGB.EAST).width(11).expandW());

        jpvoz.setPreferredSize(new Dimension(350, 180));

        AyudaGB posVOZ = new AyudaGB();
        jpVOZ.add(new Gap(GAP), posVOZ.nextRow());
        jpVOZ.add(jpTeclado, posVOZ.nextRow().height(4));
        jpVOZ.add(jpvoz, posVOZ.nextCol());
        jpborderL = new JPanel();
        jpborderL.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                "Llamada de voz",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));
        jpborderL.add(jpVOZ);

        jpborderL.setPreferredSize(new Dimension(340, 220));

        jbllamar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                // Comprobar datos correctos:
                String cad = ((jtfnumdestinoVOZ.getText().startsWith("+"))
                        ? jtfnumdestinoVOZ.getText().substring(1) : jtfnumdestinoVOZ.getText());
                try {
                    if ((Integer.parseInt(cad) < 0)
                            || (Integer.parseInt(jtfano.getText()) < 0)
                            || (Integer.parseInt(jtfmes.getText()) < 0)
                            || (Integer.parseInt(jtfdia.getText()) < 0)
                            || (Integer.parseInt(jtfhora.getText()) < 0)
                            || (Integer.parseInt(jtfmin.getText()) < 0)
                            || (Integer.parseInt(jtfduracion.getText()) < 0)) {
                        // Lanzamos una NumberFormatException
                        Integer.parseInt("NumberFormatException");
                    }//registrarServicio (int nselec,int codserv, int numdest, String fech,int dur,int d){
                    String fecha = jtfano.getText() + "/";
                    if (Integer.parseInt(jtfmes.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfmes.getText() + "/";
                    if (Integer.parseInt(jtfdia.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfdia.getText() + "/";

                    if (Integer.parseInt(jtfhora.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfhora.getText() + "/";
                    if (Integer.parseInt(jtfmin.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfmin.getText();
                    int v = si.registrarServicio(origen, Servicio_VO.VOZ, Integer.parseInt(jtfnumdestinoVOZ.getText()), fecha, Integer.parseInt(jtfduracion.getText()), -1);
                    jtfnumdestinoVOZ.setText("");
                    if (v != MSJ.OK) {
                        JOptionPane.showMessageDialog(jpborderL,
                                MSJ.Mensaje(v), "App - Llamada incorrecta",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(jpborderL,
                                "Llamada a " + jtfnumdestinoVOZ.getText() + " realizada",
                                "App - Llamada realizada", JOptionPane.INFORMATION_MESSAGE);
                    }
                    cal.setTime(new Date());
                    jtfano.setText(Integer.toString(cal.get(Calendar.YEAR)));
                    jtfmes.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
                    jtfdia.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
                    jtfhora.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
                    jtfmin.setText(Integer.toString(cal.get(Calendar.MINUTE)));
                    jtfduracion.setText(sjtfduracion);

                } catch (NumberFormatException e) {
                    // Error de datos!
                    JOptionPane.showMessageDialog(jpborderL,
                            "Los datos introducidos no son correctos", "App - Error",
                            JOptionPane.ERROR_MESSAGE);
                    Calendar cal = new GregorianCalendar();
                    cal.setTime(new Date());
                    jtfano.setText(Integer.toString(cal.get(Calendar.YEAR)));
                    jtfmes.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
                    jtfdia.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
                    jtfhora.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
                    jtfmin.setText(Integer.toString(cal.get(Calendar.MINUTE)));
                    jtfnumdestinoVOZ.setText("");
                    jtfduracion.setText("");
                }
            }
        });

        jtfano.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfano.getText().equals(sjtfano)) {
                    jtfano.setEditable(true);
                    jtfano.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfano.getText().equals("")) {
                    jtfano.setEditable(false);
                    jtfano.setText(sjtfano);
                }
            }
        });

        jtfmes.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfmes.getText().equals(sjtfmes)) {
                    jtfmes.setEditable(true);
                    jtfmes.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfmes.getText().equals("")) {
                    jtfmes.setEditable(false);
                    jtfmes.setText(sjtfmes);
                }
            }
        });

        jtfdia.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfdia.getText().equals(sjtfdia)) {
                    jtfdia.setEditable(true);
                    jtfdia.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfdia.getText().equals("")) {
                    jtfdia.setEditable(false);
                    jtfdia.setText(sjtfdia);
                }
            }
        });

        jtfhora.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfhora.getText().equals(sjtfhora)) {
                    jtfhora.setEditable(true);
                    jtfhora.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfhora.getText().equals("")) {
                    jtfhora.setEditable(false);
                    jtfhora.setText(sjtfhora);
                }
            }
        });

        jtfmin.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfmin.getText().equals(sjtfmin)) {
                    jtfmin.setEditable(true);
                    jtfmin.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfmin.getText().equals("")) {
                    jtfmin.setEditable(false);
                    jtfmin.setText(sjtfmin);
                }
            }
        });

        jtfnumdestinoVOZ.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfnumdestinoVOZ.getText().equals(sjtfnumdestinoVOZ)) {
                    jtfnumdestinoVOZ.setEditable(true);
                    jtfnumdestinoVOZ.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfnumdestinoVOZ.getText().equals("")) {
                    jtfnumdestinoVOZ.setEditable(false);
                    jtfnumdestinoVOZ.setText(sjtfnumdestinoVOZ);
                }
            }
        });

        jtfduracion.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfduracion.getText().equals(sjtfduracion)) {
                    jtfduracion.setEditable(true);
                    jtfduracion.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfduracion.getText().equals("")) {
                    jtfduracion.setEditable(false);
                    jtfduracion.setText(sjtfduracion);
                }
            }
        });

        return (jpborderL);
    }

    public JPanel jpDATOS(int num, GestorSimulador s) {
        final int origen = num;
        final GestorSimulador si = s;
        final String sjtfano = "aaaa";
        final String sjtfmes = "mm";
        final String sjtfdia = "dd";
        final String sjtfhora = "hh";
        final String sjtfmin = "mm";
        final String sjtfdatos = "Datos (en KB)";
        final JPanel jpborderD;

        JLabel jlfecha = new JLabel("Fecha de la conexion:");
        JLabel jldatos = new JLabel("Datos transmitidos");
        final JTextField jtfano = new JTextField(sjtfano, 4);
        final JTextField jtfmes = new JTextField(sjtfmes, 2);
        final JTextField jtfdia = new JTextField(sjtfdia, 2);
        final JTextField jtfhora = new JTextField(sjtfhora, 2);
        final JTextField jtfmin = new JTextField(sjtfmin, 2);
        final JTextField jtfdatos = new JTextField(sjtfdatos, 10);
        JButton jbconectar = new JButton("Realizar Conexion");

        // Añadimos un foco para seleccionar todo el texto
        jtfano.addFocusListener(new FullSelectorListener());
        jtfmes.addFocusListener(new FullSelectorListener());
        jtfdia.addFocusListener(new FullSelectorListener());
        jtfhora.addFocusListener(new FullSelectorListener());
        jtfmin.addFocusListener(new FullSelectorListener());
        jtfdatos.addFocusListener(new FullSelectorListener());

        final JPanel jpdatos = new JPanel(new GridBagLayout());
        jpdatos.setBorder(BorderFactory.createEmptyBorder(WINDOWBORDER,
                WINDOWBORDER, WINDOWBORDER, WINDOWBORDER));

        AyudaGB pos = new AyudaGB();

        jpdatos.add(jldatos, pos);
        jpdatos.add(new Gap(GAP), pos.nextCol());
        jpdatos.add(jtfdatos, pos.nextCol().expandW().width(9));
        jpdatos.add(new Gap(GAP), pos.nextRow().width(11).expandW());
        jpdatos.add(jlfecha, pos.nextRow().expandW());
        jpdatos.add(new Gap(GAP), pos.nextCol());
        jtfano.setMinimumSize(new Dimension(38, jtfano.getHeight()));
        jpdatos.add(jtfano, pos.nextCol().expandW());
        jpdatos.add(new JLabel("/ "), pos.nextCol());
        jpdatos.add(jtfmes, pos.nextCol().expandW());
        jpdatos.add(new JLabel("/ "), pos.nextCol());
        jpdatos.add(jtfdia, pos.nextCol().expandW());
        jpdatos.add(new JLabel("  "), pos.nextCol());
        jpdatos.add(jtfhora, pos.nextCol().expandW());
        jpdatos.add(new JLabel(": "), pos.nextCol());
        jpdatos.add(jtfmin, pos.nextCol().expandW());
        jpdatos.add(new Gap(GAP), pos.nextRow().width(11).expandW());
        jpdatos.add(new Gap(GAP), pos.nextRow().align(AyudaGB.WEST));
        jpdatos.add(new Gap(GAP), pos.nextCol());
        jpdatos.add(jbconectar, pos.nextCol().nextCol().align(AyudaGB.EAST).width(11).expandW());
        jpdatos.setPreferredSize(new Dimension(350, 180));

        jpborderD = new JPanel();
        jpborderD.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                "Conexion de datos",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));
        jpborderD.add(jpdatos);

        jpborderD.setPreferredSize(new Dimension(340, 220));
        final Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        jtfano.setText(Integer.toString(cal.get(Calendar.YEAR)));
        jtfmes.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
        jtfdia.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
        jtfhora.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
        jtfmin.setText(Integer.toString(cal.get(Calendar.MINUTE)));
        jtfano.setEditable(true);
        jtfmes.setEditable(true);
        jtfdia.setEditable(true);
        jtfhora.setEditable(true);
        jtfmin.setEditable(true);

        jbconectar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                // Comprobar datos correctos:
                try {
                    if ((Integer.parseInt(jtfano.getText()) < 0)
                            || (Integer.parseInt(jtfmes.getText()) < 0)
                            || (Integer.parseInt(jtfdia.getText()) < 0)
                            || (Integer.parseInt(jtfhora.getText()) < 0)
                            || (Integer.parseInt(jtfmin.getText()) < 0)
                            || (Integer.parseInt(jtfdatos.getText()) < 0)) {
                        // Lanzamos una NumberFormatException
                        Integer.parseInt("NumberFormatException");
                    }
                    //int nselec,int codserv, int numdest, String fech,int dur,int d
                    String fecha = jtfano.getText() + "/";
                    if (Integer.parseInt(jtfmes.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfmes.getText() + "/";
                    if (Integer.parseInt(jtfdia.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfdia.getText() + "/";

                    if (Integer.parseInt(jtfhora.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfhora.getText() + "/";
                    if (Integer.parseInt(jtfmin.getText()) < 9) {
                        fecha = fecha + "0";
                    }
                    fecha = fecha + jtfmin.getText();
                    int v = si.registrarServicio(origen, Servicio_VO.DATOS, -1, fecha, -1, Integer.parseInt(jtfdatos.getText()));
                    JOptionPane.showMessageDialog(jpborderD,
                            MSJ.Mensaje(v), "App - Conexion realizada",
                            JOptionPane.INFORMATION_MESSAGE);
                    cal.setTime(new Date());
                    jtfano.setText(Integer.toString(cal.get(Calendar.YEAR)));
                    jtfmes.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
                    jtfdia.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
                    jtfhora.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
                    jtfmin.setText(Integer.toString(cal.get(Calendar.MINUTE)));
                    jtfdatos.setText(sjtfdatos);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(jpborderD,
                            "Los datos introducidos no son correctos", "App - Error",
                            JOptionPane.ERROR_MESSAGE);
                    cal.setTime(new Date());
                    jtfano.setText(Integer.toString(cal.get(Calendar.YEAR)));
                    jtfmes.setText(Integer.toString(cal.get(Calendar.MONTH) + 1));
                    jtfdia.setText(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
                    jtfhora.setText(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
                    jtfmin.setText(Integer.toString(cal.get(Calendar.MINUTE)));
                }
            }
        });

        jtfdatos.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfdatos.getText().equals(sjtfdatos)) {
                    jtfdatos.setEditable(true);
                    jtfdatos.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfdatos.getText().equals("")) {
                    jtfdatos.setEditable(false);
                    jtfdatos.setText(sjtfdatos);
                }
            }
        });

        jtfano.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfano.getText().equals(sjtfano)) {
                    jtfano.setEditable(true);
                    jtfano.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfano.getText().equals("")) {
                    jtfano.setEditable(false);
                    jtfano.setText(sjtfano);
                }
            }
        });

        jtfmes.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfmes.getText().equals(sjtfmes)) {
                    jtfmes.setEditable(true);
                    jtfmes.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfmes.getText().equals("")) {
                    jtfmes.setEditable(false);
                    jtfmes.setText(sjtfmes);
                }
            }
        });

        jtfdia.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfdia.getText().equals(sjtfdia)) {
                    jtfdia.setEditable(true);
                    jtfdia.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfdia.getText().equals("")) {
                    jtfdia.setEditable(false);
                    jtfdia.setText(sjtfdia);
                }
            }
        });

        jtfhora.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfhora.getText().equals(sjtfhora)) {
                    jtfhora.setEditable(true);
                    jtfhora.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfhora.getText().equals("")) {
                    jtfhora.setEditable(false);
                    jtfhora.setText(sjtfhora);
                }
            }
        });

        jtfmin.addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent evt) {
                if (jtfmin.getText().equals(sjtfmin)) {
                    jtfmin.setEditable(true);
                    jtfmin.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (jtfmin.getText().equals("")) {
                    jtfmin.setEditable(false);
                    jtfmin.setText(sjtfmin);
                }
            }
        });

        return (jpborderD);
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
