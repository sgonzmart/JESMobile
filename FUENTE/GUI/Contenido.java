/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import TADS.*;
import java.util.*;
import Tablas.*;
import BaseDatos.*;
import MSJ.MSJ;
import java.awt.event.*;
import java.text.NumberFormat;

class Contenido {

    private static final int GAP = 5;
    private static final int GAPGRANDE = 20;
    private static final int BORDER = 1;  // Color border in pixels.
    final Font DEFAULTFONT = new java.awt.Font("Comic Sans MS", 2, 20);

    public JPanel mostrarFactura(Cliente cli, final BaseDatos bd) {
        final Cliente c = cli;
        final AyudaGB pos = new AyudaGB();
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        final JPanel panelInt = new JPanel(new GridBagLayout());
        panelInt.setBackground(Color.WHITE);
        ArrayList listaLin = cli.listaLineas();
        final ModeloTabla modelo = new ModeloTabla();

        final JScrollPane scroll = new JScrollPane();
        final JTable tabla = new JTable(modelo);

        scroll.setViewportView(tabla);
        scroll.setColumnHeaderView(tabla.getTableHeader());

        Image logo = new ImageIcon(getClass().getResource("/Imagenes/descargaPDF.gif"))
                .getImage().getScaledInstance(30, 30, 10);

        ImageIcon logoIcon = new ImageIcon(logo);
        final JButton jlImagen = new JButton(logoIcon);
        jlImagen.setToolTipText("Descargar Factura en PDF");

        final JButton jldescargar = new JButton("Ver Factura ");
        double importe = 0.0;
        int n = listaLin.size();

        switch (n) {
            case (0): // No dispone de lineas
                panel.add(new JTextField("No dispone de lineas sobre las que facturar"), pos.nextRow());
                break;
            case (1): // Solo tiene una linea
                panel.add(new Gap(GAPGRANDE), pos.nextRow());
                int numTel = ((Linea_VO) listaLin.get(0)).getNumtlf();
                final ArrayList listaFac = cli.listaFacturas(numTel);
                NumberFormat NF = NumberFormat.getInstance();
                NF.setMaximumFractionDigits(2); //2 decimales

                if (!listaFac.isEmpty()) {
                    final Factura_VO factu = (Factura_VO) listaFac.get(listaFac.size() - 1);
                    panel.add(new JLabel("Ultima Factura"), pos.nextRow());
                    panel.add(new JLabel("Numero de telefono: " + numTel), pos.nextRow());
                    importe = factu.getImporte();
                    panel.add(new Gap(GAPGRANDE), pos.nextRow());
                    panel.add(new Gap(GAP), pos.nextRow());
                    panel.add(new JLabel("Periodo Facturacion "), pos.nextRow());
                    panel.add(new Gap(GAPGRANDE), pos.nextCol());
                    panel.add(new JLabel("Importe (impuestos incluidos) "), pos.nextCol());

                    panel.add(new Gap(GAP), pos.nextRow());

                    String fechaIni = factu.getFechainicio();
                    String fechaFin = factu.getFechafin();
                    String fecha = fechaIni.substring(8, 10) + "/"
                            + fechaIni.substring(5, 7) + "/" + fechaIni.substring(0, 4)
                            + " - " + fechaFin.substring(8, 10) + "/"
                            + fechaFin.substring(5, 7) + "/" + fechaFin.substring(0, 4);
                    panel.add(new Gap(GAP), pos.nextRow());
                    panel.add(new JLabel(fecha), pos.nextRow());
                    panel.add(new Gap(GAPGRANDE), pos.nextCol());
                    panel.add(new JLabel(NF.format(importe) + " €"), pos.nextCol());

                    panel.add(new Gap(GAPGRANDE), pos.nextRow());

                    panel.add(new Gap(GAP), pos.nextRow());
                    panel.add(new JLabel("Descripcion "), pos.nextRow());
                    panel.add(new Gap(GAPGRANDE), pos.nextCol());
                    panel.add(new JLabel("Importe "), pos.nextCol());

                    panel.add(new Gap(GAP), pos.nextRow());

                    panel.add(new Gap(GAP), pos.nextRow());
                    panel.add(new JLabel("Total (base imponible) "), pos.nextRow());
                    panel.add(new Gap(GAPGRANDE), pos.nextCol());
                    panel.add(new JLabel(NF.format(importe / 1.18) + " €"), pos.nextCol());

                    panel.add(new Gap(GAP), pos.nextRow());
                    panel.add(new JLabel("IVA "), pos.nextRow());
                    panel.add(new Gap(GAPGRANDE), pos.nextCol());
                    panel.add(new JLabel(NF.format(importe - importe / 1.18) + " €"), pos.nextCol());

                    panel.add(new Gap(GAP), pos.nextRow());
                    panel.add(new JLabel("Total Factura "), pos.nextRow());
                    panel.add(new Gap(GAPGRANDE), pos.nextCol());
                    panel.add(new JLabel(NF.format(importe) + " €"), pos.nextCol());

                    panel.add(new Gap(GAP), pos.nextRow());
                    panel.add(new JLabel("Total a pagar "), pos.nextRow());
                    panel.add(new Gap(GAPGRANDE), pos.nextCol());
                    panel.add(new JLabel(NF.format(importe) + " €"), pos.nextCol());

                    panel.add(new Gap(GAP), pos.nextRow());
                    panel.add(new Gap(GAP), pos.nextCol());
                    panel.add(new Gap(GAP), pos.nextCol());
                    panel.add(jldescargar, pos.nextCol());
                    panel.add(jlImagen, pos.nextCol());
                    jlImagen.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent evt) {

                            GenerarPDF pdf = new GenerarPDF("Factura", "Factura.pdf", c.GetBD(), factu.getNumfactura(), c.getCliente());
                            //Preguntar al usuario si desea abrir el documento PDF
                            int respuesta = JOptionPane.showConfirmDialog(null, "Se ha generado el documento " + "Factura.pdf" + ", ¿Desea abrirlo?", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            //Si la respuesta es SI, abrirlo
                            if (respuesta == JOptionPane.YES_OPTION) {
                                pdf.abrirPDF();
                            }

                        }
                    });
                    jldescargar.addMouseListener(new MouseAdapter() {

                        public void mouseClicked(MouseEvent evt) {
                            NumberFormat NF = NumberFormat.getInstance();
                            NF.setMaximumFractionDigits(4); //2 decimales
                            for (int i = modelo.getRowCount() - 1; i >= 0; i--) {
                                modelo.borraFila(i);
                            }
                            Servicio ser = new Servicio(bd);
                            ArrayList listaSer = ser.listarServiciosFactura(factu.getNumfactura());
                            for (int i = 0; i < listaSer.size(); i++) {
                                Fila row = new Fila();
                                row.setServicio(Servicio_VO.tipoServicio(((Servicio_VO) listaSer.get(i)).getCodserv()));

                                if (((Servicio_VO) listaSer.get(i)).getCodserv() == Servicio_VO.DATOS) {
                                    row.setTelefono(" ");
                                    row.setDuracion(" ");
                                    row.setVolumen(Integer.toString(((Servicio_VO) listaSer.get(i)).getDatos()));
                                } else {
                                    row.setTelefono(Integer.toString(((Servicio_VO) listaSer.get(i)).getNumdestino()));

                                    if (((Servicio_VO) listaSer.get(i)).getCodserv() == Servicio_VO.MMS) {
                                        row.setVolumen(Integer.toString(
                                                ((Servicio_VO) listaSer.get(i)).getDatos()));
                                    } else {
                                        row.setVolumen(" ");
                                        if (((Servicio_VO) listaSer.get(i)).getCodserv() == Servicio_VO.SMS) {
                                            row.setDuracion(" ");
                                        } else { // VOZ
                                            int duracion = ((Servicio_VO) listaSer.get(i)).getDuracion();
                                            int min = duracion / 60;
                                            int seg = duracion % 60;
                                            row.setDuracion(min + ":" + seg);
                                        }
                                    }
                                }
                                String fecha = ((Servicio_VO) listaSer.get(i)).getFecha();

                                String fecha1 = fecha.substring(8, 10)
                                        + "-" + fecha.substring(5, 7);

                                row.setFecha(fecha1);
                                String hora = fecha.substring(11, 13)
                                        + ":" + fecha.substring(14, 15);
                                row.setHora(hora);

                                double coste = ((Servicio_VO) listaSer.get(i)).coste();

                                row.setImporte(NF.format(coste));
                                modelo.anyadeFila(row);
                            }
                            JOptionPane.showConfirmDialog(null, scroll,
                                    "Detalles Factura",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    });
                } else {
                    panel.add(new JTextField("No hay facturas disponibles para mostrar"), pos.nextRow());
                }
                break;
            default: // Tiene mas de una linea
                panel.add(new Gap(GAPGRANDE), pos.nextRow());
                panel.add(new JLabel("Ultima Factura"), pos.nextRow());
                final JComboBox lstLineas = new JComboBox();
                JScrollPane lineas = new JScrollPane(lstLineas);
                lstLineas.addItem("Seleccione el numero de telefono");
                for (int i = 0; i < n; i++) {
                    lstLineas.addItem(Integer.toString(((Linea_VO) listaLin.get(i)).getNumtlf()));
                }
                panel.add(lineas, pos.nextRow());
                panel.add(panelInt, pos.nextRow().width(3));
                lstLineas.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        final ArrayList listaFacL = c.listaFacturas(Integer.parseInt(lstLineas.getSelectedItem().toString()));
                        panelInt.removeAll();

                        if (!listaFacL.isEmpty()) {
                            NumberFormat NF = NumberFormat.getInstance();
                            NF.setMaximumFractionDigits(2); //2 decimales
                            panelInt.add(new Gap(GAPGRANDE), pos.nextRow());

                            final Factura_VO factu = (Factura_VO) listaFacL.get(listaFacL.size() - 1);
                            panelInt.add(new JLabel("Ultima Factura"), pos.nextRow());
                            panelInt.add(new JLabel("Numero de telefono: " + lstLineas.getSelectedItem()), pos.nextRow());
                            final double coste = factu.getImporte();

                            panelInt.add(new Gap(GAPGRANDE), pos.nextRow());
                            panelInt.add(new JLabel("Periodo Facturacion "), pos.nextRow());
                            panelInt.add(new Gap(GAPGRANDE), pos.nextCol());
                            panelInt.add(new JLabel("Importe (impuestos incluidos) "), pos.nextCol());

                            panelInt.add(new Gap(GAP), pos.nextRow());
                            String fechaIni = factu.getFechainicio();
                            String fechaFin = factu.getFechafin();
                            String fecha = fechaIni.substring(8, 10) + "/"
                                    + fechaIni.substring(5, 7) + "/" + fechaIni.substring(0, 4)
                                    + " - " + fechaFin.substring(8, 10) + "/"
                                    + fechaFin.substring(5, 7) + "/" + fechaFin.substring(0, 4);
                            panelInt.add(new Gap(GAP), pos.nextRow());
                            panelInt.add(new JLabel(fecha), pos.nextRow());
                            panelInt.add(new Gap(GAPGRANDE), pos.nextCol());
                            panelInt.add(new JLabel(NF.format(coste) + " €"), pos.nextCol());

                            panelInt.add(new Gap(GAPGRANDE), pos.nextRow());

                            panelInt.add(new Gap(GAP), pos.nextRow());
                            panelInt.add(new JLabel("Descripcion "), pos.nextRow());
                            panelInt.add(new Gap(GAPGRANDE), pos.nextCol());
                            panelInt.add(new JLabel("Importe "), pos.nextCol());

                            panelInt.add(new Gap(GAP), pos.nextRow());

                            panelInt.add(new Gap(GAP), pos.nextRow());
                            panelInt.add(new JLabel("Total (base imponible) "), pos.nextRow());
                            panelInt.add(new Gap(GAPGRANDE), pos.nextCol());
                            panelInt.add(new JLabel(NF.format(coste / 1.18) + " €"),
                                    pos.nextCol());

                            panelInt.add(new Gap(GAP), pos.nextRow());
                            panelInt.add(new JLabel("IVA "), pos.nextRow());
                            panelInt.add(new Gap(GAPGRANDE), pos.nextCol());
                            panelInt.add(new JLabel(NF.format(coste - coste / 1.18) + " €"), pos.nextCol());

                            panelInt.add(new Gap(GAP), pos.nextRow());
                            panelInt.add(new JLabel("Total Factura "), pos.nextRow());
                            panelInt.add(new Gap(GAPGRANDE), pos.nextCol());
                            panelInt.add(new JLabel(NF.format(coste) + " €"), pos.nextCol());

                            panelInt.add(new Gap(GAP), pos.nextRow());
                            panelInt.add(new JLabel("Total a pagar "), pos.nextRow());
                            panelInt.add(new Gap(GAPGRANDE), pos.nextCol());
                            panelInt.add(new JLabel(NF.format(coste) + " €"), pos.nextCol());

                            panelInt.add(new Gap(GAP), pos.nextRow());
                            panelInt.add(new Gap(GAP), pos.nextCol());
                            panelInt.add(new Gap(GAP), pos.nextCol());
                            panelInt.add(jldescargar, pos.nextCol());
                            panelInt.add(jlImagen, pos.nextCol());

                            jlImagen.addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent evt) {

                                    GenerarPDF pdf = new GenerarPDF("Factura", "Factura.pdf", c.GetBD(), factu.getNumfactura(), c.getCliente());
                                    //Preguntar al usuario si desea abrir el documento PDF
                                    int respuesta = JOptionPane.showConfirmDialog(null, "Se ha generado el documento " + "Factura.pdf" + ", ¿Desea abrirlo?", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                    //Si la respuesta es SI, abrirlo
                                    if (respuesta == JOptionPane.YES_OPTION) {
                                        pdf.abrirPDF();
                                    }

                                }
                            });
                            jldescargar.addMouseListener(new MouseAdapter() {

                                public void mouseClicked(MouseEvent evt) {
                                    NumberFormat NF = NumberFormat.getInstance();
                                    NF.setMaximumFractionDigits(4); //2 decimales
                                    for (int i = modelo.getRowCount() - 1; i >= 0; i--) {
                                        modelo.borraFila(i);
                                    }
                                    Servicio ser = new Servicio(bd);
                                    ArrayList listaSer = ser.listarServiciosFactura(factu.getNumfactura());
                                    for (int i = 0; i < listaSer.size(); i++) {
                                        Fila row = new Fila();
                                        row.setServicio(Servicio_VO.tipoServicio(((Servicio_VO) listaSer.get(i)).getCodserv()));

                                        if (((Servicio_VO) listaSer.get(i)).getCodserv() == Servicio_VO.DATOS) {
                                            row.setTelefono(" ");
                                            row.setDuracion(" ");
                                            row.setVolumen(Integer.toString(((Servicio_VO) listaSer.get(i)).getDatos()));
                                        } else {
                                            row.setTelefono(Integer.toString(((Servicio_VO) listaSer.get(i)).getNumdestino()));

                                            if (((Servicio_VO) listaSer.get(i)).getCodserv() == Servicio_VO.MMS) {
                                                row.setVolumen(Integer.toString(
                                                        ((Servicio_VO) listaSer.get(i)).getDatos()));
                                            } else {
                                                row.setVolumen(" ");
                                                if (((Servicio_VO) listaSer.get(i)).getCodserv() == Servicio_VO.SMS) {
                                                    row.setDuracion(" ");
                                                } else { // VOZ
                                                    int duracion = ((Servicio_VO) listaSer.get(i)).getDuracion();
                                                    int min = duracion / 60;
                                                    int seg = duracion % 60;
                                                    row.setDuracion(min + ":" + seg);
                                                }
                                            }
                                        }
                                        String fecha = ((Servicio_VO) listaSer.get(i)).getFecha();

                                        String fecha1 = fecha.substring(8, 10)
                                                + "-" + fecha.substring(5, 7);

                                        row.setFecha(fecha1);
                                        String hora = fecha.substring(11, 13)
                                                + ":" + fecha.substring(14, 15);
                                        row.setHora(hora);

                                        double coste = ((Servicio_VO) listaSer.get(i)).coste();

                                        row.setImporte(NF.format(coste));
                                        modelo.anyadeFila(row);
                                    }
                                    JOptionPane.showConfirmDialog(null, scroll,
                                            "Detalles Factura",
                                            JOptionPane.DEFAULT_OPTION,
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            });
                        } else {
                            panel.add(new JTextField("No hay facturas disponibles para mostrar"), pos.nextRow());
                        }
                        panel.updateUI();
                    }
                });
                break;
        }
        return panel;
    }

    public JPanel consumoActual(Cliente cli, final Servicio ser) {
        final AyudaGB pos = new AyudaGB();
        final JPanel panel = new JPanel(new GridBagLayout());
        final JPanel panelInt = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panelInt.setBackground(Color.WHITE);
        final Cliente c = cli;

        ArrayList listaLin = cli.listaLineasActivas();
        int n = listaLin.size();

        switch (n) {
            case (0): // No dispone de lineas
                panel.add(new JTextField("No dispone de lineas sobre las mostrar consumo"), pos.nextRow());
                break;
            case (1): // Solo tiene una linea
                panel.add(new Gap(GAPGRANDE), pos.nextRow());
                int numTel = ((Linea_VO) listaLin.get(0)).getNumtlf();
                ArrayList listaSer = ser.listarServiciosSinFacturar(numTel);
                double totalVoz = 0,
                 totalSMS = 0,
                 totalMMS = 0,
                 totalDatos = 0;
                panel.removeAll();
                panel.add(new JLabel("Consumo Actual"), pos.nextRow());
                panel.add(new JLabel("Numero de telefono: " + numTel), pos.nextRow());
                panel.add(new Gap(GAPGRANDE), pos.nextRow());

                for (int i = 0; i < listaSer.size(); i++) {
                    switch ((((Servicio_VO) listaSer.get(i)).getCodserv())) {
                        case (1): // SMS
                            totalSMS = totalSMS + (((Servicio_VO) listaSer.get(i)).coste());
                            break;
                        case (2): // MMS
                            totalMMS = totalMMS + (((Servicio_VO) listaSer.get(i)).coste());
                            break;
                        case (3): // Voz
                            totalVoz = totalVoz + (((Servicio_VO) listaSer.get(i)).coste());
                            break;
                        case (4): // Datos
                            totalDatos = totalDatos + (((Servicio_VO) listaSer.get(i)).coste());
                            break;
                    }
                }
                NumberFormat NF = NumberFormat.getInstance();
                NF.setMaximumFractionDigits(2); //2 decimales

                panel.add(new JLabel("Total SMS acumulado: " + NF.format(totalSMS) + " €"), pos.nextRow());
                panel.add(new Gap(GAP), pos.nextCol());
                panel.add(new Gap(GAP), pos.nextRow());
                panel.add(new JLabel("Total MMS acumulado: " + NF.format(totalMMS) + " €"), pos.nextRow());
                panel.add(new Gap(GAP), pos.nextCol());
                panel.add(new Gap(GAP), pos.nextRow());
                panel.add(new JLabel("Total Voz acumulado: " + NF.format(totalVoz) + " €"), pos.nextRow());
                panel.add(new Gap(GAP), pos.nextCol());
                panel.add(new Gap(GAP), pos.nextRow());
                panel.add(new JLabel("Total Datos acumulado: " + NF.format(totalDatos) + " €"), pos.nextRow());
                panel.add(new Gap(GAP), pos.nextCol());
                panel.add(new Gap(GAP), pos.nextRow());
                panel.add(new JLabel("Total : " + NF.format(totalSMS + totalMMS + totalVoz + totalDatos) + " €"), pos.nextRow());
                panel.add(new Gap(GAP), pos.nextCol());
                panel.add(new Gap(GAP), pos.nextRow());
                break;
            default: // Tiene mas de una linea
                panel.add(new Gap(GAPGRANDE), pos.nextRow());
                panel.removeAll();
                panel.add(new JLabel("Consumo Actual"), pos.nextRow());

                final JComboBox lstLineas = new JComboBox();
                JScrollPane lineas = new JScrollPane(lstLineas);
                lstLineas.addItem("Seleccione el numero de telefono");
                for (int i = 0; i < n; i++) {
                    lstLineas.addItem(Integer.toString(((Linea_VO) listaLin.get(i)).getNumtlf()));
                }
                panel.add(lineas, pos.nextRow());
                panel.add(panelInt, pos.nextRow().width(3));
                lstLineas.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        ArrayList listaSerL = ser.listarServiciosSinFacturar(Integer.parseInt(lstLineas.getSelectedItem().toString()));
                        panelInt.removeAll();
                        double totalVoz = 0, totalSMS = 0, totalMMS = 0, totalDatos = 0;
                        panelInt.add(new Gap(GAPGRANDE), pos.nextRow());
                        panelInt.add(new JLabel("Numero de telefono: " + lstLineas.getSelectedItem()), pos.nextRow());
                        panelInt.add(new Gap(GAPGRANDE), pos.nextRow());

                        for (int i = 0; i < listaSerL.size(); i++) {
                            switch ((((Servicio_VO) listaSerL.get(i)).getCodserv())) {
                                case (1): // SMS
                                    totalSMS = totalSMS + (((Servicio_VO) listaSerL.get(i)).coste());
                                    break;
                                case (2): // MMS
                                    totalMMS = totalMMS + (((Servicio_VO) listaSerL.get(i)).coste());
                                    break;
                                case (3): // Voz
                                    totalVoz = totalVoz + (((Servicio_VO) listaSerL.get(i)).coste());
                                    break;
                                case (4): // Datos
                                    totalDatos = totalDatos + (((Servicio_VO) listaSerL.get(i)).coste());
                                    break;
                            }
                        }
                        NumberFormat NF = NumberFormat.getInstance();
                        NF.setMaximumFractionDigits(2); //3 decimales

                        panelInt.add(new JLabel("Total SMS acumulado: " + NF.format(totalSMS) + " €"), pos.nextRow());
                        panelInt.add(new Gap(GAP), pos.nextCol());
                        panelInt.add(new Gap(GAP), pos.nextRow());
                        panelInt.add(new JLabel("Total MMS acumulado: " + NF.format(totalMMS) + " €"), pos.nextRow());
                        panelInt.add(new Gap(GAP), pos.nextCol());
                        panelInt.add(new Gap(GAP), pos.nextRow());
                        panelInt.add(new JLabel("Total Voz acumulado: " + NF.format(totalVoz) + " €"), pos.nextRow());
                        panelInt.add(new Gap(GAP), pos.nextCol());
                        panelInt.add(new Gap(GAP), pos.nextRow());
                        panelInt.add(new JLabel("Total Datos acumulado: " + NF.format(totalDatos) + " €"), pos.nextRow());
                        panelInt.add(new Gap(GAP), pos.nextCol());
                        panelInt.add(new Gap(GAP), pos.nextRow());
                        panelInt.add(new JLabel("Total : " + NF.format(totalSMS + totalMMS + totalVoz + totalDatos) + " €"), pos.nextRow());
                        panelInt.add(new Gap(GAP), pos.nextCol());
                        panelInt.add(new Gap(GAP), pos.nextRow());
                        panel.updateUI();
                    }
                });
                panel.updateUI();
                break;
        }
        return panel;
    }

    public JPanel facturasAnteriores(Factura fac, final Cliente cli, Linea lin, final BaseDatos bd) {
        final AyudaGB pos = new AyudaGB();
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        final JPanel panelInt = new JPanel(new GridBagLayout());
        panelInt.setBackground(Color.WHITE);
        final ArrayList lista = cli.listaLineas();
        Linea_VO lin_VO = new Linea_VO();
        // Crea el modelo
        final ModeloTabla modelo = new ModeloTabla();

        final JScrollPane scroll = new JScrollPane();
        final JTable tabla = new JTable(modelo);

        scroll.setViewportView(tabla);
        scroll.setColumnHeaderView(tabla.getTableHeader());


        panel.add(new Gap(GAPGRANDE), pos.nextRow());
        panel.add(new JLabel("Facturas Anteriores"), pos.nextRow());
        panel.add(new Gap(GAPGRANDE), pos.nextRow());

        int numTelef;
        ArrayList listaFac;
        final Fila fila = new Fila();
        Factura_VO fac_VO;

        switch (lista.size()) {
            case (0):
                panel.add(new JLabel("No dispone de lineas sobre las que mostrar facturas"), pos.nextRow());
                break;
            case (1): // una unica linea
                panel.add(new Gap(GAPGRANDE), pos.nextRow());
                panel.add(new JLabel("Fecha Inicio"), pos.nextRow());
                panel.add(new Gap(GAP), pos.nextCol());
                panel.add(new JLabel("Fecha Fin"), pos.nextCol());
                panel.add(new Gap(GAP), pos.nextCol());
                panel.add(new JLabel("Importe"), pos.nextCol());
                panel.add(new Gap(GAP), pos.nextCol());
                panel.add(new JLabel("Pagada"), pos.nextCol());
                panel.add(new Gap(GAP), pos.nextCol());
                panel.add(new JLabel("Ver Factura"), pos.nextCol());
                panel.add(new Gap(GAP), pos.nextCol());
                panel.add(new JLabel("Descargar PDF"), pos.nextCol());
                panel.add(new Gap(GAP), pos.nextCol());
                panel.add(new Gap(GAP), pos.nextRow());

                NumberFormat NF = NumberFormat.getInstance();
                NF.setMaximumFractionDigits(2); //2 decimales


                lin_VO = (Linea_VO) lista.get(0);
                numTelef = lin_VO.getNumtlf();
                listaFac = cli.listaFacturas(numTelef);
                fac_VO = new Factura_VO();
                if (listaFac.isEmpty()) {
                    panel.add(new JLabel("No hay facturas disponibles para mostrar"), pos.nextRow().width(5));
                }
                for (int i = 0; i < listaFac.size(); i++) {
                    fac_VO = (Factura_VO) listaFac.get(i);
                    final Factura_VO fact = fac_VO;
                    JButton ver = new JButton("Ver Detalles");
                    ver.addMouseListener(new MouseAdapter() {

                        public void mouseClicked(MouseEvent evt) {
                            NumberFormat NF = NumberFormat.getInstance();
                            NF.setMaximumFractionDigits(4); //2 decimales
                            for (int i = modelo.getRowCount() - 1; i >= 0; i--) {
                                modelo.borraFila(i);
                            }
                            Servicio ser = new Servicio(bd);
                            ArrayList listaSer = ser.listarServiciosFactura(fact.getNumfactura());
                            for (int i = 0; i < listaSer.size(); i++) {
                                Fila row = new Fila();
                                row.setServicio(Servicio_VO.tipoServicio(((Servicio_VO) listaSer.get(i)).getCodserv()));

                                if (((Servicio_VO) listaSer.get(i)).getCodserv() == Servicio_VO.DATOS) {
                                    row.setTelefono(" ");
                                    row.setDuracion(" ");
                                    row.setVolumen(Integer.toString(((Servicio_VO) listaSer.get(i)).getDatos()));
                                } else {
                                    row.setTelefono(Integer.toString(((Servicio_VO) listaSer.get(i)).getNumdestino()));

                                    if (((Servicio_VO) listaSer.get(i)).getCodserv() == Servicio_VO.MMS) {
                                        row.setVolumen(Integer.toString(
                                                ((Servicio_VO) listaSer.get(i)).getDatos()));
                                    } else {
                                        row.setVolumen(" ");
                                        if (((Servicio_VO) listaSer.get(i)).getCodserv() == Servicio_VO.SMS) {
                                            row.setDuracion(" ");
                                        } else { // VOZ
                                            int duracion = ((Servicio_VO) listaSer.get(i)).getDuracion();
                                            int min = duracion / 60;
                                            int seg = duracion % 60;
                                            row.setDuracion(min + ":" + seg);
                                        }
                                    }
                                }
                                String fecha = ((Servicio_VO) listaSer.get(i)).getFecha();

                                String fecha1 = fecha.substring(8, 10)
                                        + "-" + fecha.substring(5, 7);

                                row.setFecha(fecha1);
                                String hora = fecha.substring(11, 13)
                                        + ":" + fecha.substring(14, 15);
                                row.setHora(hora);

                                double coste = ((Servicio_VO) listaSer.get(i)).coste();

                                row.setImporte(NF.format(coste));
                                modelo.anyadeFila(row);
                            }
                            modelo.anyadeFila(fila);
                            JOptionPane.showConfirmDialog(null, scroll,
                                    "Detalles Factura",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    });
                    JButton descargar = new JButton("Descargar PDF");
                    descargar.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent evt) {

                            GenerarPDF pdf = new GenerarPDF("Factura", "Factura.pdf", cli.GetBD(), fact.getNumfactura(), cli.getCliente());
                            //Preguntar al usuario si desea abrir el documento PDF
                            int respuesta = JOptionPane.showConfirmDialog(null, "Se ha generado el documento " + "Factura.pdf" + ", ¿Desea abrirlo?", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            //Si la respuesta es SI, abrirlo
                            if (respuesta == JOptionPane.YES_OPTION) {
                                pdf.abrirPDF();
                            }

                        }
                    });

                    String fechaIni = fac_VO.getFechainicio();
                    String fechaFin = fac_VO.getFechafin();
                    String fecha1 = fechaIni.substring(8, 10) + "/"
                            + fechaIni.substring(5, 7) + "/"
                            + fechaIni.substring(0, 4);

                    String fecha2 = fechaFin.substring(8, 10) + "/"
                            + fechaFin.substring(5, 7) + "/"
                            + fechaFin.substring(0, 4);

                    String pago;
                    if (fac_VO.getEstapagada() == 1) {
                        pago = "Pagada";
                    } else {
                        pago = "Pendiente";
                    }

                    panel.add(new JLabel(fecha1), pos.nextRow());
                    panel.add(new Gap(GAP), pos.nextCol());
                    panel.add(new JLabel(fecha2), pos.nextCol());
                    panel.add(new Gap(GAP), pos.nextCol());
                    panel.add(new JLabel(NF.format(fac_VO.getImporte())
                            + " €"), pos.nextCol());
                    panel.add(new Gap(GAP), pos.nextCol());
                    panel.add(new JLabel(pago), pos.nextCol());
                    panel.add(new Gap(GAP), pos.nextCol());
                    panel.add(ver, pos.nextCol());
                    panel.add(new Gap(GAP), pos.nextCol());
                    panel.add(descargar, pos.nextCol());
                    panel.add(new Gap(GAP), pos.nextCol());
                    panel.add(new Gap(GAP), pos.nextRow());

                }
                panel.updateUI();

                break;
            default: //Hay mas de una linea
                final JComboBox lstLineas = new JComboBox();
                JScrollPane lineas = new JScrollPane(lstLineas);
                lstLineas.addItem("Seleccione el numero de telefono");
                for (int i = 0; i < lista.size(); i++) {
                    lstLineas.addItem(Integer.toString(((Linea_VO) lista.get(i)).getNumtlf()));
                }

                panel.add(lineas, pos.nextRow());
                panel.add(panelInt, pos.nextRow().width(3));
                lstLineas.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        NumberFormat NF = NumberFormat.getInstance();
                        NF.setMaximumFractionDigits(2); //2 decimales
                        panelInt.removeAll();
                        final AyudaGB posInt = new AyudaGB();
                        panelInt.add(new Gap(GAPGRANDE), posInt.nextRow());
                        panelInt.add(new JLabel("Fecha Inicio"), posInt.nextRow());
                        panelInt.add(new Gap(GAP), posInt.nextCol());
                        panelInt.add(new JLabel("Fecha Fin"), posInt.nextCol());
                        panelInt.add(new Gap(GAP), posInt.nextCol());
                        panelInt.add(new JLabel("Importe"), posInt.nextCol());
                        panelInt.add(new Gap(GAP), posInt.nextCol());
                        panelInt.add(new JLabel("Pagada"), posInt.nextCol());
                        panelInt.add(new Gap(GAP), posInt.nextCol());
                        panelInt.add(new JLabel("Ver Factura"), posInt.nextCol());
                        panelInt.add(new Gap(GAP), posInt.nextCol());
                        panelInt.add(new JLabel("Descargar PDF"), posInt.nextCol());
                        panelInt.add(new Gap(GAP), posInt.nextCol());
                        panelInt.add(new Gap(GAP), posInt.nextRow());
                        Factura_VO factura_VO = new Factura_VO();
                        ArrayList listaFacturas = cli.listaFacturas(Integer.parseInt(lstLineas.getSelectedItem().toString()));
                        if (listaFacturas.isEmpty()) {
                            panelInt.add(new JLabel("No hay facturas disponibles para mostrar"), posInt.nextRow().width(9));
                        }
                        for (int i = 0; i < listaFacturas.size(); i++) {
                            factura_VO = (Factura_VO) listaFacturas.get(i);
                            final Factura_VO fact = factura_VO;
                            JButton ver = new JButton("Ver Detalles");
                            ver.addMouseListener(new MouseAdapter() {

                                public void mouseClicked(MouseEvent evt) {
                                    NumberFormat NF = NumberFormat.getInstance();
                                    NF.setMaximumFractionDigits(4); //2 decimales
                                    for (int i = modelo.getRowCount() - 1; i >= 0; i--) {
                                        modelo.borraFila(i);
                                    }
                                    Servicio ser = new Servicio(bd);
                                    ArrayList listaSer = ser.listarServiciosFactura(fact.getNumfactura());
                                    for (int i = 0; i < listaSer.size(); i++) {
                                        Fila row = new Fila();
                                        row.setServicio(Servicio_VO.tipoServicio(((Servicio_VO) listaSer.get(i)).getCodserv()));

                                        if (((Servicio_VO) listaSer.get(i)).getCodserv() == Servicio_VO.DATOS) {
                                            row.setTelefono(" ");
                                            row.setDuracion(" ");
                                            row.setVolumen(Integer.toString(((Servicio_VO) listaSer.get(i)).getDatos()));
                                        } else {
                                            row.setTelefono(Integer.toString(((Servicio_VO) listaSer.get(i)).getNumdestino()));

                                            if (((Servicio_VO) listaSer.get(i)).getCodserv() == Servicio_VO.MMS) {
                                                row.setVolumen(Integer.toString(
                                                        ((Servicio_VO) listaSer.get(i)).getDatos()));
                                            } else {
                                                row.setVolumen(" ");
                                                if (((Servicio_VO) listaSer.get(i)).getCodserv() == Servicio_VO.SMS) {
                                                    row.setDuracion(" ");
                                                } else { // VOZ
                                                    int duracion = ((Servicio_VO) listaSer.get(i)).getDuracion();
                                                    int min = duracion / 60;
                                                    int seg = duracion % 60;
                                                    row.setDuracion(min + ":" + seg);
                                                }
                                            }
                                        }
                                        String fecha = ((Servicio_VO) listaSer.get(i)).getFecha();

                                        String fecha1 = fecha.substring(8, 10)
                                                + "-" + fecha.substring(5, 7);

                                        row.setFecha(fecha1);
                                        String hora = fecha.substring(11, 13)
                                                + ":" + fecha.substring(14, 15);
                                        row.setHora(hora);

                                        double coste = ((Servicio_VO) listaSer.get(i)).coste();

                                        row.setImporte(NF.format(coste));
                                        modelo.anyadeFila(row);
                                    }
                                    modelo.anyadeFila(fila);
                                    JOptionPane.showConfirmDialog(null, scroll,
                                            "Detalles Factura",
                                            JOptionPane.DEFAULT_OPTION,
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            });
                            JButton descargar = new JButton("Descargar PDF");
                            descargar.addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent evt) {

                                    GenerarPDF pdf = new GenerarPDF("Factura", "Factura.pdf", cli.GetBD(), fact.getNumfactura(), cli.getCliente());
                                    //Preguntar al usuario si desea abrir el documento PDF
                                    int respuesta = JOptionPane.showConfirmDialog(null, "Se ha generado el documento " + "Factura.pdf" + ", ¿Desea abrirlo?", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                    //Si la respuesta es SI, abrirlo
                                    if (respuesta == JOptionPane.YES_OPTION) {
                                        pdf.abrirPDF();
                                    }

                                }
                            });

                            String fechaIni = factura_VO.getFechainicio();
                            String fechaFin = factura_VO.getFechafin();
                            String fecha1 = fechaIni.substring(8, 10) + "/"
                                    + fechaIni.substring(5, 7) + "/"
                                    + fechaIni.substring(0, 4);

                            String fecha2 = fechaFin.substring(8, 10) + "/"
                                    + fechaFin.substring(5, 7) + "/"
                                    + fechaFin.substring(0, 4);

                            String pago;
                            if (factura_VO.getEstapagada() == 1) {
                                pago = "Pagada";
                            } else {
                                pago = "Pendiente";
                            }

                            panelInt.add(new JLabel(fecha1), posInt.nextRow());
                            panelInt.add(new Gap(GAP), posInt.nextCol());
                            panelInt.add(new JLabel(fecha2), posInt.nextCol());
                            panelInt.add(new Gap(GAP), posInt.nextCol());
                            panelInt.add(new JLabel(NF.format(factura_VO.getImporte())
                                    + " €"), posInt.nextCol());
                            panelInt.add(new Gap(GAP), posInt.nextCol());
                            panelInt.add(new JLabel(pago), posInt.nextCol());
                            panelInt.add(new Gap(GAP), posInt.nextCol());
                            panelInt.add(ver, posInt.nextCol());
                            panelInt.add(new Gap(GAP), posInt.nextCol());
                            panelInt.add(descargar, posInt.nextCol());
                            panelInt.add(new Gap(GAP), posInt.nextCol());
                            panelInt.add(new Gap(GAP), posInt.nextRow());

                        }
                        panel.updateUI();
                    }
                });
                panel.updateUI();
                break;
        }
        return panel;
    }

    public JPanel datosPersonales(Cliente cli) {
        JPanel panel = new JPanel(new GridBagLayout());
        JPanel jppersonal = new JPanel(new GridBagLayout());
        JPanel jpdireccion = new JPanel(new GridBagLayout());
        JPanel jpbancarios = new JPanel(new GridBagLayout());
        jppersonal.setBackground(Color.WHITE);
        jpdireccion.setBackground(Color.WHITE);
        jpbancarios.setBackground(Color.WHITE);
        panel.setBackground(Color.WHITE);
        jppersonal.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Datos Personales ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        jpdireccion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Direccion ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        jpbancarios.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Datos Bancarios ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        Cliente_VO c;
        c = cli.getCliente();

        AyudaGB posPer = new AyudaGB();
        jppersonal.add(new Gap(GAP), posPer.nextRow());
        jppersonal.add(new JLabel(" > Nombre:"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new JLabel(c.getNombre()), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextRow());
        jppersonal.add(new JLabel(" > Apellidos:"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new JLabel(c.getApellidos()), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextRow());
        jppersonal.add(new JLabel(" > Documento Ident:"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new JLabel(c.getNif()), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextRow());
        jppersonal.add(new JLabel(" > Email:"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new JLabel(c.getEmail()), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextRow());
        jppersonal.add(new JLabel(" > Telefono:"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new JLabel(Integer.toString(c.getTelefono())), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextRow());

        AyudaGB posdir = new AyudaGB();
        String direcc;
        jpdireccion.add(new Gap(GAP), posdir.nextRow());
        direcc = c.getTipovia() + "\\ " + c.getNomvia();
        jpdireccion.add(new JLabel(direcc), posdir.nextRow().width(3));
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel(" > Nº:"), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel(c.getNumcalle()), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextRow());
        if (!c.getPiso().isEmpty()) {

            jpdireccion.add(new JLabel(" > Piso:"), posdir.nextRow());
            jpdireccion.add(new Gap(GAP), posdir.nextCol());
            jpdireccion.add(new JLabel(c.getPiso()), posdir.nextCol());
            jpdireccion.add(new Gap(GAP), posdir.nextCol());
        }
        if (!(c.getPuerta().isEmpty())) {
            jpdireccion.add(new JLabel(" > Puerta:"), posdir.nextCol());
            jpdireccion.add(new Gap(GAP), posdir.nextCol());
            jpdireccion.add(new JLabel(c.getPuerta()), posdir.nextCol());
            jpdireccion.add(new Gap(GAP), posdir.nextCol());
        }
        if (!(c.getEscalera().isEmpty())) {
            jpdireccion.add(new JLabel(" > Escalera:"), posdir.nextCol());
            jpdireccion.add(new Gap(GAP), posdir.nextCol());
            jpdireccion.add(new JLabel(c.getEscalera()), posdir.nextCol());
            jpdireccion.add(new Gap(GAP), posdir.nextCol());
        }
        jpdireccion.add(new Gap(GAP), posdir.nextRow());
        jpdireccion.add(new JLabel(" > Localidad:"), posdir.nextRow());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel(c.getLocalidad()), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel(" > Codigo Postal:"), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel(Integer.toString(c.getCodpostal())), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel(" > Provincia:"), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel(c.getProvincia()), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextRow());

        AyudaGB posban = new AyudaGB();
        jpbancarios.add(new Gap(GAP), posban.nextRow());
        jpbancarios.add(new JLabel(" > Tipo de pago:"), posban.nextRow());
        jpbancarios.add(new Gap(GAP), posban.nextCol());
        jpbancarios.add(new JLabel(c.getTipopago()), posban.nextCol());
        jpbancarios.add(new Gap(GAP), posban.nextCol());
        jpbancarios.add(new Gap(GAP), posban.nextRow());
        jpbancarios.add(new JLabel(" > Titular:"), posban.nextRow());
        jpbancarios.add(new Gap(GAP), posban.nextCol());
        jpbancarios.add(new JLabel(c.getTitular()), posban.nextCol());
        if (c.getTipopago().equals("Tarjeta")) {
            jpbancarios.add(new Gap(GAP), posban.nextRow());
            jpbancarios.add(new JLabel(" > Numero de tarjeta:"), posban.nextRow());
            jpbancarios.add(new Gap(GAP), posban.nextCol());
            jpbancarios.add(new JLabel(c.getNumtarjeta()), posban.nextCol());
            jpbancarios.add(new Gap(GAP), posban.nextCol());
            jpbancarios.add(new Gap(GAP), posban.nextRow());
            jpbancarios.add(new JLabel(" > Fecha de Caducidad:"), posban.nextRow());
            jpbancarios.add(new Gap(GAP), posban.nextCol());
            jpbancarios.add(new JLabel(c.getCaducidad()), posban.nextCol());
            jpbancarios.add(new Gap(GAP), posban.nextCol());
        } else {
            jpbancarios.add(new Gap(GAP), posban.nextRow());
            jpbancarios.add(new JLabel(" > Nombre entidad financiera:"), posban.nextRow());
            jpbancarios.add(new Gap(GAP), posban.nextCol());
            String numCuenta = c.getNumcuenta();
            int codigo = Integer.parseInt(numCuenta.substring(0, 4));
            jpbancarios.add(new JLabel(codigosBancarios(codigo)), posban.nextCol());
            jpbancarios.add(new Gap(GAP), posban.nextCol());
            jpbancarios.add(new Gap(GAP), posban.nextRow());
            jpbancarios.add(new JLabel(" > Numero de cuenta:"), posban.nextRow());
            jpbancarios.add(new Gap(GAP), posban.nextCol());
            numCuenta = numCuenta.substring(0, 12);
            numCuenta = numCuenta + "********";
            jpbancarios.add(new JLabel(numCuenta), posban.nextCol());
            jpbancarios.add(new Gap(GAP), posban.nextCol());
            jpbancarios.add(new Gap(GAP), posban.nextRow());
        }

        AyudaGB pos = new AyudaGB();
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(jppersonal, pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(jpdireccion, pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(jpbancarios, pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.setBackground(Color.WHITE);

        return panel;
    }

    public JPanel modifDPersonales(Cliente cli) {
        final JPanel panel = new JPanel(new GridBagLayout());
        JPanel jppersonal = new JPanel(new GridBagLayout());
        JPanel jpdireccion = new JPanel(new GridBagLayout());
        JPanel jpbancarios = new JPanel(new GridBagLayout());
        final JPanel jpbancariosA = new JPanel(new GridBagLayout());
        JButton jbAceptar = new JButton("Aceptar");
        jppersonal.setBackground(Color.WHITE);
        jpdireccion.setBackground(Color.WHITE);
        jpbancarios.setBackground(Color.WHITE);
        jpbancariosA.setBackground(Color.WHITE);
        panel.setBackground(Color.WHITE);

        jppersonal.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Datos Personales ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        jpdireccion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Direccion ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        jpbancarios.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Datos Bancarios ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));


        String[] TipoVia = {"", "Alameda", "Autopista", "Autovia", "Avenida", "Calle",
            "Callejon", "Camino", "Cañada", "Carrera", "Carretera",
            "Circunvalacion", "Cuesta", "Glorieta", "Parque", "Pasaje",
            "Paseo", "Plaza", "Rambla", "Ronda", "Sendero", "Travesia",
            "Urbanizacion", "Via"};
        String[] Provincia = {"", "Alava", "Albacete", "Alicante", "Almeria", "Asturias", "Avila",
            "Badajoz", "Barcelona", "Burgos", "Caceres", "Cadiz", "Cantabria",
            "Castellon", "Ceuta", "Cuidad Real", "Cordoba", "La Coruña",
            "Cuenca", "Gerona", "Granada", "Guadalajara", "Guipuzcoa",
            "Huelva", "Huesca", "Islas Baleares", "Jaen", "Leon", "Lerida",
            "Lugo", "Madrid", "Malaga", "Melilla", "Murcia", "Navarra",
            "Orense", "Palencia", "Las Palmas", "Pontevedra", "La Rioja",
            "Salamanca", "Segovia", "Sevilla", "Soria", "Tarragona",
            "Santa Cruz de Tenerife", "Teruel", "Toledo", "Valencia",
            "Valladolid", "Vizcaya", "Zamora", "Zaragoza"};
        String[] tiposPago = {"Tarjeta", "Domiciliacion Bancaria"};
        String[] tiposTarjeta = {"Visa", "Mastercard", "American-Express"};
        String[] anyos = {"2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018",
            "2019", "2020"};
        String[] meses = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12"};
        final JComboBox formaPago = new JComboBox(tiposPago);
        JScrollPane JSPFormasPago = new JScrollPane(formaPago);
        final JComboBox tipoTarjeta = new JComboBox(tiposTarjeta);
        final JScrollPane JSPTiposTarjetas = new JScrollPane(tipoTarjeta);
        final JComboBox JCAnyos = new JComboBox(anyos);
        final JScrollPane JSPAnyos = new JScrollPane(JCAnyos);
        final JComboBox JCMeses = new JComboBox(meses);
        final JScrollPane JSPMeses = new JScrollPane(JCMeses);
        final JComboBox lstProv = new JComboBox(Provincia);
        final JComboBox lstVias = new JComboBox(TipoVia);

        final JTextField nombre = new JTextField(15);
        final JTextField apellidos = new JTextField(40);
        final JTextField dni = new JTextField(16);
        dni.setEditable(false);
        final JTextField email = new JTextField(40);
        final JTextField telefono = new JTextField(9);
        final JTextField calle = new JTextField(40);
        final JTextField num = new JTextField(5);
        final JTextField puerta = new JTextField(5);
        final JTextField esc = new JTextField(5);
        final JTextField piso = new JTextField(5);
        final JTextField ciudad = new JTextField(20);
        final JTextField codigopostal = new JTextField(5);
        final JTextField numtarjeta = new JTextField(16);
        final JTextField numcuenta = new JTextField(20);
        final JTextField cvv = new JTextField(4);
        final JTextField titular = new JTextField(50);
        JScrollPane prov = new JScrollPane(lstProv);
        JScrollPane vias = new JScrollPane(lstVias);
        final JPasswordField pass = new JPasswordField(15);
        final JPasswordField repass = new JPasswordField(15);
        final Cliente_VO c;
        c = cli.getCliente();

        nombre.setText(c.getNombre());
        nombre.addFocusListener(new FullSelectorListener());
        apellidos.setText(c.getApellidos());
        apellidos.addFocusListener(new FullSelectorListener());
        dni.setText(c.getNif());
        dni.addFocusListener(new FullSelectorListener());
        email.setText(c.getEmail());
        email.addFocusListener(new FullSelectorListener());
        telefono.setText(Integer.toString(c.getTelefono()));
        telefono.addFocusListener(new FullSelectorListener());
        calle.setText(c.getNomvia());
        calle.addFocusListener(new FullSelectorListener());
        num.setText(c.getNumcalle());
        num.addFocusListener(new FullSelectorListener());
        piso.setText(c.getPiso());
        piso.addFocusListener(new FullSelectorListener());
        puerta.setText(c.getPuerta());
        puerta.addFocusListener(new FullSelectorListener());
        esc.setText(c.getEscalera());
        esc.addFocusListener(new FullSelectorListener());
        ciudad.setText(c.getLocalidad());
        ciudad.addFocusListener(new FullSelectorListener());
        codigopostal.setText(Integer.toString(c.getCodpostal()));
        codigopostal.addFocusListener(new FullSelectorListener());
        titular.setText(c.getTitular());
        titular.addFocusListener(new FullSelectorListener());
        numtarjeta.setText(c.getNumtarjeta());
        numtarjeta.addFocusListener(new FullSelectorListener());
        numcuenta.setText(c.getNumcuenta());
        numcuenta.addFocusListener(new FullSelectorListener());
        cvv.setText(Integer.toString(c.getCodseg()));
        cvv.addFocusListener(new FullSelectorListener());
        pass.setText(c.getPass());
        pass.addFocusListener(new FullSelectorListener());
        repass.setText(c.getPass());
        repass.addFocusListener(new FullSelectorListener());

        numtarjeta.setToolTipText("Escriba los numeros de su tarjeta "
                + "sin espacion ni guiones");
        numcuenta.setToolTipText("Escriba los 20 digitos de su cuenta"
                + " sin espacios");
        cvv.setToolTipText("Escriba los digitos de CVV de su tarjeta");
        titular.setToolTipText("Escriba el nombre y apellidos del titular "
                + "de la tarjeta exactamente igual a como aparecen en la tarjeta");


        AyudaGB posPer = new AyudaGB();
        jppersonal.add(new Gap(GAP), posPer.nextRow());
        jppersonal.add(new JLabel(" > Nombre:"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(nombre, posPer.nextCol().width(2));
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextRow());
        jppersonal.add(new JLabel(" > Apellidos:"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(apellidos, posPer.nextCol().width(3));
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextRow());
        jppersonal.add(new JLabel(" > Documento Ident:"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(dni, posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextRow());
        jppersonal.add(new JLabel(" > Email:"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(email, posPer.nextCol().width(3));
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextRow());
        jppersonal.add(new JLabel(" > Telefono:"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(telefono, posPer.nextCol().width(1));
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextRow());
        jppersonal.add(new JLabel(" > Password:"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(pass, posPer.nextCol().width(1));
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextRow());
        jppersonal.add(new JLabel(" > Repita Password:"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(repass, posPer.nextCol().width(1));
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextRow());

        AyudaGB posdir = new AyudaGB();
        jpdireccion.add(new Gap(GAP), posdir.nextRow());
        lstVias.setSelectedItem(c.getTipovia());
        jpdireccion.add(vias, posdir.nextRow());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(calle, posdir.nextCol().width(13));
        jpdireccion.add(new Gap(GAP), posdir.nextRow());
        jpdireccion.add(new JLabel(" > Nº:"), posdir.nextRow());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(num, posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel(" > Piso:"), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(piso, posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel(" > Puerta:"), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(puerta, posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());

        jpdireccion.add(new JLabel(" > Escalera:"), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(esc, posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());

        jpdireccion.add(new Gap(GAP), posdir.nextRow());
        jpdireccion.add(new JLabel(" > Localidad:"), posdir.nextRow());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(ciudad, posdir.nextCol().width(9));
        jpdireccion.add(new Gap(GAP), posdir.nextRow());
        jpdireccion.add(new JLabel(" > Codigo Postal:"), posdir.nextRow());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(codigopostal, posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        lstProv.setSelectedItem(c.getProvincia());
        jpdireccion.add(new JLabel(" > Provincia:"), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(prov, posdir.nextCol().width(5));
        jpdireccion.add(new Gap(GAP), posdir.nextCol());

        final AyudaGB posban = new AyudaGB();

        jpbancarios.add(new Gap(GAP), posban.nextRow());
        jpbancarios.add(new JLabel(" > Tipo de pago:"), posban.nextRow());
        jpbancarios.add(new Gap(GAP), posban.nextCol());
        jpbancarios.add(JSPFormasPago, posban.nextCol());
        jpbancarios.add(new Gap(GAP), posban.nextCol());
        jpbancarios.add(new Gap(GAP), posban.nextRow());
        jpbancarios.add(new JLabel(" > Titular:"), posban.nextRow());
        jpbancarios.add(new Gap(GAP), posban.nextCol());
        jpbancarios.add(titular, posban.nextCol().width(2));
        jpbancarios.add(new Gap(GAP), posban.nextRow());
        jpbancarios.add(jpbancariosA, posban.nextRow().width(4));

        formaPago.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JLabel JLQueEs = new JLabel("¿Que es CVV?");
                JLQueEs.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent evt) {
                        Image CVV = new ImageIcon(getClass().getResource
                                ("/Imagenes/Quees.jpg")).getImage();
                        ImageIcon icon = new ImageIcon(CVV);
                        JOptionPane.showMessageDialog(panel, icon, "Que es CVV",
                                JOptionPane.DEFAULT_OPTION);
                    }
                });
                jpbancariosA.removeAll();
                Image Visa = new ImageIcon(getClass().getResource
                        ("/Imagenes/Tarjetas.jpg")).getImage().
                        getScaledInstance(150, 30, 0);
                ImageIcon visaIcon = new ImageIcon(Visa);
                JLabel JLVisa = new JLabel(visaIcon);


                tipoTarjeta.setSelectedItem(c.getTipotarjeta());

                if (formaPago.getSelectedItem().equals("Tarjeta")) {

                    jpbancariosA.add(new JLabel(" > Tipo Tarjeta:"), posban.nextRow());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(JSPTiposTarjetas, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(JLVisa, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextRow());
                    jpbancariosA.add(new JLabel(" > Numero de tarjeta:"), posban.nextRow());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(numtarjeta, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextRow());
                    jpbancariosA.add(new JLabel(" > Fecha de Caducidad:"), posban.nextRow());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    String fecha = c.getCaducidad();
                    if (!(fecha.isEmpty())) {
                        JCAnyos.setSelectedItem(fecha.substring(3, 6));
                        JCMeses.setSelectedItem(fecha.substring(0, 1));
                    }
                    jpbancariosA.add(JSPMeses, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(JSPAnyos, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextRow());
                    jpbancariosA.add(new JLabel(" > Codigo seguridad (CVV):"), posban.nextRow());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    if ((cvv.getText()).equals("0")) {
                        cvv.setText("");
                    }
                    jpbancariosA.add(cvv, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(JLQueEs, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                } else {
                    jpbancariosA.add(new Gap(GAP), posban.nextRow());
                    jpbancariosA.add(new JLabel(" > Numero de cuenta:"), posban.nextRow());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(numcuenta, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                }
                panel.updateUI();
            }
        });

        formaPago.setSelectedItem(c.getTipopago());
        AyudaGB pos = new AyudaGB();
        jbAceptar.setBackground(Color.GREEN);
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(jppersonal, pos.nextRow().width(6));
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(jpdireccion, pos.nextRow().width(6));
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(jpbancarios, pos.nextRow().width(6));
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(new Gap(GAP), pos.nextCol());
        panel.add(new Gap(GAP), pos.nextCol());
        panel.add(new Gap(GAP), pos.nextCol());
        panel.add(jbAceptar, pos.nextCol());
        panel.add(new Gap(GAP), pos.nextRow());
        final Cliente auxCli = cli;
        jbAceptar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                boolean completo = true;

                try {
                    if (nombre.getText().length() > 15) {
                        JOptionPane.showMessageDialog(panel, "Nombre demasiado largo, maximo 15 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (apellidos.getText().length() > 50) {
                        JOptionPane.showMessageDialog(panel, "Apellidos demasiado largos, maximo 50 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (dni.getText().length() > 9) {
                        JOptionPane.showMessageDialog(panel, "DNI demasiado largo, maximo 9 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (email.getText().length() > 100) {
                        JOptionPane.showMessageDialog(panel, "Email demasiado largo, maximo 100 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (pass.getText().length() > 20) {
                        JOptionPane.showMessageDialog(panel, "Contraseña demasiado larga, se admiten maximo 20 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (calle.getText().length() > 50) {
                        JOptionPane.showMessageDialog(panel, "Calle demasiado larga, maximo 50 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (num.getText().length() > 10) {
                        JOptionPane.showMessageDialog(panel, "Numero de calle demasiado largo, maximo 10 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (piso.getText().length() > 10) {
                        JOptionPane.showMessageDialog(panel, "Numero de piso demasiado largo, se admiten maximo 10 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (puerta.getText().length() > 10) {
                        JOptionPane.showMessageDialog(panel, "Numero de puerta demasiado largo, se admiten maximo 10 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (esc.getText().length() > 10) {
                        JOptionPane.showMessageDialog(panel, "Escalera demasiado larga, se admiten maximo 10 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (ciudad.getText().length() > 50) {
                        JOptionPane.showMessageDialog(panel, "Nombre de ciudad demasiado largo, maximo 50 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (formaPago.getSelectedIndex() == 0) {
                        // Pago con tarjeta
                        if (numtarjeta.getText().length() > 18) {
                            JOptionPane.showMessageDialog(panel, "Numero de la tarjeta demasiado largo, maximo 18 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (titular.getText().length() > 100) {
                            JOptionPane.showMessageDialog(panel, "Titular demasiado largo, maximo 100 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        // Pago con domiciliacion
                        if (titular.getText().length() > 100) {
                            JOptionPane.showMessageDialog(panel, "Titular demasiado largo, maximo 100 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    completo = completo
                            & !nombre.getText().isEmpty()
                            & !apellidos.getText().isEmpty()
                            & !lstVias.getSelectedItem().toString().isEmpty()
                            & !calle.getText().isEmpty()
                            & !num.getText().isEmpty()
                            & !ciudad.getText().isEmpty()
                            & !lstProv.getSelectedItem().toString().isEmpty()
                            & !codigopostal.getText().isEmpty()
                            & !dni.getText().isEmpty()
                            & !email.getText().isEmpty();

                    if (formaPago.getSelectedIndex() == 0) { // Hemos seleccionado el
                        // pago mediante tarjeta de credito
                        completo = completo
                                & !numtarjeta.getText().isEmpty()
                                & !titular.getText().isEmpty()
                                & !cvv.getText().isEmpty();
                    } else { // Hemos seleccionado pago mediante domiciliacion bancaria
                        completo = completo
                                & !titular.getText().isEmpty()
                                & !numcuenta.getText().isEmpty();
                    }

                    if (telefono.getText().isEmpty()) {
                        telefono.setText("0");
                    }
                    if (piso.getText().isEmpty()) {
                        piso.setText("0");
                    }
                    if (puerta.getText().isEmpty()) {
                        puerta.setText("");
                    }
                    if (esc.getText().isEmpty()) {
                        esc.setText("");
                    }
                    if (formaPago.getSelectedIndex() == 0) {
                        numcuenta.setText("0");
                    } else {
                        numtarjeta.setText("0");
                        cvv.setText("0");
                    }
                    if (!completo) {
                        JOptionPane.showMessageDialog(panel,
                                "Error; Debe rellenar todos los campos marcados con un *.\n",
                                "Error al Registrarse",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Construimos la fecha en el formato: mm/aaaa
                        String ftarj = JCMeses.getSelectedItem().toString()
                                + "/" + JCAnyos.getSelectedItem().toString();
                        if ((pass.getText().equals(repass.getText()))) {
                            int m = auxCli.modificarCliente(pass.getText(),
                                    nombre.getText(),
                                    apellidos.getText(),
                                    email.getText(),
                                    Integer.parseInt(telefono.getText()),
                                    lstVias.getSelectedItem().toString(),
                                    calle.getText(),
                                    num.getText(),
                                    piso.getText(),
                                    puerta.getText(),
                                    esc.getText(),
                                    ciudad.getText(),
                                    lstProv.getSelectedItem().toString(),
                                    Integer.parseInt(codigopostal.getText()),
                                    formaPago.getSelectedItem().toString(),
                                    tipoTarjeta.getSelectedItem().toString(),
                                    numtarjeta.getText(),
                                    ftarj,
                                    titular.getText(),
                                    Integer.parseInt(cvv.getText()),
                                    numcuenta.getText());
                            if (m == 0) {
                                JOptionPane.showMessageDialog(panel,
                                        "Modificacion realizada con exito.\n",
                                        "Modificacion Correcta",
                                        JOptionPane.DEFAULT_OPTION);
                            }
                        } else {
                            JOptionPane.showMessageDialog(panel,
                                    "Error; Las contraseñas no coinciden.\n",
                                    "Error al Registrarse",
                                    JOptionPane.ERROR_MESSAGE);
                            repass.setText("");
                            pass.addFocusListener(new FullSelectorListener());
                        }
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(panel,
                            "Compruebe el formato de los datos\n",
                            "Datos incorrectos",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    public JPanel misLineas(Cliente cli) {
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        final JPanel jpLineas = new JPanel(new GridBagLayout());
        jpLineas.setBackground(Color.WHITE);
        JPanel jpAlta = new JPanel(new GridBagLayout());
        jpAlta.setBackground(Color.WHITE);
        final JPanel jpBaja = new JPanel(new GridBagLayout());
        jpBaja.setBackground(Color.WHITE);
        JButton nueva = new JButton("Nueva Linea");
        final Cliente gc = cli;
        final JComboBox lstLineas = new JComboBox();

        jpLineas.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Lineas Actuales ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        jpAlta.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Alta Nuevas Lineas ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        jpBaja.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Dar de Baja Lineas ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        jpLineas.add(mostrarLineas(gc));

        AyudaGB posNew = new AyudaGB();
        jpAlta.add(new Gap(GAP), posNew.nextRow());
        jpAlta.add(new JLabel("Dar de alta una nueva linea"), posNew.nextRow());
        jpAlta.add(new Gap(GAP), posNew.nextCol());
        jpAlta.add(new Gap(GAP), posNew.nextRow());
        jpAlta.add(nueva, posNew.nextRow());
        jpAlta.add(new Gap(GAP), posNew.nextCol());
        jpAlta.add(new Gap(GAP), posNew.nextRow());
        nueva.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int num = gc.contratarLinea();
                JOptionPane.showMessageDialog(panel,
                        "Nueva linea dada de alta, numero: " + num + "\n",
                        "Nueva Linea OK",
                        JOptionPane.DEFAULT_OPTION);
                lstLineas.addItem(num);
                jpLineas.removeAll();
                jpLineas.add(mostrarLineas(gc));
                panel.updateUI();

            }
        });

        JScrollPane jspLineas = new JScrollPane(lstLineas);
        ArrayList listaLin = cli.listaLineasActivas();
        lstLineas.addItem("Seleccione el numero de telefono");
        for (int i = 0; i < listaLin.size(); i++) {
            lstLineas.addItem(Integer.toString(((Linea_VO) listaLin.get(i)).getNumtlf()));
        }
        AyudaGB posDel = new AyudaGB();
        JButton baja = new JButton("Dar de Baja");
        jpBaja.add(new Gap(GAP), posDel.nextRow());
        jpBaja.add(new JLabel("Dar de baja una linea"), posDel.nextRow());

        jpBaja.add(new Gap(GAP), posDel.nextCol());
        jpBaja.add(new Gap(GAP), posDel.nextRow());
        jpBaja.add(jspLineas, posDel.nextRow());
        jpBaja.add(new Gap(GAP), posDel.nextCol());
        jpBaja.add(new Gap(GAP), posDel.nextRow());
        jpBaja.add(baja, posDel.nextRow());
        jpBaja.add(new Gap(GAP), posDel.nextCol());
        jpBaja.add(new Gap(GAP), posDel.nextRow());
        baja.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (lstLineas.getSelectedIndex() != 0) {
                    gc.DarBajaLinea(Integer.parseInt(lstLineas.getSelectedItem().toString()));
                    JOptionPane.showMessageDialog(panel,
                            "Linea dada de baja, numero: "
                            + lstLineas.getSelectedItem().toString() + "\n",
                            "Baja de linea OK",
                            JOptionPane.DEFAULT_OPTION);
                    lstLineas.removeItemAt(lstLineas.getSelectedIndex());
                    jpLineas.removeAll();
                    jpLineas.add(mostrarLineas(gc));
                    panel.updateUI();
                }


            }
        });

        AyudaGB pos = new AyudaGB();
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(jpLineas, pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(jpAlta, pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(jpBaja, pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());

        return panel;
    }

    private JPanel mostrarLineas(Cliente gc) {
        final JPanel jpLineas = new JPanel(new GridBagLayout());
        jpLineas.setBackground(Color.WHITE);
        ArrayList lista = gc.listaLineasActivas();
        AyudaGB posLin = new AyudaGB();
        jpLineas.add(new Gap(GAPGRANDE), posLin.nextRow());
        jpLineas.add(new Gap(GAP), posLin.width(5));
        jpLineas.add(new JLabel("Lista de las lineas telefonicas: "), posLin.nextRow());
        jpLineas.add(new Gap(GAP), posLin.nextCol());
        jpLineas.add(new Gap(GAP), posLin.nextRow());
        jpLineas.add(new Gap(GAP), posLin.nextRow());
        int n = lista.size();
        for (int i = 0; i < n; i++) {
            jpLineas.add(new JLabel(Integer.toString(((Linea_VO) lista.get(i)).getNumtlf())), posLin.nextRow());
            jpLineas.add(new Gap(GAP), posLin.nextCol());
        }


        return jpLineas;

    }

    public JPanel registrarse(final Cliente gc) {
        final JPanel panel = new JPanel(new GridBagLayout());
        JPanel jppersonal = new JPanel(new GridBagLayout());
        JPanel jpdireccion = new JPanel(new GridBagLayout());
        JPanel jpbancarios = new JPanel(new GridBagLayout());
        final JPanel jpbancariosA = new JPanel(new GridBagLayout());
        JButton jbAceptar = new JButton("Aceptar");
        jppersonal.setBackground(Color.WHITE);
        jpdireccion.setBackground(Color.WHITE);
        jpbancarios.setBackground(Color.WHITE);
        jpbancariosA.setBackground(Color.WHITE);
        panel.setBackground(Color.WHITE);
        jppersonal.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Datos Personales ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        jpdireccion.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Direccion ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        jpbancarios.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Datos Bancarios ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));


        String[] TipoVia = {"", "Alameda", "Autopista", "Autovia", "Avenida", "Calle",
            "Callejon", "Camino", "Cañada", "Carrera", "Carretera",
            "Circunvalacion", "Cuesta", "Glorieta", "Parque", "Pasaje",
            "Paseo", "Plaza", "Rambla", "Ronda", "Sendero", "Travesia",
            "Urbanizacion", "Via"};
        String[] Provincia = {"", "Alava", "Albacete", "Alicante", "Almeria", "Asturias", "Avila",
            "Badajoz", "Barcelona", "Burgos", "Caceres", "Cadiz", "Cantabria",
            "Castellon", "Ceuta", "Cuidad Real", "Cordoba", "La Coruña",
            "Cuenca", "Gerona", "Granada", "Guadalajara", "Guipuzcoa",
            "Huelva", "Huesca", "Islas Baleares", "Jaen", "Leon", "Lerida",
            "Lugo", "Madrid", "Malaga", "Melilla", "Murcia", "Navarra",
            "Orense", "Palencia", "Las Palmas", "Pontevedra", "La Rioja",
            "Salamanca", "Segovia", "Sevilla", "Soria", "Tarragona",
            "Santa Cruz de Tenerife", "Teruel", "Toledo", "Valencia",
            "Valladolid", "Vizcaya", "Zamora", "Zaragoza"};
        String[] tiposPago = {"Tarjeta", "Domiciliacion Bancaria"};
        String[] tiposTarjeta = {"Visa", "Mastercard", "American-Express"};
        String[] anyos = {"2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018",
            "2019", "2020"};
        String[] meses = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12"};
        final JComboBox formaPago = new JComboBox(tiposPago);
        JScrollPane JSPFormasPago = new JScrollPane(formaPago);
        final JComboBox tipoTarjeta = new JComboBox(tiposTarjeta);
        final JScrollPane JSPTiposTarjetas = new JScrollPane(tipoTarjeta);
        final JComboBox JCAnyos = new JComboBox(anyos);
        final JScrollPane JSPAnyos = new JScrollPane(JCAnyos);
        final JComboBox JCMeses = new JComboBox(meses);
        final JScrollPane JSPMeses = new JScrollPane(JCMeses);
        final JComboBox lstProv = new JComboBox(Provincia);
        final JComboBox lstVias = new JComboBox(TipoVia);
        final JTextField nombre = new JTextField(15);
        final JTextField apellidos = new JTextField(20);
        final JTextField dni = new JTextField(16);
        final JTextField email = new JTextField(15);
        final JTextField telefono = new JTextField(9);
        final JTextField calle = new JTextField(40);
        final JTextField num = new JTextField(5);
        final JTextField puerta = new JTextField(5);
        final JTextField esc = new JTextField(5);
        final JTextField piso = new JTextField(5);
        final JTextField ciudad = new JTextField(20);
        final JTextField codigopostal = new JTextField(5);
        final JTextField numtarjeta = new JTextField(16);
        final JTextField numcuenta = new JTextField(20);
        final JTextField cvv = new JTextField(4);
        final JTextField titular = new JTextField(50);
        JScrollPane prov = new JScrollPane(lstProv);
        JScrollPane vias = new JScrollPane(lstVias);
        final JPasswordField pass = new JPasswordField(15);
        final JPasswordField repass = new JPasswordField(15);


        nombre.addFocusListener(new FullSelectorListener());
        apellidos.addFocusListener(new FullSelectorListener());
        dni.addFocusListener(new FullSelectorListener());
        email.addFocusListener(new FullSelectorListener());
        telefono.addFocusListener(new FullSelectorListener());
        calle.addFocusListener(new FullSelectorListener());
        num.addFocusListener(new FullSelectorListener());
        piso.addFocusListener(new FullSelectorListener());
        puerta.addFocusListener(new FullSelectorListener());
        esc.addFocusListener(new FullSelectorListener());
        ciudad.addFocusListener(new FullSelectorListener());
        codigopostal.addFocusListener(new FullSelectorListener());
        titular.addFocusListener(new FullSelectorListener());
        numtarjeta.addFocusListener(new FullSelectorListener());
        numcuenta.addFocusListener(new FullSelectorListener());
        cvv.addFocusListener(new FullSelectorListener());
        pass.addFocusListener(new FullSelectorListener());
        repass.addFocusListener(new FullSelectorListener());

        numtarjeta.setToolTipText("Escriba los numeros de su tarjeta "
                + "sin espacion ni guiones");
        numcuenta.setToolTipText("Escriba los 20 digitos de su cuenta"
                + " sin espacios");
        cvv.setToolTipText("Escriba los digitos de CVV de su tarjeta");
        titular.setToolTipText("Escriba el nombre y apellidos del titular "
                + "de la tarjeta exactamente igual a como aparecen en la tarjeta");

        AyudaGB posPer = new AyudaGB();

        jppersonal.add(new Gap(GAP), posPer.nextRow());

        jppersonal.add(new JLabel("Nombre*"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new JLabel("Apellidos*"), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new JLabel("Documento Identificacion*"), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());

        jppersonal.add(new Gap(GAP), posPer.nextRow());

        jppersonal.add(nombre, posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(apellidos, posPer.nextCol().width(3));
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(dni, posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());

        jppersonal.add(new Gap(GAP), posPer.nextRow());

        jppersonal.add(new JLabel("Email*"), posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new JLabel("Telefono"), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol().width(2));
        jppersonal.add(new JLabel("Contraseña*"), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(new JLabel("Repetir Contraseña*"), posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());

        jppersonal.add(new Gap(GAP), posPer.nextRow());

        jppersonal.add(email, posPer.nextRow());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(telefono, posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol().width(2));
        jppersonal.add(pass, posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());
        jppersonal.add(repass, posPer.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());

        jppersonal.add(new Gap(GAP), posPer.nextRow());

        AyudaGB posdir = new AyudaGB();

        jpdireccion.add(new Gap(GAP), posdir.nextRow());

        jpdireccion.add(new JLabel("Tipo de Via*"), posdir.nextRow());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel("Nombre de Via*"), posdir.nextCol());
        jppersonal.add(new Gap(GAP), posPer.nextCol());

        jpdireccion.add(new Gap(GAP), posdir.nextRow());

        jpdireccion.add(vias, posdir.nextRow());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(calle, posdir.nextCol().width(3));
        jpdireccion.add(new Gap(GAP), posdir.nextCol());

        jpdireccion.add(new Gap(GAP), posdir.nextRow());

        jpdireccion.add(new JLabel("Numero*"), posdir.nextRow());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel("Piso"), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel("Puerta"), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel("Escalera"), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());

        jpdireccion.add(new Gap(GAP), posdir.nextRow());

        jpdireccion.add(num, posdir.nextRow());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(piso, posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(puerta, posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(esc, posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());

        jpdireccion.add(new Gap(GAP), posdir.nextRow());

        jpdireccion.add(new JLabel("Localidad*"), posdir.nextRow());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel("Provincia*"), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(new JLabel("Codigo Postal*"), posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());

        jpdireccion.add(new Gap(GAP), posdir.nextRow());

        jpdireccion.add(ciudad, posdir.nextRow());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(prov, posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());
        jpdireccion.add(codigopostal, posdir.nextCol());
        jpdireccion.add(new Gap(GAP), posdir.nextCol());

        final AyudaGB posban = new AyudaGB();

        jpbancarios.add(new JLabel("Titular:"), posban.nextRow());
        jpbancarios.add(new Gap(GAP), posban.nextCol());
        jpbancarios.add(titular, posban.nextCol().width(3));

        jpbancarios.add(new Gap(GAP), posban.nextRow());

        jpbancarios.add(new JLabel("Tipo de pago*"), posban.nextRow());
        jpbancarios.add(new Gap(GAP), posban.nextCol());
        jpbancarios.add(JSPFormasPago, posban.nextCol());
        jpbancarios.add(new Gap(GAP), posban.nextCol());

        jpbancarios.add(new Gap(GAP), posban.nextRow());

        jpbancarios.add(jpbancariosA, posban.nextRow().width(4));

        formaPago.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JLabel JLQueEs = new JLabel("¿Que es CVV?");
                JLQueEs.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent evt) {
                        Image CVV = new ImageIcon(getClass().getResource
                                ("/Imagenes/Quees.jpg")).getImage();
                        ImageIcon icon = new ImageIcon(CVV);
                        JOptionPane.showMessageDialog(panel, icon, "Que es CVV",
                                JOptionPane.DEFAULT_OPTION);
                    }
                });
                jpbancariosA.removeAll();
                Image Visa = new ImageIcon(getClass().getResource
                        ("/Imagenes/Tarjetas.jpg")).getImage().
                        getScaledInstance(150, 30, 0);
                ImageIcon visaIcon = new ImageIcon(Visa);
                JLabel JLVisa = new JLabel(visaIcon);

                if (formaPago.getSelectedIndex() == 0) {

                    jpbancariosA.add(new JLabel("Tipo Tarjeta*"), posban.nextRow());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(JSPTiposTarjetas, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(JLVisa, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());

                    jpbancariosA.add(new Gap(GAP), posban.nextRow());

                    jpbancariosA.add(new JLabel("Numero Tarjeta*"), posban.nextRow());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(numtarjeta, posban.nextCol().width(3));
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());

                    jpbancariosA.add(new Gap(GAP), posban.nextRow());

                    jpbancariosA.add(new JLabel("Fecha Caducidad*"), posban.nextRow());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(JSPMeses, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(JSPAnyos, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());

                    jpbancariosA.add(new Gap(GAP), posban.nextRow());

                    jpbancariosA.add(new JLabel("Codigo seguridad (CVV)*"), posban.nextRow());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(cvv, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(JLQueEs, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                } else {
                    jpbancariosA.add(new JLabel("Numero Cuenta*"), posban.nextRow());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                    jpbancariosA.add(numcuenta, posban.nextCol());
                    jpbancariosA.add(new Gap(GAP), posban.nextCol());
                }
                panel.updateUI();
            }
        });
        formaPago.setSelectedIndex(0);

        AyudaGB pos = new AyudaGB();

        jbAceptar.setBackground(Color.GREEN);

        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(jppersonal, pos.nextRow().width(6));
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(jpdireccion, pos.nextRow().width(6));
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(jpbancarios, pos.nextRow().width(6));
        panel.add(new Gap(GAP), pos.nextRow());
        panel.add(new Gap(GAP), pos.nextCol());
        panel.add(new Gap(GAP), pos.nextCol());
        panel.add(new Gap(GAP), pos.nextCol());
        panel.add(jbAceptar, pos.nextCol());
        panel.add(new Gap(GAP), pos.nextRow());
        jbAceptar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                boolean completo = true;
                try {
                    if (nombre.getText().length() > 15) {
                        JOptionPane.showMessageDialog(panel, "Nombre demasiado largo, maximo 15 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (apellidos.getText().length() > 50) {
                        JOptionPane.showMessageDialog(panel, "Apellidos demasiado largos, maximo 50 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (dni.getText().length() > 9) {
                        JOptionPane.showMessageDialog(panel, "DNI demasiado largo, maximo 9 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (email.getText().length() > 100) {
                        JOptionPane.showMessageDialog(panel, "Email demasiado largo, maximo 100 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (pass.getText().length() > 20) {
                        JOptionPane.showMessageDialog(panel, "Contraseña demasiado larga, se admiten maximo 20 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (calle.getText().length() > 50) {
                        JOptionPane.showMessageDialog(panel, "Calle demasiado larga, maximo 50 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (num.getText().length() > 10) {
                        JOptionPane.showMessageDialog(panel, "Numero de calle demasiado largo, maximo 10 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (piso.getText().length() > 10) {
                        JOptionPane.showMessageDialog(panel, "Numero de piso demasiado largo, se admiten maximo 10 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (puerta.getText().length() > 10) {
                        JOptionPane.showMessageDialog(panel, "Numero de puerta demasiado largo, se admiten maximo 10 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (esc.getText().length() > 10) {
                        JOptionPane.showMessageDialog(panel, "Escalera demasiado larga, se admiten maximo 10 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (ciudad.getText().length() > 50) {
                        JOptionPane.showMessageDialog(panel, "Nombre de ciudad demasiado largo, maximo 50 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (formaPago.getSelectedIndex() == 0) {
                        // Pago con tarjeta
                        if (numtarjeta.getText().length() > 18) {
                            JOptionPane.showMessageDialog(panel, "Numero de la tarjeta demasiado largo, maximo 18 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (titular.getText().length() > 100) {
                            JOptionPane.showMessageDialog(panel, "Titular demasiado largo, maximo 100 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        // Pago con domiciliacion
                        if (titular.getText().length() > 100) {
                            JOptionPane.showMessageDialog(panel, "Titular demasiado largo, maximo 100 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    completo = completo
                            && !nombre.getText().isEmpty()
                            && !apellidos.getText().isEmpty()
                            && !lstVias.getSelectedItem().toString().isEmpty()
                            && !calle.getText().isEmpty()
                            && !num.getText().isEmpty()
                            && !ciudad.getText().isEmpty()
                            && !lstProv.getSelectedItem().toString().isEmpty()
                            && !codigopostal.getText().isEmpty()
                            && !dni.getText().isEmpty()
                            && !email.getText().isEmpty()
                            && !pass.getText().isEmpty()
                            && !repass.getText().isEmpty();

                    if (formaPago.getSelectedIndex() == 0) { // Hemos seleccionado el
                        // pago mediante tarjeta de credito
                        completo = completo
                                && !numtarjeta.getText().isEmpty()
                                && !titular.getText().isEmpty()
                                && !cvv.getText().isEmpty();
                    } else { // Hemos seleccionado pago mediante domiciliacion bancaria
                        completo = completo
                                && !titular.getText().isEmpty()
                                && !numcuenta.getText().isEmpty();
                    }

                    if (telefono.getText().isEmpty()) {
                        telefono.setText("0");
                    }
                    if (piso.getText().isEmpty()) {
                        piso.setText("");
                    }
                    if (puerta.getText().isEmpty()) {
                        puerta.setText("");
                    }
                    if (esc.getText().isEmpty()) {
                        esc.setText("");
                    }
                    if (formaPago.getSelectedIndex() == 0) {
                        numcuenta.setText("0");
                    } else {
                        numtarjeta.setText("0");
                        cvv.setText("0");
                    }
                    if (!completo) {
                        JOptionPane.showMessageDialog(panel,
                                "Error; Debe rellenar todos los campos marcados con un *.\n",
                                "Error al Registrarse",
                                JOptionPane.ERROR_MESSAGE);
                        if (nombre.getText().isEmpty()) {
                            nombre.setBackground(Color.RED);
                        } else {
                            nombre.setBackground(Color.WHITE);
                        }
                        if (pass.getText().isEmpty()) {
                            pass.setBackground(Color.RED);
                        } else {
                            pass.setBackground(Color.WHITE);
                        }
                        if (repass.getText().isEmpty()) {
                            repass.setBackground(Color.RED);
                        } else {
                            repass.setBackground(Color.WHITE);
                        }
                        if (apellidos.getText().isEmpty()) {
                            apellidos.setBackground(Color.RED);
                        } else {
                            apellidos.setBackground(Color.WHITE);
                        }
                        if (lstVias.getSelectedItem().toString().isEmpty()) {
                            lstVias.setBackground(Color.RED);
                        } else {
                            lstVias.setBackground(Color.WHITE);
                        }
                        if (calle.getText().isEmpty()) {
                            calle.setBackground(Color.RED);
                        } else {
                            calle.setBackground(Color.WHITE);
                        }
                        if (num.getText().isEmpty()) {
                            num.setBackground(Color.RED);
                        } else {
                            num.setBackground(Color.WHITE);
                        }
                        if (ciudad.getText().isEmpty()) {
                            ciudad.setBackground(Color.RED);
                        } else {
                            ciudad.setBackground(Color.WHITE);
                        }
                        if (lstProv.getSelectedItem().toString().isEmpty()) {
                            lstProv.setBackground(Color.RED);
                        } else {
                            lstProv.setBackground(Color.WHITE);
                        }
                        if (codigopostal.getText().isEmpty()) {
                            codigopostal.setBackground(Color.RED);
                        } else {
                            codigopostal.setBackground(Color.WHITE);
                        }
                        if (dni.getText().isEmpty()) {
                            dni.setBackground(Color.RED);
                        } else {
                            dni.setBackground(Color.WHITE);
                        }
                        if (email.getText().isEmpty()) {
                            email.setBackground(Color.RED);
                        } else {
                            email.setBackground(Color.WHITE);
                        }
                        if (titular.getText().isEmpty()) {
                            titular.setBackground(Color.RED);
                        } else {
                            titular.setBackground(Color.WHITE);
                        }
                        if (formaPago.getSelectedIndex() == 0) { // Hemos seleccionado el
                            if (numtarjeta.getText().isEmpty()) {
                                numtarjeta.setBackground(Color.RED);
                            } else {
                                numtarjeta.setBackground(Color.WHITE);
                            }
                            if (cvv.getText().isEmpty()) {
                                cvv.setBackground(Color.RED);
                            } else {
                                cvv.setBackground(Color.WHITE);
                            }
                        } else { // Hemos seleccionado pago mediante domiciliacion bancaria
                            if (numcuenta.getText().isEmpty()) {
                                numcuenta.setBackground(Color.RED);
                            } else {
                                numcuenta.setBackground(Color.WHITE);
                            }
                        }
                    } else {
                        // Construimos la fecha en el formato: mm/aaaa
                        String ftarj = JCMeses.getSelectedItem().toString()
                                + "/" + JCAnyos.getSelectedItem().toString();

                        if ((pass.getText().equals(repass.getText()))) {
                            int v = gc.registrarCliente(dni.getText(), pass.getText(),
                                    nombre.getText(),
                                    apellidos.getText(),
                                    email.getText(),
                                    Integer.parseInt(telefono.getText()),
                                    lstVias.getSelectedItem().toString(),
                                    calle.getText(),
                                    num.getText(),
                                    piso.getText(),
                                    puerta.getText(),
                                    esc.getText(),
                                    ciudad.getText(),
                                    lstProv.getSelectedItem().toString(),
                                    Integer.parseInt(codigopostal.getText()),
                                    formaPago.getSelectedItem().toString(),
                                    tipoTarjeta.getSelectedItem().toString(),
                                    numtarjeta.getText(),
                                    ftarj,
                                    titular.getText(),
                                    Integer.parseInt(cvv.getText()),
                                    numcuenta.getText());
                            if (v == MSJ.OK) {
                                JOptionPane.showMessageDialog(panel,
                                        "Cliente registrado con exito.\n",
                                        "Registro Correcto",
                                        JOptionPane.DEFAULT_OPTION);
                            } else {
                                JOptionPane.showMessageDialog(panel,
                                        MSJ.Mensaje(v),
                                        "Registro Erroneo",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(panel,
                                    "Error; Las contraseñas no coinciden.\n",
                                    "Error al Registrarse",
                                    JOptionPane.ERROR_MESSAGE);
                            repass.setText("");
                            pass.addFocusListener(new FullSelectorListener());
                        }
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(panel,
                            "Compruebe el formato de los datos\n",
                            "Datos incorrectos",
                            JOptionPane.ERROR_MESSAGE);
                    try {
                        Integer.parseInt(telefono.getText());
                        telefono.setBackground(Color.WHITE);
                    } catch (Exception ex) {
                        telefono.setBackground(Color.RED);
                    }
                    try {
                        Integer.parseInt(codigopostal.getText());
                        codigopostal.setBackground(Color.WHITE);
                    } catch (Exception ex) {
                        codigopostal.setBackground(Color.RED);
                    }
                    try {
                        Integer.parseInt(cvv.getText());
                        cvv.setBackground(Color.WHITE);
                    } catch (Exception ex) {
                        cvv.setBackground(Color.RED);
                    }
                }
            }
        });
        return panel;
    }

    private String codigosBancarios(int codigo) {
        switch (codigo) {
            case (9):
                return "FINANZIA, BANCO DE CREDITO";
            case (38):
                return "BANESTO BANCO DE EMISIONES";
            case (49):
                return "BANCO SANTANDER";
            case (46):
                return "BANCO GALLEGO";
            case (58):
                return "BNP PARIBAS ESPAÑA";
            case (75):
                return "BANCO POPULAR ESPAÑOL";
            case (11):
                return "ALLFUNDS BANK";
            case (99):
                return "ALTAE BANCO";
            case (94):
                return "RBC DEXIA INVESTOR SERVICES ESPAÑA";
            case (136):
                return "ARESBANK";
            case (490):
                return "BANCA CIVICA";
            case (61):
                return "BANCA MARCH";
            case (78):
                return "BANCA PUEYO";
            case (188):
                return "BANCO ALCALA";
            case (83):
                return "BANCO ALICANTINO DE COMERCIO";
            case (86):
                return "BANCO BANIF";
            case (182):
                return "BANCO BILBAO VIZCAYA ARGENTARIA";
            case (0130):
                return "BANCO CAIXA GERAL";
            case (0234):
                return "BANCO CAMINOS";
            case (0225):
                return "BANCO CETELEM";
            case (198):
                return "BANCO COOPERATIVO ESPAÑOL";
            case (91):
                return "BANCO DE ALBACETE";
            case (0003):
                return "BANCO DE DEPOSITOS";
            case (186):
                return "BANCO DE FINANZAS E INVERSIONES";
            case (0142):
                return "BANCO DE LA PEQUEÑA Y MEDIANA EMPRESA";
            case (59):
                return "BANCO DE MADRID";
            case (0132):
                return "BANCO DE PROMOCION DE NEGOCIOS";
            case (81):
                return "BANCO DE SABADELL";
            case (0063):
                return "BANCO DE SERVICIOS FINANCIEROS CAJA MADRID – MAPFRE";
            case (93):
                return "BANCO DE VALENCIA";
            case (0057):
                return "BANCO DEPOSITARIO BBVA";
            case (0030):
                return "BANCO ESPAÑOL DE CREDITO";
            case (0031):
                return "BANCO ETCHEVERRIA";
            case (184):
                return "BANCO EUROPEO DE FINANZAS";
            case (0220):
                return "BANCO FINANTIA SOFINLOC";
            case (0042):
                return "BANCO GUIPUZCOANO";
            case (0217):
                return "BANCO HALIFAX HISPANIA";
            case (113):
                return "BANCO INDUSTRIAL DE BILBAO";
            case (0232):
                return "BANCO INVERSIS";
            case (0115):
                return "BANCO LIBERTA";
            case (121):
                return "BANCO OCCIDENTAL";
            case (72):
                return "BANCO PASTOR";
            case (0235):
                return "BANCO PICHINCHA ESPAÑA";
            case (0216):
                return "BANCO POPULAR HIPOTECARIO";
            case (185):
                return "BANCO URQUIJO SABADELL BANCA PRIVADA";
            case (0125):
                return "BANCOFAR";
            case (229):
                return "BANCOPOPULAR-E";
            case (128):
                return "BANKINTER";
            case (138):
                return "BANKOA";
            case (219):
                return "BANQUE MAROCAINE DU COMMERCE EXTERIEUR INTERNATIONAL";
            case (65):
                return "BARCLAYS BANK";
            case (129):
                return "BBVA BANCO DE FINANCIACION";
            case (0122):
                return "CITIBANK ESPAÑA";
            case (19):
                return "DEUTSCHE BANK";
            case (0231):
                return "DEXIA SABADELL";
            case (0211):
                return "EBN BANCO DE NEGOCIOS";
            case (0223):
                return "GENERAL ELECTRIC CAPITAL BANK";
            case (133):
                return "MICROBANK DE LA CAIXA";
            case (73):
                return "OPEN BANK";
            case (0233):
                return "POPULAR BANCA PRIVADA";
            case (200):
                return "PRIVAT BANK DEGROOF";
            case (0224):
                return "SANTANDER CONSUMER FINANCE";
            case (0036):
                return "SANTANDER INVESTMENT";
            case (1490):
                return "SELF TRADE BANK";
            case (0226):
                return "UBS BANK";
            case (0227):
                return "UNOE BANK";
            case (2054):
                return "CAJA DE AHORROS Y MONTE DE PIEDAD DE NAVARRA";
            case (2095):
                return "BILBAO BIZKAIA KUTXA, AURREZKI KUTXA ETA BAHITETXEA";
            case (2080):
                return "CAIXA DE AFORROS DE VIGO, OURENSE E PONTEVEDRA (CAIXANOVA)";
            case (2040):
                return "CAIXA D’ESTALVIS COMARCAL DE MANLLEU";
            case (2013):
                return "CAIXA D’ESTALVIS DE CATALUNYA";
            case (2030):
                return "CAIXA D’ESTALVIS DE GIRONA";
            case (2041):
                return "CAIXA D’ESTALVIS DE MANRESA";
            case (2059):
                return "CAIXA D’ESTALVIS DE SABADELL";
            case (2073):
                return "CAIXA D’ESTALVIS DE TARRAGONA";
            case (2074):
                return "CAIXA D’ESTALVIS DE TERRASSA";
            case (2081):
                return "CAIXA D’ESTALVIS DEL PENEDES";
            case (2042):
                return "CAIXA D’ESTALVIS LAIETANA";
            case (2032):
                return "CAJA DE AHORRO PROVINCIAL DE GUADALAJARA";
            case (2048):
                return "CAJA DE AHORROS DE ASTURIAS";
            case (2105):
                return "CAJA DE AHORROS DE CASTILLA-LA MANCHA";
            case (2091):
                return "CAJA DE AHORROS DE GALICIA";
            case (2086):
                return "CAJA DE AHORROS DE LA INMACULADA DE ARAGON";
            case (2037):
                return "CAJA DE AHORROS DE LA RIOJA";
            case (2043):
                return "CAJA DE AHORROS DE MURCIA";
            case (2104):
                return "CAJA DE AHORROS DE SALAMANCA Y SORIA";
            case (2066):
                return "CAJA DE AHORROS DE SANTANDER Y CANTABRIA";
            case (2077):
                return "CAJA DE AHORROS DE VALENCIA, CASTELLON Y ALICANTE, BANCAJA";
            case (2097):
                return "CAJA DE AHORROS DE VITORIA Y ALAVA- ARABA ETA GASTEIZKO AURREZKI KUTXA";
            case (2090):
                return "CAJA DE AHORROS DEL MEDITERRANEO";
            case (2018):
                return "CAJA DE AHORROS MUNICIPAL DE BURGOS";
            case (2094):
                return "CAJA DE AHORROS Y MONTE DE PIEDAD DE AVILA";
            case (2024):
                return "CAJA DE AHORROS Y MONTE DE PIEDAD DE CORDOBA";
            case (2099):
                return "CAJA DE AHORROS Y MONTE DE PIEDAD DE EXTREMADURA";
            case (2101):
                return "CAJA DE AHORROS Y MONTE DE PIEDAD DE GIPUZKOA Y SAN SEBASTIAN";
            case (2051):
                return "CAJA DE AHORROS Y MONTE DE PIEDAD DE LAS BALEARES";
            case (2038):
                return "CAJA DE AHORROS Y MONTE DE PIEDAD DE MADRID";
            case (2045):
                return "CAJA DE AHORROS Y MONTE DE PIEDAD DE ONTINYENT";
            case (2069):
                return "CAJA DE AHORROS Y MONTE DE PIEDAD DE SEGOVIA";
            case (2085):
                return "CAJA DE AHORROS Y MONTE DE PIEDAD DE ZARAGOZA, ARAGON Y RIOJA (IBERCAJA)";
            case (2017):
                return "CAJA DE AHORROS Y MONTE DE PIEDAD DEL CIRCULO CATOLICO DE OBREROS DE BURGOS";
            case (2100):
                return "CAJA DE AHORROS Y PENSIONES DE BARCELONA";
            case (2096):
                return "CAJA ESPAÑA DE INVERSIONES, CAJA DE AHORROS Y MONTE DE PIEDAD";
            case (2065):
                return "CAJA GENERAL DE AHORROS DE CANARIAS";
            case (2031):
                return "CAJA GENERAL DE AHORROS DE GRANADA";
            case (2052):
                return "CAJA INSULAR DE AHORROS DE CANARIAS";
            case (2056):
                return "COLONYA – CAIXA D’ESTALVIS DE POLLENSA";
            case (2000):
                return "CONFEDERACION ESPAÑOLA DE CAJAS DE AHORROS";
            case (2103):
                return "MONTE DE PIEDAD Y CAJA DE AHORROS DE RONDA, CADIZ, ALMERIA, MALAGA, ANTEQUERA Y JAEN (UNICAJA)";
            case (2106):
                return "MONTE DE PIEDAD Y CAJA DE AHORROS SAN FERNANDO DE HUELVA, JEREZ Y SEVILLA";
            case (2010):
                return "MONTE DE PIEDAD Y CAJA GENERAL DE AHORROS DE BADAJOZ";
            case (3025):
                return "CAIXA DE C. DELS ENGINYERS-C.C. INGENIEROS";
            case (3159):
                return "CAIXA POPULAR-CAIXA RURAL";
            case (3186):
                return "CAIXA RURAL ALBALAT DELS SORELLS";
            case (3045):
                return "CAIXA RURAL ALTEA";
            case (3162):
                return "CAIXA RURAL BENICARLO";
            case (3117):
                return "CAIXA RURAL D'ALGEMESI";
            case (3147):
                return "CAIXA RURAL DE BALEARS";
            case (3105):
                return "CAIXA RURAL DE CALLOSA D'EN SARRIA";
            case (3096):
                return "CAIXA RURAL DE L'ALCUDIA";
            case (3123):
                return "CAIXA RURAL DE TURIS";
            case (3070):
                return "CAIXA RURAL GALEGA";
            case (3111):
                return "CAIXA RURAL LA VALL 'S. ISIDRO'";
            case (3102):
                return "CAIXA RURAL S. VICENT FERRER DE LA VALL D'UIXO";
            case (3174):
                return "CAIXA RURAL VINAROS";
            case (3160):
                return "CAIXA R.S.JOSEP DE VILAVELLA";
            case (3166):
                return "CAIXA RURAL LES COVES DE VINROMA";
            case (3118):
                return "CAIXA RURAL TORRENT";
            case (3094):
                return "CAJA CAMPO";
            case (3183):
                return "CAJA DE ARQUITECTOS";
            case (3146):
                return "CAJA DE CREDITO COOPERATIVO";
            case (3029):
                return "CAJA DE CREDITO DE PETREL";
            case (3035):
                return "CAJA LABORAL POPULAR";
            case (3115):
                return "CAJA RURAL 'NUESTRA MADRE DEL SOL'";
            case (3189):
                return "CAJA RURAL ARAGONESA Y DE LOS PIRINEOS";
            case (3089):
                return "CAJA RURAL BAENA NTRA. SRA. GUADALUPE";
            case (3114):
                return "CAJA RURAL CASTELLON S. ISIDRO";
            case (3110):
                return "CAJA RURAL CATOLICO AGRARIA";
            case (3005):
                return "CAJA RURAL CENTRAL";
            case (3116):
                return "CAJA RURAL COMARCAL DE MOTA DEL CUERVO";
            case (3056):
                return "CAJA RURAL DE ALBACETE";
            case (3150):
                return "CAJA RURAL DE ALBAL";
            case (3179):
                return "CAJA RURAL DE ALGINET";
            case (3001):
                return "CAJA RURAL DE ALMENDRALEJO";
            case (3021):
                return "CAJA RURAL DE ARAGON";
            case (3059):
                return "CAJA RURAL DE ASTURIAS";
            case (3138):
                return "CAJA RURAL DE BETXI";
            case (3060):
                return "CAJA RURAL DE BURGOS";
            case (3177):
                return "CAJA RURAL DE CANARIAS";
            case (3127):
                return "CAJA RURAL DE CASAS IBAÑEZ";
            case (3067):
                return "CAJA RURAL DE JAEN, BARCELONA Y MADRID";
            case (3140):
                return "CAJA RURAL DE GUISSONA";
            case (3023):
                return "CAJA RURAL DE GRANADA";
            case (3007):
                return "CAJA RURAL DE GIJON";
            case (3022):
                return "CAJA RURAL DE FUENTEPELAYO";
            case (3009):
                return "CAJA RURAL DE EXTREMADURA";
            case (3064):
                return "CAJA RURAL DE CUENCA";
            case (3063):
                return "CAJA RURAL DE CORDOBA";
            case (3062):
                return "CAJA RURAL DE CIUDAD REAL";
            case (3121):
                return "CAJA RURAL DE CHESTE";
            case (3104):
                return "CAJA RURAL DE CAÑETE TORRES NTRA.SRA.DEL CAMPO";
            case (3137):
                return "CAJA RURAL DE CASINOS";
            case (3084):
                return "IPAR KUTXA RURAL";
            case (3188):
                return "CREDIT VALENCIA";
            case (3076):
                return "CAJASIETE";
            case (3058):
                return "CAJAMAR CAJA RURAL";
            case (3018):
                return "CAJA R.R.S.AGUSTIN DE FUENTE ALAMO M.";
            case (3095):
                return "CAJA RURAL S. ROQUE DE ALMENARA";
            case (3135):
                return "CAJA RURAL S. JOSE DE NULES";
            case (3112):
                return "CAJA RURAL S. JOSE DE BURRIANA";
            case (3130):
                return "CAJA RURAL S. JOSE DE ALMASSORA";
            case (3113):
                return "CAJA RURAL S. JOSE DE ALCORA";
            case (3119):
                return "CAJA RURAL S. JAIME ALQUERIAS NIÑO PERDIDO";
            case (3165):
                return "CAJA RURAL S. ISIDRO DE VILAFAMES";
            case (3161):
                return "CAJA RURAL S. FORTUNATO";
            case (3134):
                return "CAJA RURAL NTRA. SRA. LA ESPERANZA DE ONDA";
            case (3098):
                return "CAJA RURAL NTRA. SRA. DEL ROSARIO";
            case (3157):
                return "CAJA RURAL LA JUNQUERA DE CHILCHES";
            case (3187):
                return "CAJA RURAL DEL SUR";
            case (3082):
                return "CAJA RURAL DEL MEDITERRANEO";
            case (3085):
                return "CAJA RURAL DE ZAMORA";
            case (3152):
                return "CAJA RURAL DE VILLAR";
            case (3144):
                return "CAJA RURAL DE VILLAMALEA";
            case (3020):
                return "CAJA RURAL DE UTRERA";
            case (3081):
                return "CAJA RURAL DE TOLEDO";
            case (3080):
                return "CAJA RURAL DE TERUEL";
            case (3017):
                return "CAJA RURAL DE SORIA";
            case (3078):
                return "CAJA RURAL DE SEGOVIA";
            case (3016):
                return "CAJA RURAL DE SALAMANCA";
            case (3008):
                return "CAJA RURAL DE NAVARRA";
            case (3128):
                return "CAJA RURAL DE LA RODA";
            default:
                return "";


        }
    }

    private class FullSelectorListener extends java.awt.event.FocusAdapter {

        @Override
        public void focusGained(java.awt.event.FocusEvent evt) {
            Object o = evt.getSource();
            if (o instanceof javax.swing.JTextField) {
                javax.swing.JTextField txt = (javax.swing.JTextField) o;
                txt.setSelectionStart(0);
                txt.setSelectionEnd(txt.getText().length());
                txt.setBackground(Color.white);
            }
        }
    }
}
