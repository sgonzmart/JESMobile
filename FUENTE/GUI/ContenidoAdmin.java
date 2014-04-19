/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import TADS.*;
import java.util.*;
import BaseDatos.*;
import MSJ.MSJ;
import java.awt.event.*;
import java.text.NumberFormat;

class ContenidoAdmin {

    private static final int GAP = 5;
    // private static final int GAPGRANDE = 20;
    private static final int BORDER = 1;  // Color border in pixels.
    final Font DEFAULTFONT = new java.awt.Font("Comic Sans MS", 2, 20);
    private static final int GAPGRANDE = 20;
    Admin adm;
    // Auxiliares:
    //----------------------------------------------------------------------------------------
    String[] TipoVia = {"", "Alameda", "Autopista", "Autovia", "Avenida", "Calle", //
        "Callejon", "Camino", "Cañada", "Carrera", "Carretera", //
        "Circunvalacion", "Cuesta", "Glorieta", "Parque", "Pasaje", //
        "Paseo", "Plaza", "Rambla", "Ronda", "Sendero", "Travesia", //
        "Urbanizacion", "Via"};                                             //
    String[] Provincia = {"", "Alava", "Albacete", "Alicante", "Almeria", "Asturias", "Avila", //
        "Badajoz", "Barcelona", "Burgos", "Caceres", "Cadiz", "Cantabria", //
        "Castellon", "Ceuta", "Cuidad Real", "Cordoba", "La Coruña", //
        "Cuenca", "Gerona", "Granada", "Guadalajara", "Guipuzcoa", //
        "Huelva", "Huesca", "Islas Baleares", "Jaen", "Leon", "Lerida", //
        "Lugo", "Madrid", "Malaga", "Melilla", "Murcia", "Navarra", //
        "Orense", "Palencia", "Las Palmas", "Pontevedra", "La Rioja", //
        "Salamanca", "Segovia", "Sevilla", "Soria", "Tarragona", //
        "Santa Cruz de Tenerife", "Teruel", "Toledo", "Valencia", //
        "Valladolid", "Vizcaya", "Zamora", "Zaragoza"};                                                    //
    String[] tiposPago = {"Tarjeta", "Domiciliacion Bancaria"};
    String[] tiposTarjeta = {"Visa", "Mastercard", "American-Express"};
    String[] anyos = {"2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018",
        "2019", "2020"};
    String[] meses = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
        "11", "12"};
    private JPanel jpterceroa = new JPanel(new GridBagLayout());
    private JTextField JTFNumTarjeta = new JTextField(16);
    private JTextField JTFNumCuenta = new JTextField(20);
    private JTextField JTFTitular = new JTextField(20);
    private JTextField JTFCVV = new JTextField(4);
    private JComboBox JCAnyos = new JComboBox(anyos);
    private JScrollPane JSPAnyos = new JScrollPane(JCAnyos);
    private JComboBox JCMeses = new JComboBox(meses);
    private JScrollPane JSPMeses = new JScrollPane(JCMeses);
    private JComboBox tipoTarjeta = new JComboBox(tiposTarjeta);
    private JScrollPane JSPTiposTarjetas = new JScrollPane(tipoTarjeta);
    private JComboBox formaPago = new JComboBox(tiposPago);

    ///////////////////////////////////////////////////////////////////////////
    //FACTURAS
    public JPanel mostrarFactura(final Admin adm, Cliente cli, final BaseDatos bd) {
        final AyudaGB pos = new AyudaGB();
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        //  final String periodo = "Desde el ";   //Añadir fecha ultima factura
        final Cliente c = cli;
        final JComboBox jcbLineas = new JComboBox();
        final JComboBox jcbClientes = new JComboBox();
        Image logo = new ImageIcon(getClass().getResource("/Imagenes/descargaPDF.gif")).getImage().getScaledInstance(30, 30, 10);
        final ImageIcon logoIcon = new ImageIcon(logo);
        //   final JButton jlImagen; // = new JButton(logoIcon);
        final JLabel jldescargar = new JLabel("Descargar factura en pdf ");
        // double importe = 0.0;
        final JPanel cont = new JPanel(new GridBagLayout());
        final JPanel cont2 = new JPanel(new GridBagLayout());
        cont.setBackground(Color.WHITE);
        cont2.setBackground(Color.WHITE);
        final AyudaGB pos2 = new AyudaGB();

        final ArrayList lstClientes = adm.listarTodosLosClientes();
        for (int i = 0; i < lstClientes.size(); i++) {
            // No añadimos el administrador
            if (!((Cliente_VO) lstClientes.get(i)).getNif().equals("admin")) {
                jcbClientes.addItem(((Cliente_VO) lstClientes.get(i)).getNif());
            }
        }
        // antes de que se seleccione nada
        ArrayList lstLineas = adm.listaLineasActivasCliente(jcbClientes.getSelectedItem().toString());
        jcbLineas.addItem("Seleccione una linea de telefono");
        for (int i = 0; i < lstLineas.size(); i++) {
            jcbLineas.addItem(((Linea_VO) lstLineas.get(i)).getNumtlf());
        }
        jcbClientes.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                jcbLineas.removeAllItems();
                ArrayList lstLineas = adm.listaLineasActivasCliente(jcbClientes.getSelectedItem().toString());
                for (int i = 0; i < lstLineas.size(); i++) {
                    jcbLineas.addItem(((Linea_VO) lstLineas.get(i)).getNumtlf());
                }
            }
        });

        cont.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                "Listado facturas",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));
        cont.setBackground(Color.WHITE);
        jcbClientes.setPreferredSize(new Dimension(100, jcbClientes.getHeight()));
        jcbLineas.setPreferredSize(new Dimension(100, jcbLineas.getHeight()));

        cont.add(new Gap(GAPGRANDE), pos);
        cont.add(new JLabel("Dni: "), pos.nextCol());
        cont.add(new Gap(GAP), pos.nextCol());
        cont.add(jcbClientes, pos.nextCol().expandW());

        cont.add(new Gap(GAP), pos.nextCol());
        cont.add(new JLabel("telefono: "), pos.nextCol());
        cont.add(new Gap(GAP), pos.nextCol());
        //cont.add(tfn2, pos.nextCol());
        cont.add(jcbLineas, pos.nextCol().expandW());

        //cont.add(ok, pos.nextRow());
        cont.add(cont2, pos.nextRow().width(8));
        jcbLineas.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (jcbLineas.getSelectedIndex() != 0) {
                    cont2.removeAll();
                    cont2.add(new Gap(GAPGRANDE), pos2.nextRow());
                    cont2.add(new Gap(GAP), pos2.width(5));
                    cont2.add(new JLabel("Valor de la ultima factura: "), pos2.nextRow());
                    cont2.add(new Gap(GAP), pos2.nextCol());
                    cont2.add(new Gap(GAP), pos2.nextRow());

                    int num = 0;
                    try {
                        num = Integer.parseInt(jcbLineas.getSelectedItem().toString());
                    } catch (Exception ex) {
                    }

                    final ArrayList lista = adm.listaFacturasLineaCliente(jcbClientes.getSelectedItem().toString(), num);
                    int n = lista.size();
                    cont2.add(new JLabel("Lista con todas las facturas del cliente dado: "), pos2.nextRow());

                    cont2.add(jldescargar, pos2.nextCol());
                    JButton jlImagen;
                    for (int i = 0; i < n; i++) {
                        final int j = i;
                        cont2.add(new JLabel("Número de la factura: "), pos2.nextRow());
                        cont2.add(new JLabel(Integer.toString(((Factura_VO) lista.get(i)).getNumfactura())), pos2.nextCol());
                        cont2.add(new Gap(GAP), pos2.nextCol());
                        jlImagen = new JButton(logoIcon);

                        jlImagen.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent evt) {

                                GenerarPDF pdf = new GenerarPDF("Factura", "Factura.pdf", c.GetBD(), (((Factura_VO) lista.get(j)).getNumfactura()), adm.obtenerCliente(jcbClientes.getSelectedItem().toString()));
                                //Preguntar al usuario si desea abrir el documento PDF
                                int respuesta = JOptionPane.showConfirmDialog(null, "Se ha generado el documento " + "Factura.pdf" + ", ¿Desea abrirlo?", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                //Si la respuesta es SI, abrirlo
                                if (respuesta == JOptionPane.YES_OPTION) {
                                    pdf.abrirPDF();
                                }

                            }
                        });
                        cont2.add(jlImagen, pos2.nextCol());

                        cont2.add(new Gap(GAP), pos.nextRow());
                        cont2.add(new JLabel("Número de telefono: "), pos2.nextRow());
                        cont2.add(new JLabel(Integer.toString(((Factura_VO) lista.get(i)).getNumtlf())), pos2.nextCol());
                        cont2.add(new JLabel("Importe de la factura: "), pos2.nextRow());
                        cont2.add(new JLabel(Double.toString(((Factura_VO) lista.get(i)).getImporte())), pos2.nextCol());
                        cont2.add(new JLabel("Fecha inicial: "), pos2.nextRow());
                        String fecha = (((Factura_VO) lista.get(i)).getFechainicio());
                        String fecha2 = fecha.substring(8, 10) + "/" + fecha.substring(5, 7) + "/" + fecha.substring(0, 4);
                        cont2.add(new JLabel(fecha2), pos2.nextCol());
                        cont2.add(new JLabel("Fecha final: "), pos2.nextRow());
                        fecha = (((Factura_VO) lista.get(i)).getFechafin());
                        fecha2 = fecha.substring(8, 10) + "/" + fecha.substring(5, 7) + "/" + fecha.substring(0, 4);
                        cont2.add(new JLabel(fecha2), pos2.nextCol());
                        cont2.add(new JLabel("Estado del pago: "), pos2.nextRow());
                        String pago = Integer.toString(((Factura_VO) lista.get(i)).getEstapagada());
                        JButton jbpagar = new JButton("Actualizar Pago");
                        if (pago.equals("0")) {
                            cont2.add(new JLabel("No se encuentra pagada"), pos2.nextCol());
                            cont2.add(new Gap(GAP), pos2.nextCol());
                            cont2.add(jbpagar, pos2.nextCol());
                            cont2.add(new Gap(GAP), pos2.nextCol());
                        }
                        if (pago.equals("1")) {
                            cont2.add(new JLabel("La factura se encuentra pagada"), pos2.nextCol());
                        }
                        cont2.add(new Gap(GAPGRANDE), pos2.nextRow());
                        final int aux = i;
                        jbpagar.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent evt) {
                                Factura fac = new Factura(bd);
                                fac.pagar(((Factura_VO) lista.get(aux)).getNumfactura());
                                JOptionPane.showMessageDialog(null,
                                        "Actualizacion del pago correcta.\n",
                                        "Actualizacion realizada",
                                        JOptionPane.ERROR_MESSAGE);

                            }
                        });

                    }
                    cont2.updateUI();
                } else {
                    cont2.removeAll();
                    cont2.updateUI();
                }
            }
        });

        panel.add(cont);
        return (panel);
    }

    public JPanel Facturar(Cliente c, Admin a) {
        final Admin ad = a;
        JPanel ventana = new JPanel();
        ventana.setBackground(Color.WHITE);
        final JPanel cont = new JPanel(new GridBagLayout());
        cont.setBackground(Color.WHITE);
        AyudaGB pos = new AyudaGB();
        JButton ok = new JButton(" Aceptar");
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                ad.facturar();
                JOptionPane.showMessageDialog(cont,
                        "Facturacion realizada con exito\n",
                        "Facturacion correcta",
                        JOptionPane.INFORMATION_MESSAGE);
                cont.updateUI();
            }
        });
        cont.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Facturacion del Cliente ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));
        cont.setPreferredSize(new Dimension(300, 200));
        cont.setBackground(Color.WHITE);

        cont.add(new Gap(GAP), pos);
        cont.add(new JLabel("Se van a facturar todas las lineas"), pos.nextRow());
        cont.add(new JLabel("telefonicas desde el ultimo periodo"), pos.nextRow());
        cont.add(new JLabel("¿Desea continuar?"), pos.nextRow());
        cont.add(new Gap(GAP), pos.nextRow());
        cont.add(ok, pos.nextRow());
        cont.add(new Gap(GAP), pos.nextRow());
        ventana.add(cont);

        return (ventana);
    }

////////////////////////////////////////////////////////////////////////////////////
    public JPanel datosPersonales(Admin a) {
        final JPanel jppersonal = new JPanel(new GridBagLayout());
        final JPanel jpdireccion = new JPanel(new GridBagLayout());
        final JPanel jpbancarios = new JPanel(new GridBagLayout());
        JPanel ventana = new JPanel();
        adm = a;
        final JPanel cont = new JPanel(new GridBagLayout());
        final JPanel cont2 = new JPanel(new GridBagLayout());
        jppersonal.setBackground(Color.WHITE);
        jpdireccion.setBackground(Color.WHITE);
        jpbancarios.setBackground(Color.WHITE);
        cont.setBackground(Color.WHITE);
        ventana.setBackground(Color.WHITE);
        cont2.setBackground(Color.WHITE);

        AyudaGB pos = new AyudaGB();
        final JComboBox jcbClientes = new JComboBox();
        //final JTextField dni2 = new JTextField(7);
        //final JTextField tfn = new JTextField(15);
        JButton ok = new JButton("Aceptar");

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

        cont.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Mostrar cliente",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        final ArrayList lstClientes = adm.listarTodosLosClientes();
        for (int i = 0; i < lstClientes.size(); i++) {
            // No añadimos el administrador
            if (!((Cliente_VO) lstClientes.get(i)).getNif().equals("admin")) {
                jcbClientes.addItem(((Cliente_VO) lstClientes.get(i)).getNif());
            }
        }

        cont.add(new Gap(GAP), pos);
        cont.add(new JLabel("Dni (documento de identidad)"), pos.nextCol());
        cont.add(new Gap(GAP), pos.nextCol());


        cont.add(new Gap(GAP), pos.nextRow());
        cont.add(jcbClientes, pos.nextCol().expandW());
        cont.add(new Gap(GAP), pos.nextCol());
        cont.add(ok, pos.nextRow().width(3));

        cont.add(cont2, pos.nextRow().width(3));

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                AyudaGB posPer = new AyudaGB();
                Cliente_VO caux = adm.obtenerCliente(jcbClientes.getSelectedItem().toString());

                cont2.removeAll();
                jppersonal.removeAll();
                jpdireccion.removeAll();
                jpbancarios.removeAll();
                jppersonal.add(new Gap(GAP), posPer.nextRow());
                jppersonal.add(new JLabel(" > Nombre:"), posPer.nextRow().align(AyudaGB.WEST));
                jppersonal.add(new Gap(GAP), posPer.nextCol());
                jppersonal.add(new JLabel(caux.getNombre()), posPer.nextCol());
                jppersonal.add(new Gap(GAP), posPer.nextCol());
                jppersonal.add(new Gap(GAP), posPer.nextRow());
                jppersonal.add(new JLabel(" > Apellidos:"), posPer.nextRow().align(AyudaGB.WEST));
                jppersonal.add(new Gap(GAP), posPer.nextCol());
                jppersonal.add(new JLabel(caux.getApellidos()), posPer.nextCol());
                jppersonal.add(new Gap(GAP), posPer.nextCol());
                jppersonal.add(new Gap(GAP), posPer.nextRow());
                jppersonal.add(new JLabel(" > Documento Ident:"), posPer.nextRow().align(AyudaGB.WEST));
                jppersonal.add(new Gap(GAP), posPer.nextCol());
                jppersonal.add(new JLabel(caux.getNif()), posPer.nextCol());
                jppersonal.add(new Gap(GAP), posPer.nextCol());
                jppersonal.add(new Gap(GAP), posPer.nextRow());
                jppersonal.add(new JLabel(" > Email:"), posPer.nextRow().align(AyudaGB.WEST));
                jppersonal.add(new Gap(GAP), posPer.nextCol());
                jppersonal.add(new JLabel(caux.getEmail()), posPer.nextCol());
                jppersonal.add(new Gap(GAP), posPer.nextCol());
                jppersonal.add(new Gap(GAP), posPer.nextRow());
                jppersonal.add(new JLabel(" > Telefono:"), posPer.nextRow().align(AyudaGB.WEST));
                jppersonal.add(new Gap(GAP), posPer.nextCol());
                jppersonal.add(new JLabel(Integer.toString(caux.getTelefono())), posPer.nextCol());
                jppersonal.add(new Gap(GAP), posPer.nextCol());
                jppersonal.add(new Gap(GAP), posPer.nextRow());

                AyudaGB posdir = new AyudaGB();
                String direcc;
                jpdireccion.add(new Gap(GAP), posdir.nextRow());
                direcc = caux.getTipovia() + "\\ " + caux.getNomvia();
                jpdireccion.add(new JLabel(direcc), posdir.nextRow().width(3));
                jpdireccion.add(new Gap(GAP), posdir.nextCol());
                jpdireccion.add(new Gap(GAP), posdir.nextCol());
                jpdireccion.add(new Gap(GAP), posdir.nextCol());
                jpdireccion.add(new JLabel(" > Nº:"), posdir.nextCol().align(AyudaGB.WEST));
                jpdireccion.add(new Gap(GAP), posdir.nextCol());
                jpdireccion.add(new JLabel(caux.getNumcalle()), posdir.nextCol());
                jpdireccion.add(new Gap(GAP), posdir.nextRow());
                if (!caux.getPiso().isEmpty()) {

                    jpdireccion.add(new JLabel(" > Piso:"), posdir.nextRow().align(AyudaGB.WEST));
                    jpdireccion.add(new Gap(GAP), posdir.nextCol());
                    jpdireccion.add(new JLabel(caux.getPiso()), posdir.nextCol());
                    jpdireccion.add(new Gap(GAP), posdir.nextCol());
                }
                if (!(caux.getPuerta().isEmpty())) {
                    jpdireccion.add(new JLabel(" > Puerta:"), posdir.nextCol().align(AyudaGB.WEST));
                    jpdireccion.add(new Gap(GAP), posdir.nextCol());
                    jpdireccion.add(new JLabel(caux.getPuerta()), posdir.nextCol());
                    jpdireccion.add(new Gap(GAP), posdir.nextCol());
                }
                if (!(caux.getEscalera().isEmpty())) {
                    jpdireccion.add(new JLabel(" > Escalera:"), posdir.nextCol().align(AyudaGB.WEST));
                    jpdireccion.add(new Gap(GAP), posdir.nextCol());
                    jpdireccion.add(new JLabel(caux.getEscalera()), posdir.nextCol());
                    jpdireccion.add(new Gap(GAP), posdir.nextCol());
                }
                jpdireccion.add(new Gap(GAP), posdir.nextRow());
                jpdireccion.add(new JLabel(" > Localidad:"), posdir.nextRow().align(AyudaGB.WEST));
                jpdireccion.add(new Gap(GAP), posdir.nextCol());
                jpdireccion.add(new JLabel(caux.getLocalidad()), posdir.nextCol());
                jpdireccion.add(new Gap(GAP), posdir.nextCol());
                jpdireccion.add(new JLabel(" > Codigo Postal:"), posdir.nextCol().align(AyudaGB.WEST));
                jpdireccion.add(new Gap(GAP), posdir.nextCol());
                jpdireccion.add(new JLabel(Integer.toString(caux.getCodpostal())), posdir.nextCol());
                jpdireccion.add(new Gap(GAP), posdir.nextCol());
                jpdireccion.add(new JLabel(" > Provincia:"), posdir.nextCol().align(AyudaGB.WEST));
                jpdireccion.add(new Gap(GAP), posdir.nextCol());
                jpdireccion.add(new JLabel(caux.getProvincia()), posdir.nextCol());
                jpdireccion.add(new Gap(GAP), posdir.nextCol());
                jpdireccion.add(new Gap(GAP), posdir.nextRow());

                AyudaGB posban = new AyudaGB();
                jpbancarios.add(new Gap(GAP), posban.nextRow());
                jpbancarios.add(new JLabel(" > Tipo de pago:"), posban.nextRow().align(AyudaGB.WEST));
                jpbancarios.add(new Gap(GAP), posban.nextCol());
                jpbancarios.add(new JLabel(caux.getTipopago()), posban.nextCol());
                jpbancarios.add(new Gap(GAP), posban.nextCol());
                jpbancarios.add(new Gap(GAP), posban.nextRow());
                jpbancarios.add(new JLabel(" > Titular:"), posban.nextRow().align(AyudaGB.WEST));
                jpbancarios.add(new Gap(GAP), posban.nextCol());
                jpbancarios.add(new JLabel(caux.getTitular()), posban.nextCol());
                if (caux.getTipopago().equals("Tarjeta")) {
                    jpbancarios.add(new Gap(GAP), posban.nextRow());
                    jpbancarios.add(new JLabel(" > Numero de tarjeta:"), posban.nextRow().align(AyudaGB.WEST));
                    jpbancarios.add(new Gap(GAP), posban.nextCol());
                    jpbancarios.add(new JLabel(caux.getNumtarjeta()), posban.nextCol());
                    jpbancarios.add(new Gap(GAP), posban.nextCol());
                    jpbancarios.add(new Gap(GAP), posban.nextRow());
                    jpbancarios.add(new JLabel(" > Fecha de Caducidad:"), posban.nextRow().align(AyudaGB.WEST));
                    jpbancarios.add(new Gap(GAP), posban.nextCol());
                    jpbancarios.add(new JLabel(caux.getCaducidad()), posban.nextCol());
                    jpbancarios.add(new Gap(GAP), posban.nextCol());
                } else {
                    jpbancarios.add(new Gap(GAP), posban.nextRow());
                    jpbancarios.add(new JLabel(" > Nombre entidad financiera:"), posban.nextRow().align(AyudaGB.WEST));
                    jpbancarios.add(new Gap(GAP), posban.nextCol());
                    String numCuenta = caux.getNumcuenta();
                    int codigo = 0;
                    try {
                        codigo = Integer.parseInt(numCuenta.substring(0, 4));
                    } catch (Exception ex) {
                        codigo = 0;
                    }
                    jpbancarios.add(new JLabel(codigosBancarios(codigo)), posban.nextCol());
                    jpbancarios.add(new Gap(GAP), posban.nextCol());
                    jpbancarios.add(new Gap(GAP), posban.nextRow());
                    jpbancarios.add(new JLabel(" > Numero de cuenta:"), posban.nextRow().align(AyudaGB.WEST));
                    jpbancarios.add(new Gap(GAP), posban.nextCol());
                    jpbancarios.add(new JLabel(numCuenta), posban.nextCol());
                    jpbancarios.add(new Gap(GAP), posban.nextCol());
                    jpbancarios.add(new Gap(GAP), posban.nextRow());
                }

                AyudaGB pos = new AyudaGB();
                cont2.add(new Gap(GAP), pos.nextRow());
                cont2.add(jppersonal, pos.nextRow().expandW());
                cont2.add(new Gap(GAP), pos.nextRow());
                cont2.add(jpdireccion, pos.nextRow().expandW());
                cont2.add(new Gap(GAP), pos.nextRow());
                cont2.add(jpbancarios, pos.nextRow().expandW());
                cont2.add(new Gap(GAP), pos.nextRow());
                cont.updateUI();
            }
        });

        ventana.add(cont);
        return (ventana);
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

    public JPanel modifDPersonales(final Admin a) {

        final JPanel panel = new JPanel(new GridBagLayout());
        final JPanel jpsuperior = new JPanel(new GridBagLayout());
        final JPanel jppersonal = new JPanel(new GridBagLayout());
        final JPanel jpdireccion = new JPanel(new GridBagLayout());
        final JPanel jpprimero = new JPanel(new GridBagLayout());
        final JPanel jpbancarios = new JPanel(new GridBagLayout());
        final JPanel jpbancariosA = new JPanel(new GridBagLayout());
        jppersonal.setBackground(Color.WHITE);
        jpdireccion.setBackground(Color.WHITE);
        jpbancarios.setBackground(Color.WHITE);
        jpbancariosA.setBackground(Color.WHITE);
        jpprimero.setBackground(Color.WHITE);
        jpsuperior.setBackground(Color.WHITE);
        panel.setBackground(Color.WHITE);

        final JComboBox jcbClientes = new JComboBox();
        JButton jbSeleccionar = new JButton("Seleccionar");
        final JButton jbAceptar = new JButton("Modificar");
        adm = a;
        AyudaGB pos = new AyudaGB();

        jpprimero.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Identificacion del Cliente ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

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

        // final ArrayList lstClientes = adm.listarClientesActivos();
        final ArrayList lstClientes = adm.listarTodosLosClientes();
        for (int i = 0; i < lstClientes.size(); i++) {
            // No añadimos el administrador
            if (!((Cliente_VO) lstClientes.get(i)).getNif().equals("admin")) {
                jcbClientes.addItem(((Cliente_VO) lstClientes.get(i)).getNif());
            }
        }

        jpsuperior.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        jpsuperior.add(new Gap(GAP), pos);
        jpsuperior.add(new JLabel("Identificacion (DNI)"), pos.nextCol());
        jpsuperior.add(new Gap(GAP), pos.nextCol());

        jpsuperior.add(new Gap(GAP), pos.nextRow());
        jpsuperior.add(jcbClientes, pos.nextCol());
        jpsuperior.add(new Gap(GAP), pos.nextCol());

        jpsuperior.add(new Gap(GAP), pos.nextRow());
        jpsuperior.add(jbSeleccionar, pos.nextCol());
        jpsuperior.add(new Gap(GAP), pos.nextCol());


        final Cliente_VO c;
        if (lstClientes.size() != 1) { // Solo esta el admin entonces no entramos
            c = adm.obtenerCliente(jcbClientes.getSelectedItem().toString());

            final JTextField nombre = new JTextField(15);
            final JTextField apellidos = new JTextField(15);
            final JTextField dni = new JTextField(16);
            dni.setEnabled(false);
            final JTextField email = new JTextField(15);
            final JTextField telefono = new JTextField(9);
            final JTextField calle = new JTextField(15);
            final JTextField num = new JTextField(5);
            final JTextField puerta = new JTextField(5);
            final JTextField esc = new JTextField(5);
            final JTextField piso = new JTextField(5);
            final JTextField ciudad = new JTextField(15);
            final JTextField codigopostal = new JTextField(15);
            final JTextField numtarjeta = new JTextField(16);
            final JTextField numcuenta = new JTextField(20);
            final JTextField cvv = new JTextField(4);
            final JTextField titular = new JTextField(50);
            final JComboBox fpago = new JComboBox(tiposPago);
            final JScrollPane JSPFormasPago = new JScrollPane(fpago);
            final JComboBox lstProv = new JComboBox(Provincia);                                           //
            final JComboBox lstVias = new JComboBox(TipoVia);
            final JComboBox tptarjeta = new JComboBox(tiposTarjeta);
            final JScrollPane jsptptarjeta = new JScrollPane(tptarjeta);
            final JComboBox jcanos = new JComboBox(anyos);
            final JScrollPane jspanos = new JScrollPane(jcanos);
            final JComboBox jcbmeses = new JComboBox(meses);
            final JScrollPane jspmeses = new JScrollPane(jcbmeses);
            // JPasswordField repass = new JPasswordField(15);


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
            numtarjeta.setToolTipText("Escriba los numeros de su tarjeta "
                    + "sin espacion ni guiones");
            numcuenta.setToolTipText("Escriba los 20 digitos de su cuenta"
                    + " sin espacios");
            cvv.setToolTipText("Escriba los digitos de CVV de su tarjeta");
            titular.setToolTipText("Escriba el nombre y apellidos del titular "
                    + "de la tarjeta exactamente igual a como aparecen en la tarjeta");

            jbSeleccionar.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    JScrollPane vias = new JScrollPane(lstVias);
                    JScrollPane prov = new JScrollPane(lstProv);
                    final Cliente_VO c = adm.obtenerCliente(jcbClientes.getSelectedItem().toString());

                    nombre.setText(c.getNombre());
                    apellidos.setText(c.getApellidos());
                    dni.setText(c.getNif());
                    email.setText(c.getEmail());
                    telefono.setText(Integer.toString(c.getTelefono()));
                    calle.setText(c.getNomvia());
                    num.setText(c.getNumcalle());
                    piso.setText(c.getPiso());
                    puerta.setText(c.getPuerta());
                    esc.setText(c.getEscalera());
                    ciudad.setText(c.getLocalidad());
                    codigopostal.setText(Integer.toString(c.getCodpostal()));
                    titular.setText(c.getTitular());
                    numtarjeta.setText(c.getNumtarjeta());
                    numcuenta.setText(c.getNumcuenta());
                    cvv.setText(Integer.toString(c.getCodseg()));

                    AyudaGB posPer = new AyudaGB();

                    jppersonal.removeAll();
                    jpdireccion.removeAll();
                    jpbancarios.removeAll();

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
                    fpago.setSelectedItem(c.getTipopago());
                    jpbancarios.add(JSPFormasPago, posban.nextCol());
                    jpbancarios.add(new Gap(GAP), posban.nextCol());
                    jpbancarios.add(new Gap(GAP), posban.nextRow());
                    jpbancarios.add(new JLabel(" > Titular:"), posban.nextRow());
                    jpbancarios.add(new Gap(GAP), posban.nextCol());
                    jpbancarios.add(titular, posban.nextCol().width(2));
                    jpbancarios.add(new Gap(GAP), posban.nextRow());
                    jpbancarios.add(jpbancariosA, posban.nextRow().width(4));

                    fpago.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            JLabel JLQueEs = new JLabel("¿Que es CVV?");
                            JLQueEs.addMouseListener(new MouseAdapter() {

                                @Override
                                public void mouseClicked(MouseEvent evt) {
                                    Image CVV = new ImageIcon(getClass().getResource("/Imagenes/Quees.jpg")).getImage();
                                    ImageIcon icon = new ImageIcon(CVV);
                                    JOptionPane.showMessageDialog(panel, icon, "Que es CVV",
                                            JOptionPane.DEFAULT_OPTION);
                                }
                            });
                            jpbancariosA.removeAll();
                            Image Visa = new ImageIcon(getClass().getResource("/Imagenes/Tarjetas.jpg")).getImage().
                                    getScaledInstance(150, 30, 0);
                            ImageIcon visaIcon = new ImageIcon(Visa);
                            JLabel JLVisa = new JLabel(visaIcon);


                            tptarjeta.setSelectedItem(c.getTipotarjeta());

                            if (fpago.getSelectedItem().equals("Tarjeta")) {

                                jpbancariosA.add(new JLabel(" > Tipo Tarjeta:"), posban.nextRow());
                                jpbancariosA.add(new Gap(GAP), posban.nextCol());
                                jpbancariosA.add(jsptptarjeta, posban.nextCol());
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
                                    jcanos.setSelectedItem(fecha.substring(3, 6));
                                    jcbmeses.setSelectedItem(fecha.substring(0, 1));
                                }
                                jpbancariosA.add(jspmeses, posban.nextCol());
                                jpbancariosA.add(new Gap(GAP), posban.nextCol());
                                jpbancariosA.add(jspanos, posban.nextCol());
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
                            jpbancariosA.updateUI();
                            panel.updateUI();
                        }
                    });

                    AyudaGB pos3 = new AyudaGB();
                    panel.removeAll();
                    panel.add(jpsuperior, pos3.expandW().width(6));
                    panel.add(new Gap(GAP), pos3.nextRow());
                    panel.add(jppersonal, pos3.nextRow().width(6));
                    panel.add(new Gap(GAP), pos3.nextRow());
                    panel.add(new Gap(GAP), pos3.nextRow());
                    panel.add(jpdireccion, pos3.nextRow().width(6));
                    panel.add(new Gap(GAP), pos3.nextRow());
                    panel.add(new Gap(GAP), pos3.nextRow());
                    panel.add(jpbancarios, pos3.nextRow().width(6));
                    panel.add(new Gap(GAP), pos3.nextRow());
                    panel.add(new Gap(GAP), pos3.nextRow());
                    panel.add(new Gap(GAP), pos3.nextCol());
                    panel.add(new Gap(GAP), pos3.nextCol());
                    panel.add(new Gap(GAP), pos3.nextCol());
                    panel.add(jbAceptar, pos3.nextCol());
                    panel.add(new Gap(GAP), pos3.nextRow());
                }
            });

            //   AyudaGB pos = new AyudaGB();
            jbAceptar.setBackground(Color.GREEN);
            panel.add(jpsuperior, pos);
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

                        if (fpago.getSelectedIndex() == 0) { // Hemos seleccionado el
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
                        if (fpago.getSelectedIndex() == 0) {
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
                            String ftarj = jcbmeses.getSelectedItem().toString()
                                    + "/" + jcanos.getSelectedItem().toString();

                            adm.modificarCliente(dni.getText(), "1234",
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
                                    fpago.getSelectedItem().toString(),
                                    tptarjeta.getSelectedItem().toString(),
                                    numtarjeta.getText(),
                                    ftarj,
                                    titular.getText(),
                                    Integer.parseInt(cvv.getText()),
                                    numcuenta.getText());

                            JOptionPane.showMessageDialog(panel, "Modificado con exito",
                                    "Modificar OK", JOptionPane.DEFAULT_OPTION);

                        }
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(panel,
                                "Compruebe el formato de los datos\n",
                                "Datos incorrectos",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        return panel;
    }

    /**
     * Crea la venta de registrarse
     * @param Tienda
     */
    public JPanel registrar(final Admin adm2) {
        final JPanel jpglobal = new JPanel(new GridBagLayout());
        jpglobal.setBackground(Color.WHITE);
        JButton aceptar2 = new JButton("Aceptar");
        adm = adm2;

        // Primer panel:

        JPanel jpprimero = new JPanel(new GridBagLayout());
        jpprimero.setBackground(Color.WHITE);

        jpprimero.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Datos Personales ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));


        final JTextField nom = new JTextField(15);
        final JTextField app = new JTextField(15);
        final JPasswordField pass2 = new JPasswordField(15);
        final JPasswordField repass2 = new JPasswordField(15);
        final JTextField dni2 = new JTextField(16);
        final JTextField email2 = new JTextField(15);
        final JTextField tfn = new JTextField(9);
        final JComboBox lstProv = new JComboBox(Provincia);                                           //
        final JComboBox lstVias = new JComboBox(TipoVia);
        final JComboBox jcbanos2 = new JComboBox(anyos);
        final JComboBox jcbmeses = new JComboBox(meses);
        final JComboBox tptarjeta = new JComboBox(tiposTarjeta);
        JScrollPane JSPFormasPago = new JScrollPane(formaPago);

        AyudaGB pos = new AyudaGB();

        jpprimero.removeAll();
        // 1
        jpprimero.add(new Gap(GAP), pos.width(5));

        // 2
        jpprimero.add(new JLabel("Nombre*"), pos.nextRow());
        jpprimero.add(new Gap(GAP), pos.nextCol());
        jpprimero.add(new JLabel("Apellidos*"), pos.nextCol());
        jpprimero.add(new Gap(GAP), pos.nextCol());
        jpprimero.add(new Gap(GAP), pos.nextCol());
        jpprimero.add(new Gap(GAP), pos.nextCol());
        jpprimero.add(new JLabel("Documento Identificacion*"), pos.nextCol());

        // 3
        jpprimero.add(new Gap(GAP), pos.nextRow().width(5));

        // 4
        jpprimero.add(nom, pos.nextRow());
        jpprimero.add(new Gap(GAP), pos.nextCol());
        jpprimero.add(app, pos.nextCol().width(3));
        jpprimero.add(new Gap(GAP), pos.nextCol());
        jpprimero.add(new Gap(GAP), pos.nextCol());
        jpprimero.add(new Gap(GAP), pos.nextCol());
        jpprimero.add(dni2, pos.nextCol());


        // 5
        jpprimero.add(new Gap(GAP), pos.nextRow().width(5));

        // 6
        jpprimero.add(new JLabel("Email*"), pos.nextRow());
        jpprimero.add(new Gap(GAP), pos.nextCol());
        jpprimero.add(new JLabel("Telefono"), pos.nextCol());
        jpprimero.add(new Gap(GAP), pos.nextCol().width(2));
        jpprimero.add(new JLabel("Contraseña*"), pos.nextCol());
        jpprimero.add(new Gap(GAP), pos.nextCol());
        jpprimero.add(new JLabel("Repetir Contraseña*"), pos.nextCol());
        jpprimero.add(new Gap(GAP), pos.nextCol().width(2));

        // 7
        jpprimero.add(new Gap(GAP), pos.nextRow().width(5));

        // 8
        jpprimero.add(email2, pos.nextRow());
        jpprimero.add(new Gap(GAP), pos.nextCol());
        jpprimero.add(tfn, pos.nextCol());
        jpprimero.add(new Gap(GAP), pos.nextCol());
        jpprimero.add(pass2, pos.nextCol());
        jpprimero.add(new Gap(GAP), pos.nextCol());
        jpprimero.add(repass2, pos.nextCol());
        jpprimero.add(new Gap(GAP), pos.nextCol().width(2));

        // 9

        jpprimero.add(new Gap(GAP), pos.nextRow().width(5));

        // segundo panel:

        final JTextField calle2 = new JTextField(15);
        final JTextField num2 = new JTextField(5);
        final JTextField puerta2 = new JTextField(5);
        final JTextField esc2 = new JTextField(5);
        final JTextField piso2 = new JTextField(5);
        final JTextField ciudad2 = new JTextField(15);
        final JTextField cp = new JTextField(15);


        JScrollPane prov2 = new JScrollPane(lstProv);
        JScrollPane vias2 = new JScrollPane(lstVias);
        JPanel jpsegundo = new JPanel(new GridBagLayout());
        jpsegundo.setBackground(Color.WHITE);
        //----------------------------------------------------------------------

        jpsegundo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                "Direccion de Facturacion",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        pos = new AyudaGB();

        // 2
        jpsegundo.add(new JLabel("Tipo de Via*"), pos);
        jpsegundo.add(new Gap(GAP), pos.nextCol());
        jpsegundo.add(new JLabel("Nombre de Via*"), pos.nextCol());

        // 2
        jpsegundo.add(new Gap(GAP), pos.nextRow().width(5));

        // 3
        jpsegundo.add(vias2, pos.nextRow());
        jpsegundo.add(new Gap(GAP), pos.nextCol());
        jpsegundo.add(calle2, pos.nextCol().width(3));

        // 4
        jpsegundo.add(new JLabel("Numero*"), pos.nextRow());
        jpsegundo.add(new Gap(GAP), pos.nextCol());
        jpsegundo.add(new JLabel("Piso"), pos.nextCol());
        jpsegundo.add(new Gap(GAP), pos.nextCol());
        jpsegundo.add(new JLabel("Puerta"), pos.nextCol());
        jpsegundo.add(new Gap(GAP), pos.nextCol());
        jpsegundo.add(new JLabel("Escalera"), pos.nextCol());

        jpsegundo.add(new Gap(GAP), pos.nextRow().width(5));

        // 5
        jpsegundo.add(num2, pos);
        jpsegundo.add(new Gap(GAP), pos.nextCol());
        jpsegundo.add(piso2, pos.nextCol());
        jpsegundo.add(new Gap(GAP), pos.nextCol());
        jpsegundo.add(puerta2, pos.nextCol());
        jpsegundo.add(new Gap(GAP), pos.nextCol());
        jpsegundo.add(esc2, pos.nextCol());


        // 6
        jpsegundo.add(new Gap(GAP), pos.nextRow().width(5));

        // 7
        jpsegundo.add(new JLabel("Localidad*"), pos.nextRow());
        jpsegundo.add(new Gap(GAP), pos.nextCol());
        jpsegundo.add(new JLabel("Provincia*"), pos.nextCol());
        jpsegundo.add(new Gap(GAP), pos.nextCol());
        jpsegundo.add(new JLabel("Codigo Postal*"), pos.nextCol());

        // 6
        jpsegundo.add(new Gap(GAP), pos.nextRow().width(5));

        // 7
        jpsegundo.add(ciudad2, pos.nextRow());
        jpsegundo.add(new Gap(GAP), pos.nextCol());
        jpsegundo.add(prov2, pos.nextCol());
        jpsegundo.add(new Gap(GAP), pos.nextCol());
        jpsegundo.add(cp, pos.nextCol());

        // tercer panel:
        JPanel jptercero = new JPanel(new GridBagLayout());
        jptercero.setBackground(Color.WHITE);

        jptercero.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                "Datos de Facturacion",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        pos = new AyudaGB();

        //Establecemos el combobox para elegir el tipo de pago

        formaPago.addActionListener(new EscuchadorTPago());
        formaPago.setSelectedIndex(0);
        jpterceroa.setBackground(Color.WHITE);
        jptercero.add(new JLabel("Tipo de pago*"), pos.nextRow());
        jptercero.add(new Gap(GAP), pos.nextCol());
        jptercero.add(JSPFormasPago, pos.nextCol());
        jptercero.add(new Gap(GAP), pos.nextRow());
        jptercero.add(jpterceroa, pos.nextRow().width(5));
        jptercero.add(new Gap(GAP), pos.nextRow());

        // construimos la ventana:

        jpglobal.removeAll();
        jpglobal.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        pos = new AyudaGB();

        // 1
        jpglobal.add(new Gap(GAPGRANDE), pos);
        jpglobal.add(jpprimero, pos.nextCol().width(3));
        jpglobal.add(new Gap(GAPGRANDE), pos.nextCol().expandW());

        // 2
        jpglobal.add(new Gap(GAPGRANDE), pos.nextRow().width(5));

        // 3
        jpglobal.add(new Gap(GAPGRANDE), pos.nextRow());
        jpglobal.add(jpsegundo, pos.nextCol().width(3));
        jpglobal.add(new Gap(GAPGRANDE), pos.nextCol());

        // 4
        jpglobal.add(new Gap(GAPGRANDE), pos.nextRow().width(5));

        // 5
        jpglobal.add(new Gap(GAPGRANDE), pos.nextRow());
        jpglobal.add(jptercero, pos.nextCol().width(3));
        jpglobal.add(new Gap(GAPGRANDE), pos.nextCol());

        // 6
        jpglobal.add(new Gap(GAPGRANDE), pos.nextRow().width(5));

        // 7
        jpglobal.add(new Gap(GAPGRANDE), pos.nextRow());
        jpglobal.add(aceptar2, pos.nextCol());
        aceptar2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {


                try {
                    //Comprobamos que todos los campos obligatorios estan rellenos
                    boolean completo = true;
                    if (nom.getText().length() > 15) {
                        JOptionPane.showMessageDialog(jpglobal, "Nombre demasiado largo, maximo 15 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (app.getText().length() > 50) {
                        JOptionPane.showMessageDialog(jpglobal, "Apellidos demasiado largos, maximo 50 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (dni2.getText().length() > 9) {
                        JOptionPane.showMessageDialog(jpglobal, "DNI demasiado largo, maximo 9 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (email2.getText().length() > 100) {
                        JOptionPane.showMessageDialog(jpglobal, "Email demasiado largo, maximo 100 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (pass2.getText().length() > 20) {
                        JOptionPane.showMessageDialog(jpglobal, "Contraseña demasiado larga, se admiten maximo 20 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (calle2.getText().length() > 50) {
                        JOptionPane.showMessageDialog(jpglobal, "Calle demasiado larga, maximo 50 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (num2.getText().length() > 10) {
                        JOptionPane.showMessageDialog(jpglobal, "Numero de calle demasiado largo, maximo 10 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (piso2.getText().length() > 10) {
                        JOptionPane.showMessageDialog(jpglobal, "Numero de piso demasiado largo, se admiten maximo 10 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (puerta2.getText().length() > 10) {
                        JOptionPane.showMessageDialog(jpglobal, "Numero de puerta demasiado largo, se admiten maximo 10 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (esc2.getText().length() > 10) {
                        JOptionPane.showMessageDialog(jpglobal, "Escalera demasiado larga, se admiten maximo 10 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (ciudad2.getText().length() > 50) {
                        JOptionPane.showMessageDialog(jpglobal, "Nombre de ciudad demasiado largo, maximo 50 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (formaPago.getSelectedIndex() == 0) {
                        // Pago con tarjeta
                        if (JTFNumTarjeta.getText().length() > 18) {
                            JOptionPane.showMessageDialog(jpglobal, "Numero de la tarjeta demasiado largo, maximo 18 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if (JTFTitular.getText().length() > 100) {
                            JOptionPane.showMessageDialog(jpglobal, "Titular demasiado largo, maximo 100 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        // Pago con domiciliacion
                        if (JTFTitular.getText().length() > 100) {
                            JOptionPane.showMessageDialog(jpglobal, "Titular demasiado largo, maximo 100 caracteres.\n", "Error al Registrarse", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                    completo = completo
                            && !nom.getText().isEmpty()
                            && !app.getText().isEmpty()
                            && !lstVias.getSelectedItem().toString().isEmpty()
                            && !calle2.getText().isEmpty()
                            && !num2.getText().isEmpty()
                            && !ciudad2.getText().isEmpty()
                            && !lstProv.getSelectedItem().toString().isEmpty()
                            && !cp.getText().isEmpty()
                            && !dni2.getText().isEmpty()
                            && !email2.getText().isEmpty()
                            && !pass2.getText().isEmpty()
                            && !repass2.getText().isEmpty();

                    if (formaPago.getSelectedIndex() == 0) { // Hemos seleccionado el
                        // pago mediante tarjeta de credito
                        completo = completo
                                && !JTFNumTarjeta.getText().isEmpty()
                                && !JTFTitular.getText().isEmpty()
                                && !JTFCVV.getText().isEmpty();
                    } else { // Hemos seleccionado pago mediante domiciliacion bancaria
                        completo = completo
                                && !JTFTitular.getText().isEmpty()
                                && !JTFNumCuenta.getText().isEmpty();
                    }

                    if (tfn.getText().isEmpty()) {
                        tfn.setText("0");
                    }
                    if (piso2.getText().isEmpty()) {
                        piso2.setText("");
                    }
                    if (puerta2.getText().isEmpty()) {
                        puerta2.setText("");
                    }
                    if (esc2.getText().isEmpty()) {
                        esc2.setText("");
                    }
                    if (formaPago.getSelectedIndex() == 0) {
                        JTFNumCuenta.setText("0");
                    } else {
                        JTFNumTarjeta.setText("0");
                        JTFCVV.setText("0");
                    }
                    if (!completo) {
                        JOptionPane.showMessageDialog(jpglobal,
                                "Error; Debe rellenar todos los campos marcados con un *.\n",
                                "Error al Registrarse",
                                JOptionPane.ERROR_MESSAGE);
                        if (nom.getText().isEmpty()) {
                            nom.setBackground(Color.RED);
                        } else {
                            nom.setBackground(Color.WHITE);
                        }
                        if (pass2.getText().isEmpty()) {
                            pass2.setBackground(Color.RED);
                        } else {
                            pass2.setBackground(Color.WHITE);
                        }
                        if (repass2.getText().isEmpty()) {
                            repass2.setBackground(Color.RED);
                        } else {
                            repass2.setBackground(Color.WHITE);
                        }
                        if (app.getText().isEmpty()) {
                            app.setBackground(Color.RED);
                        } else {
                            app.setBackground(Color.WHITE);
                        }
                        if (lstVias.getSelectedItem().toString().isEmpty()) {
                            lstVias.setBackground(Color.RED);
                        } else {
                            lstVias.setBackground(Color.WHITE);
                        }
                        if (calle2.getText().isEmpty()) {
                            calle2.setBackground(Color.RED);
                        } else {
                            calle2.setBackground(Color.WHITE);
                        }
                        if (num2.getText().isEmpty()) {
                            num2.setBackground(Color.RED);
                        } else {
                            num2.setBackground(Color.WHITE);
                        }
                        if (ciudad2.getText().isEmpty()) {
                            ciudad2.setBackground(Color.RED);
                        } else {
                            ciudad2.setBackground(Color.WHITE);
                        }
                        if (lstProv.getSelectedItem().toString().isEmpty()) {
                            lstProv.setBackground(Color.RED);
                        } else {
                            lstProv.setBackground(Color.WHITE);
                        }
                        if (cp.getText().isEmpty()) {
                            cp.setBackground(Color.RED);
                        } else {
                            cp.setBackground(Color.WHITE);
                        }
                        if (dni2.getText().isEmpty()) {
                            dni2.setBackground(Color.RED);
                        } else {
                            dni2.setBackground(Color.WHITE);
                        }
                        if (email2.getText().isEmpty()) {
                            email2.setBackground(Color.RED);
                        } else {
                            email2.setBackground(Color.WHITE);
                        }
                        if (formaPago.getSelectedIndex() == 0) { // Hemos seleccionado el
                            if (JTFNumTarjeta.getText().isEmpty()) {
                                JTFNumTarjeta.setBackground(Color.RED);
                            } else {
                                JTFNumTarjeta.setBackground(Color.WHITE);
                            }
                            if (JTFTitular.getText().isEmpty()) {
                                JTFTitular.setBackground(Color.RED);
                            } else {
                                JTFTitular.setBackground(Color.WHITE);
                            }
                            if (JTFCVV.getText().isEmpty()) {
                                JTFCVV.setBackground(Color.RED);
                            } else {
                                JTFCVV.setBackground(Color.WHITE);
                            }
                        } else { // Hemos seleccionado pago mediante domiciliacion bancaria
                            if (JTFTitular.getText().isEmpty()) {
                                JTFTitular.setBackground(Color.RED);
                            } else {
                                JTFTitular.setBackground(Color.WHITE);
                            }
                            if (JTFNumCuenta.getText().isEmpty()) {
                                JTFNumCuenta.setBackground(Color.RED);
                            } else {
                                JTFNumCuenta.setBackground(Color.WHITE);
                            }
                        }
                    } else {
                        // Construimos la fecha en el formato: mm/aaaa
                        String ftarj = jcbmeses.getSelectedItem().toString()
                                + "/" + jcbanos2.getSelectedItem().toString();

                        if ((pass2.getText().equals(repass2.getText()))) {
                            int v = adm.registrarCliente(dni2.getText(), pass2.getText(),
                                    nom.getText(),
                                    app.getText(),
                                    email2.getText(),
                                    Integer.parseInt(tfn.getText()),
                                    lstVias.getSelectedItem().toString(),
                                    calle2.getText(),
                                    num2.getText(),
                                    piso2.getText(),
                                    puerta2.getText(),
                                    esc2.getText(),
                                    ciudad2.getText(),
                                    lstProv.getSelectedItem().toString(),
                                    Integer.parseInt(cp.getText()),
                                    formaPago.getSelectedItem().toString(),
                                    tptarjeta.getSelectedItem().toString(),
                                    JTFNumTarjeta.getText(),
                                    ftarj,
                                    JTFTitular.getText(),
                                    Integer.parseInt(JTFCVV.getText()),
                                    JTFNumCuenta.getText());
                            if (v == MSJ.OK) {
                                JOptionPane.showMessageDialog(jpglobal,
                                        "Cliente registrado con exito.\n",
                                        "Registro Correcto",
                                        JOptionPane.DEFAULT_OPTION);
                            } else {
                                JOptionPane.showMessageDialog(jpglobal,
                                        MSJ.Mensaje(v),
                                        "Registro Erroneo",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(jpglobal,
                                    "Error; Las contraseñas no coinciden.\n",
                                    "Error al Registrarse",
                                    JOptionPane.ERROR_MESSAGE);
                            repass2.setText("");
                            pass2.addFocusListener(new FullSelectorListener());
                        }
                    }
                } catch (Exception e2) {
                    JOptionPane.showMessageDialog(jpglobal,
                            "Compruebe el formato de los datos\n",
                            "Datos incorrectos",
                            JOptionPane.ERROR_MESSAGE);
                    try {
                        Integer.parseInt(tfn.getText());
                        tfn.setBackground(Color.WHITE);
                    } catch (Exception ex) {
                        tfn.setBackground(Color.RED);
                    }
                    try {
                        Integer.parseInt(cp.getText());
                        cp.setBackground(Color.WHITE);
                    } catch (Exception ex) {
                        cp.setBackground(Color.RED);
                    }
                    try {
                        Integer.parseInt(JTFCVV.getText());
                        JTFCVV.setBackground(Color.WHITE);
                    } catch (Exception ex) {
                        JTFCVV.setBackground(Color.RED);
                    }
                }
            }
        });
        return jpglobal;
    }

    class EscuchadorTPago implements ActionListener {

        public void actionPerformed(ActionEvent e) {


            jpterceroa.removeAll();
            jpterceroa.updateUI();
            //jpterceroa = new JPanel(new GridBagLayout());
            JTFNumTarjeta = new JTextField(16);
            JTFNumCuenta = new JTextField(20);
            JTFTitular = new JTextField(20);
            JTFCVV = new JTextField(4);
            JCAnyos = new JComboBox(anyos);
            JSPAnyos = new JScrollPane(JCAnyos);
            JCMeses = new JComboBox(meses);
            JSPMeses = new JScrollPane(JCMeses);
            tipoTarjeta = new JComboBox(tiposTarjeta);
            JSPTiposTarjetas = new JScrollPane(tipoTarjeta);

            AyudaGB pos = new AyudaGB();


            JTFNumTarjeta.setToolTipText("Escriba los numeros de su tarjeta "
                    + "sin espacion ni guiones");
            JTFNumCuenta.setToolTipText("Escriba los 20 digitos de su cuenta"
                    + " sin espacios");
            JTFCVV.setToolTipText("Escriba los digitos de CVV de su tarjeta");
            JTFTitular.setToolTipText("Escriba el nombre y apellidos del titular "
                    + "de la tarjeta exactamente igual a como aparecen en la tarjeta");

            JLabel JLQueEs = new JLabel("¿Que es CVV?");
            JLQueEs.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent evt) {
                    mostrarQueEsCVV();
                }
            });

            Image Visa = new ImageIcon(getClass().getResource("/Imagenes/Tarjetas.jpg")).getImage().
                    getScaledInstance(150, 40, 30);
            ImageIcon visaIcon = new ImageIcon(Visa);
            JLabel JLVisa = new JLabel(visaIcon);

            if (formaPago.getSelectedIndex() == 0) { //Pago con Tarjeta

                jpterceroa.add(new JLabel("Tipo Tarjeta*"), pos.nextRow());
                jpterceroa.add(new Gap(GAP), pos.nextCol());
                jpterceroa.add(JSPTiposTarjetas, pos.nextCol());
                jpterceroa.add(new Gap(GAP), pos.nextCol());
                jpterceroa.add(JLVisa, pos.nextCol());

                jpterceroa.add(new Gap(GAP), pos.nextRow());
                jpterceroa.add(new JLabel("Numero Tarjeta*"), pos.nextRow());
                jpterceroa.add(new Gap(GAP), pos.nextCol());
                jpterceroa.add(JTFNumTarjeta, pos.nextCol());
                jpterceroa.add(new Gap(GAP), pos.nextRow());
                jpterceroa.add(new JLabel("Fecha Caducidad*"), pos.nextRow());
                jpterceroa.add(new Gap(GAP), pos.nextCol());
                jpterceroa.add(JSPMeses, pos.nextCol());
                jpterceroa.add(new Gap(GAP), pos.nextCol());
                jpterceroa.add(JSPAnyos, pos.nextCol());
                jpterceroa.add(new Gap(GAP), pos.nextRow());
                jpterceroa.add(new JLabel("Titular*"), pos.nextRow());
                jpterceroa.add(new Gap(GAP), pos.nextCol());
                jpterceroa.add(JTFTitular, pos.nextCol().width(2));
                jpterceroa.add(new Gap(GAP), pos.nextRow());
                jpterceroa.add(new JLabel("Codigo seguridad (CVV)*"), pos.nextRow());
                jpterceroa.add(new Gap(GAP), pos.nextCol());
                jpterceroa.add(JTFCVV, pos.nextCol());
                jpterceroa.add(new Gap(GAP), pos.nextCol());
                jpterceroa.add(JLQueEs, pos.nextCol());
                jpterceroa.add(new Gap(GAP), pos.nextRow());
            } else {

                jpterceroa.add(new JLabel("Numero Cuenta*"), pos.nextRow());
                jpterceroa.add(new Gap(GAP), pos.nextCol());
                jpterceroa.add(JTFNumCuenta, pos.nextCol());
                jpterceroa.add(new Gap(GAP), pos.nextRow());
                jpterceroa.add(new JLabel("Titular*"), pos.nextRow());
                jpterceroa.add(new Gap(GAP), pos.nextCol());
                jpterceroa.add(JTFTitular, pos.nextCol().width(2));
                jpterceroa.add(new Gap(GAP), pos.nextRow());
                jpterceroa.add(new Gap(GAP), pos.nextRow());
            }
            jpterceroa.updateUI();
        }
    }

    private void mostrarQueEsCVV() {
        Image CVV = new ImageIcon(getClass().getResource("/Imagenes/Quees.jpg")).getImage();
        ImageIcon icon = new ImageIcon(CVV);

        JOptionPane.showMessageDialog(null, icon, "Que es CVV",
                JOptionPane.DEFAULT_OPTION);


    }

    public JPanel borrarLineaCliente(final Admin a) {
        JPanel ventana = new JPanel();
        final JPanel cont = new JPanel(new GridBagLayout());
        ventana.setBackground(Color.WHITE);
        cont.setBackground(Color.WHITE);
        AyudaGB pos = new AyudaGB();
        JButton ok = new JButton(" Aceptar");
        final JComboBox jcbClientes = new JComboBox();
        final JComboBox jcbLineas = new JComboBox();
        adm = a;

        cont.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Borrar una linea telefonica",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));
        cont.setBackground(Color.WHITE);
        cont.setPreferredSize(new Dimension(300, 200));

        final ArrayList lstClientes = adm.listarClientesActivos();
        for (int i = 0; i < lstClientes.size(); i++) {
            // No añadimos el administrador
            if (!((Cliente_VO) lstClientes.get(i)).getNif().equals("admin")) {
                jcbClientes.addItem(((Cliente_VO) lstClientes.get(i)).getNif());
            }
        }
        jcbLineas.removeAllItems();
        // antes de que se seleccione nada
        if (lstClientes.size() != 1) {
            ArrayList lstLineas = adm.listaLineasActivasCliente(jcbClientes.getSelectedItem().toString());
            for (int i = 0; i < lstLineas.size(); i++) {
                jcbLineas.addItem(((Linea_VO) lstLineas.get(i)).getNumtlf());
            }
            // cuando se seleccione algo saltara el ActionListener
            jcbClientes.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    jcbLineas.removeAllItems();
                    ArrayList lstLineas = adm.listaLineasActivasCliente(jcbClientes.getSelectedItem().toString());
                    for (int i = 0; i < lstLineas.size(); i++) {
                        jcbLineas.addItem(((Linea_VO) lstLineas.get(i)).getNumtlf());
                    }
                }
            });

            cont.add(new Gap(GAP), pos);
            cont.add(new JLabel("Numero de identificacion(DNI)"), pos.nextRow());
            cont.add(new Gap(GAP), pos.nextRow());
            cont.add(jcbClientes, pos.nextRow());
            cont.add(new Gap(GAP), pos.nextRow());
            cont.add(new JLabel("Numero de telefono"), pos.nextRow());
            cont.add(new Gap(GAP), pos.nextRow());
            cont.add(jcbLineas, pos.nextRow());
            cont.add(new Gap(GAP), pos.nextRow());

            cont.add(ok, pos.nextRow());
            ok.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    int v = adm.DarBajaLineaCliente(jcbClientes.getSelectedItem().toString(), Integer.parseInt(jcbLineas.getSelectedItem().toString()));
                    if (v == MSJ.OK) {
                        JOptionPane.showMessageDialog(cont,
                                "Linea" + jcbLineas.getSelectedItem().toString() + " borrada con exito\n",
                                "Linea borrada",
                                JOptionPane.DEFAULT_OPTION);
                    } else {
                        JOptionPane.showMessageDialog(cont,
                                MSJ.Mensaje(v),
                                "Error",
                                JOptionPane.DEFAULT_OPTION);
                    }
                    jcbLineas.removeAllItems();
                    ArrayList lstLineas = adm.listaLineasActivasCliente(jcbClientes.getSelectedItem().toString());
                    for (int i = 0; i < lstLineas.size(); i++) {
                        jcbLineas.addItem(((Linea_VO) lstLineas.get(i)).getNumtlf());
                    }
                }
            });
        } else {
            JOptionPane.showMessageDialog(null,
                    "No hay clientes dados de alta\n",
                    "Sin clientes",
                    JOptionPane.ERROR_MESSAGE);
        }
        ventana.add(cont);
        return (ventana);
    }

    public JPanel borrarCli(Cliente c, Admin a) {
        final Admin ad = a;
        JPanel ventana = new JPanel();
        final JPanel cont = new JPanel(new GridBagLayout());
        ventana.setBackground(Color.WHITE);
        cont.setBackground(Color.WHITE);
        AyudaGB pos = new AyudaGB();
        final JComboBox jcbClientes = new JComboBox();
        JButton ok = new JButton("Borrar Cliente");
        adm = a;

        final ArrayList lstClientes = adm.listarClientesActivos();
        for (int i = 0; i < lstClientes.size(); i++) {
            // No añadimos el administrador
            if (!((Cliente_VO) lstClientes.get(i)).getNif().equals("admin")) {
                jcbClientes.addItem(((Cliente_VO) lstClientes.get(i)).getNif());
            }
        }

        cont.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Baja de un cliente ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));
        cont.setBackground(Color.WHITE);

        cont.add(new Gap(GAP), pos);
        cont.add(new JLabel("Numero de identificacion(NIF)"), pos.nextRow());
        cont.add(new Gap(GAP), pos.nextRow());
        cont.add(jcbClientes, pos.nextRow());
        cont.add(new Gap(GAP), pos.nextRow());
        cont.add(ok, pos.nextRow());
        cont.setPreferredSize(new Dimension(300, 200));
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (jcbClientes.getSelectedItem() != "") {
                    if (jcbClientes.getSelectedItem().toString().equals("admin")) {
                        JOptionPane.showMessageDialog(cont,
                                "El administrador no puede ser borrado",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int v = ad.borrarCliente(jcbClientes.getSelectedItem().toString());
                    if (v == MSJ.OK) {
                        JOptionPane.showMessageDialog(cont,
                                "Cliente" + jcbClientes.getSelectedItem().toString() + " borrado con exito\n",
                                "Cliente borrado",
                                JOptionPane.DEFAULT_OPTION);
                    } else {
                        JOptionPane.showMessageDialog(cont,
                                MSJ.Mensaje(v),
                                "Error",
                                JOptionPane.DEFAULT_OPTION);
                    }
                    jcbClientes.removeAllItems();
                    final ArrayList lstClientes = adm.listarClientesActivos();
                    for (int i = 0; i < lstClientes.size(); i++) {
                        // No añadimos el administrador
                        if (!((Cliente_VO) lstClientes.get(i)).getNif().equals("admin")) {
                            jcbClientes.addItem(((Cliente_VO) lstClientes.get(i)).getNif());
                        }
                    }
                    if (lstClientes.size() == 1) { // Solo esta el admin
                        jcbClientes.addItem("");
                    }
                }
            }
        });

        ventana.add(cont);

        return (ventana);
    }

    public JPanel AltaLineaCliente(final Admin a) {
        JPanel ventana = new JPanel();
        final JPanel cont = new JPanel(new GridBagLayout());
        ventana.setBackground(Color.WHITE);
        cont.setBackground(Color.WHITE);
        final AyudaGB pos = new AyudaGB();
        JButton ok = new JButton(" Aceptar");
        final JComboBox jcbClientes = new JComboBox();
        adm = a;
        final ArrayList lstClientes = adm.listarClientesActivos();
        for (int i = 0; i < lstClientes.size(); i++) {
            // No añadimos el administrador
            if (!((Cliente_VO) lstClientes.get(i)).getNif().equals("admin")) {
                jcbClientes.addItem(((Cliente_VO) lstClientes.get(i)).getNif());
            }
        }

        cont.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Dar de alta una linea",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));
        cont.setPreferredSize(new Dimension(300, 200));

        cont.setBackground(Color.WHITE);
        cont.add(new Gap(GAP), pos);
        cont.add(new JLabel("Numero de identificacion(DNI)"), pos.nextRow());
        cont.add(new Gap(GAP), pos.nextRow());
        cont.add(jcbClientes, pos.nextRow());
        cont.add(new Gap(GAP), pos.nextRow());
        cont.add(ok, pos.nextRow());
        cont.add(new Gap(GAP), pos.nextRow());
        if (lstClientes.size() != 1) {
            ok.addActionListener(new ActionListener() {

                int numero1;

                public void actionPerformed(ActionEvent e) {

                    numero1 = adm.contratarLineaCliente(jcbClientes.getSelectedItem().toString());
                    if (numero1 > 600000000) {
                        JOptionPane.showMessageDialog(cont,
                                "Nueva linea dada de alta, numero: " + numero1 + "\n",
                                "Nueva Linea OK",
                                JOptionPane.DEFAULT_OPTION);
                    } else {
                        JOptionPane.showMessageDialog(cont,
                                MSJ.Mensaje(numero1),
                                "Error",
                                JOptionPane.DEFAULT_OPTION);
                    }

                }
            });
        } else {
            JOptionPane.showMessageDialog(null,
                    "No hay clientes dados de alta\n",
                    "Sin clientes",
                    JOptionPane.ERROR_MESSAGE);
        }
        ventana.add(cont);

        return (ventana);
    }

    /////////////////////////////////////////////////////////////////////      
    public JPanel topClientes(final Admin a, final BaseDatos bd) {

        JPanel ventana = new JPanel();
        ventana.setBackground(Color.WHITE);
        final JPanel cont = new JPanel(new GridBagLayout());
        cont.setBackground(Color.WHITE);
        final JPanel cont2 = new JPanel(new GridBagLayout());
        cont2.setBackground(Color.WHITE);
        final AyudaGB pos2 = new AyudaGB();
        AyudaGB pos = new AyudaGB();
        JButton ok = new JButton(" Listar ");
        cont.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Listado Top10",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        cont.add(ok, pos.nextRow());
        cont.add(cont2, pos.nextRow());
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                cont2.removeAll();
                ArrayList lista = a.top10();
                cont2.add(new Gap(GAPGRANDE), pos2.nextRow());
                cont2.add(new Gap(GAP), pos2.width(5));
                cont2.add(new JLabel("Lista de los 10 mejores clientes: "), pos2.nextRow());
                cont2.add(new Gap(GAP), pos2.nextCol());
                cont2.add(new JLabel("Total facturado"), pos2.nextCol());
                cont2.add(new Gap(GAP), pos2.nextCol());
                cont2.add(new Gap(GAP), pos2.nextRow());

                int n = lista.size();
                for (int i = 0; i < n; i++) {
                    // No añadimos el administrador
                    if (!((Cliente_VO) lista.get(i)).getNif().equals("admin")) {
                        // Eliminamos los clientes con 0€ facturados
                        Cliente cli = new Cliente(bd, (Cliente_VO) lista.get(i));
                        if (cli.importeTotalCliente() != 0) {
                            cont2.add(new JLabel(((Cliente_VO) lista.get(i)).getNif()), pos2.nextRow());
                            NumberFormat NF = NumberFormat.getInstance();
                            NF.setMaximumFractionDigits(4); //2 decimales
                            cont2.add(new Gap(GAP), pos2.nextCol());
                            cont2.add(new JLabel(NF.format(cli.importeTotalCliente()) + " €"), pos2.nextCol());
                        }
                    }
                }
                cont2.updateUI();
            }
        });
        ventana.add(cont);
        return (ventana);
    }

    public JPanel mostrarLineas(final Admin a) {
        JPanel ventana = new JPanel();
        ventana.setBackground(Color.WHITE);
        final JPanel cont = new JPanel(new GridBagLayout());
        final JPanel cont2 = new JPanel(new GridBagLayout());
        cont.setBackground(Color.WHITE);
        cont2.setBackground(Color.WHITE);
        final AyudaGB pos = new AyudaGB();
        final AyudaGB pos2 = new AyudaGB();
        final JComboBox jcbClientes = new JComboBox();
        adm = a;

        final ArrayList lstClientes = adm.listarTodosLosClientes();
        jcbClientes.addItem("Seleccionar cliente");
        for (int i = 0; i < lstClientes.size(); i++) {
            // No añadimos el administrador
            if (!((Cliente_VO) lstClientes.get(i)).getNif().equals("admin")) {
                jcbClientes.addItem(((Cliente_VO) lstClientes.get(i)).getNif());
            }
        }

        cont.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Listar lineas ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));

        cont.setBackground(Color.WHITE);
        cont.add(new Gap(GAP), pos);
        cont.add(new JLabel("Numero de identificacion(DNI)"), pos.nextRow());
        cont.add(new Gap(GAP), pos.nextRow());
        cont.add(jcbClientes, pos.nextRow());
        cont.add(new Gap(GAP), pos.nextRow());
        cont.add(cont2, pos.nextRow());
        jcbClientes.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                cont2.removeAll();
                if (jcbClientes.getSelectedIndex() != 0) {
                    ArrayList lista = a.listaLineasActivasCliente(jcbClientes.getSelectedItem().toString());
                    cont2.add(new Gap(GAPGRANDE), pos2.nextRow());
                    cont2.add(new Gap(GAP), pos2.width(5));
                    cont2.add(new JLabel("Lista de las lineas telefonicas: "), pos2.nextRow());
                    cont2.add(new Gap(GAP), pos2.nextCol());
                    cont2.add(new Gap(GAP), pos2.nextRow());
                    int n = lista.size();
                    for (int i = 0; i < n; i++) {
                        cont2.add(new JLabel(Integer.toString(((Linea_VO) lista.get(i)).getNumtlf())), pos2.nextRow());
                        cont2.add(new Gap(GAP), pos2.nextCol());
                    }
                }
                cont2.updateUI();
            }
        });

        ventana.add(cont);
        return (ventana);
    }

    public JPanel Importefechas(final Admin a) {
        JPanel ventana = new JPanel();
        ventana.setBackground(Color.WHITE);
        final JPanel cont = new JPanel(new GridBagLayout());
        cont.setBackground(Color.WHITE);
        AyudaGB pos = new AyudaGB();
        final JPanel cont2 = new JPanel(new GridBagLayout());
        cont2.setBackground(Color.WHITE);
        final AyudaGB pos2 = new AyudaGB();
        adm = a;

        JButton ok = new JButton("Aceptar");
        final JTextField anoini = new JTextField(5);
        final JTextField mesini = new JTextField(5);
        final JTextField diaini = new JTextField(5);
        final JTextField anofin = new JTextField(5);
        final JTextField mesfin = new JTextField(5);
        final JTextField diafin = new JTextField(5);
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {

                try {
                    //Comprobamos que todos los campos obligatorios estan rellenos
                    boolean completo = true;
                    //cont.remove(cont2);
                    int anoi = 0;
                    int mesi = 0;
                    int diai = 0;
                    int anof = 0;
                    int mesf = 0;
                    int diaf = 0;

                    anoi = Integer.parseInt(anoini.getText());
                    mesi = Integer.parseInt(mesini.getText());
                    diai = Integer.parseInt(diaini.getText());
                    anof = Integer.parseInt(anofin.getText());
                    mesf = Integer.parseInt(mesfin.getText());
                    diaf = Integer.parseInt(diafin.getText());

                    completo = completo
                            & !anoini.getText().isEmpty()
                            & !mesini.getText().isEmpty()
                            & !diaini.getText().isEmpty()
                            & !anofin.getText().isEmpty()
                            & !mesfin.getText().isEmpty()
                            & !diafin.getText().isEmpty();

                    if (!completo) {
                        JOptionPane.showMessageDialog(cont,
                                "Error; Debe rellenar todos los campos marcados con un *.\n",
                                "Error al listar facturas entre dos fechas dadas",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        String fechaini = ((anoi >= 1800 && anoi < 9999) ? Integer.toString(anoi) : "1800") + "/"
                                + ((mesi < 10) ? "0" + Integer.toString(mesi) : ((mesi < 13) ? Integer.toString(mesi) : "12")) + "/"
                                + ((diai < 10) ? "0" + Integer.toString(diai) : ((diai < 31) ? Integer.toString(diai) : "31")) + "/00/00";
                        String fechafin = ((anof >= 1800 && anof < 9999) ? Integer.toString(anof) : "1800") + "/"
                                + ((mesf < 10) ? "0" + Integer.toString(mesf) : ((mesf < 13) ? Integer.toString(mesf) : "12")) + "/"
                                + ((diaf < 10) ? "0" + Integer.toString(diaf) : ((diaf < 32) ? Integer.toString(diaf) : "31")) + "/00/00";
                        double valor;

                        valor = adm.importeEntreDosFechas(fechaini, fechafin);

                        cont2.removeAll();
                        cont2.add(new Gap(GAPGRANDE), pos2.nextRow());
                        cont2.add(new Gap(GAP), pos2.width(5));
                        cont2.add(new JLabel("Valor de la factura entre las dos fechas dadas: "), pos2.nextRow());
                        cont2.add(new Gap(GAP), pos2.nextCol());
                        cont2.add(new Gap(GAP), pos2.nextRow());
                        cont2.add(new JLabel(Double.toString(valor)), pos2.nextRow());
                        cont2.add(new Gap(GAP), pos2.nextCol());

                        cont2.updateUI();
                        cont.updateUI();
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(cont,
                            "Compruebe el formato de los datos\n",
                            "Datos incorrectos",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cont.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(BORDER, BORDER, BORDER, BORDER, Color.black),
                " Listado entre dos fechas",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                DEFAULTFONT, Color.BLACK));
        cont.setBackground(Color.WHITE);

        cont.add(new Gap(GAP), pos);
        cont.add(new JLabel("Fecha inicial:         Dia: "), pos.nextCol().align(AyudaGB.EAST));
        cont.add(new Gap(GAP), pos.nextCol());
        cont.add(diaini, pos.nextCol());
        cont.add(new Gap(GAP), pos.nextCol());
        cont.add(new JLabel("Mes: "), pos.nextCol());
        cont.add(new Gap(GAP), pos.nextCol());
        cont.add(mesini, pos.nextCol());
        cont.add(new Gap(GAP), pos.nextCol());
        cont.add(new JLabel("Año: "), pos.nextCol());
        cont.add(new Gap(GAP), pos.nextCol());
        cont.add(anoini, pos.nextCol());

        cont.add(new Gap(GAP), pos.nextRow());
        cont.add(new JLabel("Fecha final,           Dia: "), pos.nextCol().align(AyudaGB.EAST));
        cont.add(new Gap(GAP), pos.nextCol());
        cont.add(diafin, pos.nextCol());
        cont.add(new Gap(GAP), pos.nextCol());
        cont.add(new JLabel("Mes: "), pos.nextCol());
        cont.add(new Gap(GAP), pos.nextCol());
        cont.add(mesfin, pos.nextCol());
        cont.add(new Gap(GAP), pos.nextCol());
        cont.add(new JLabel("Año: "), pos.nextCol());
        cont.add(new Gap(GAP), pos.nextCol());
        cont.add(anofin, pos.nextCol());

        cont.add(new Gap(GAP), pos.nextRow());
        cont.add(ok, pos.nextCol().width(11).align(AyudaGB.EAST));

        cont.add(cont2, pos.nextRow().expandW().width(12));

        ventana.add(cont);

        return (ventana);
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
