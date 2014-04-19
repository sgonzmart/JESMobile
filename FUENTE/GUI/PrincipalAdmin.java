/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

//import BaseDatos.Cliente_VO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import TADS.*;
import BaseDatos.*;
import Simulador.*;

public final class PrincipalAdmin extends JFrame {

    Image logo = new ImageIcon(getClass().getResource("/Imagenes/Logo.png"))
            .getImage().getScaledInstance(190, 150, 10);
    Image fondo = new ImageIcon(getClass().getResource("/Imagenes/Logo.png"))
            .getImage().getScaledInstance(300, 250, 10);
    ImageIcon logoIcon = new ImageIcon(logo);
    ImageIcon logofondo = new ImageIcon(fondo);
    private static final int WINDOWBORDER = 10;
    JLabel jlImagen = new JLabel(logoIcon);
    JLabel jlImagenfondo = new JLabel(logofondo, JLabel.CENTER);
    JButton jbDesconectar;
    JPanel ventana, titulo, menu, contenido;
    JPanel menuFac, menuLin, menuCon, menuDat, menuCli, menuTop, menuAltas, menuBajas, menuFechas;
    JPanel menulinBaja, menulinAlta, menuMod, menuDatos, menuFacturar;
    JFrame superTienda;
    JLabel jlNombre, jlCliente, jlInicio, jlFacturacion, jlLineas, jlDatos, jlConsumo, jlFacturar;
    JLabel jlFacturasAnt, jlmisLineas, jlConsumoAct, jlDatosPer, jlModifDatos;
    JLabel jlmodifLineas, jlAltas, jlBajas, jlTop, jlFacturaFechas, jlUltimaFac, jlMensaje;
    JLabel jlLineasAlta, jlLineasBaja, jlModificar, jlSimulador;
    Cliente gc;
    Admin ga;
    Cliente_VO c;
    Factura fac;
    BaseDatos bd;
    Linea lin;
    JScrollPane scroll = new JScrollPane();
    private static final int GAP = 5;
    private static final int GAPGRANDE = 20;
    ContenidoAdmin cont;

    public PrincipalAdmin(JFrame Tienda, Cliente g, Admin a, BaseDatos db) {
        bd = db;
        superTienda = Tienda;
        ga = new Admin(db);
        gc = g;
        c = gc.getCliente();
        titulo = new JPanel(new GridBagLayout());
        menu = new JPanel(new GridBagLayout());
        menuCli = new JPanel(new GridBagLayout());
        menuFac = new JPanel(new GridBagLayout());
        menuCon = new JPanel(new GridBagLayout());
        contenido = new JPanel(new GridBagLayout());
        ventana = new JPanel(new BorderLayout());
        jbDesconectar = new JButton("Desconectar");
        jbDesconectar.addActionListener(new EscuchadorDesconectar());
        cont = new ContenidoAdmin();
        fac = new Factura(db);
        lin = new Linea(db);
        jlNombre = new JLabel(c.getNombre() + " " + c.getApellidos());
        jlMensaje = new JLabel("¡Bienvenido Administrador!  ");
        AyudaGB posTitulo = new AyudaGB();
        AyudaGB posMenu = new AyudaGB();

        titulo.add(new Gap(GAPGRANDE), posTitulo.nextRow());
        titulo.add(new Gap(GAP), posTitulo.nextRow());
        titulo.add(jlImagen, posTitulo.nextCol().width(2).align(AyudaGB.WEST));
        titulo.add(new Gap(GAPGRANDE), posTitulo.nextCol());
        titulo.setBackground(Color.ORANGE);
        titulo.add(new Gap(GAP), posTitulo.nextCol());
        titulo.add(jlMensaje, posTitulo.nextCol().align(AyudaGB.SOUTHEAST).expandW());
        titulo.add(jlNombre, posTitulo.nextCol().align(AyudaGB.SOUTHEAST));
        titulo.add(new Gap(4 * GAP), posTitulo.nextCol());
        titulo.add(jbDesconectar, posTitulo.nextCol().align(AyudaGB.SOUTHEAST));
        titulo.add(new Gap(GAP), posTitulo.nextRow());

        contenido.add(jlImagenfondo);
        //Definimos las etiquetas del menu y su comportamiento
        JLabel JInicioaux = new JLabel("Inicio");
        JInicioaux.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JInicioaux.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(jlImagenfondo);
                contenido.updateUI();
            }
        });


        jlFacturacion = new JLabel("Visualizar facturas de un cliente");
        jlFacturacion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlFacturacion.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(cont.mostrarFactura(ga, gc,bd));
                ventana.updateUI();
            }
        });



        jlFacturar = new JLabel("Facturar");
        jlFacturar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlFacturar.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(cont.Facturar(gc, ga));
                ventana.updateUI();
            }
        });


        jlLineas = new JLabel("Consultar lineas de un cliente");
        jlLineas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlLineas.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(cont.mostrarLineas(ga));
                ventana.updateUI();
            }
        });

        jlLineasAlta = new JLabel("Dar de alta una nueva linea");
        jlLineasAlta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlLineasAlta.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(cont.AltaLineaCliente(ga));
                ventana.updateUI();
            }
        });


        jlLineasBaja = new JLabel("Dar de baja una linea");
        jlLineasBaja.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlLineasBaja.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(cont.borrarLineaCliente(ga));
                ventana.updateUI();
            }
        });


        jlAltas = new JLabel("Registrar un nuevo cliente");
        jlAltas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlAltas.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(cont.registrar(ga));
                ventana.updateUI();
            }
        });


        jlBajas = new JLabel("Dar de baja a un Cliente");
        jlBajas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlBajas.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(cont.borrarCli(gc, ga));
                ventana.updateUI();
            }
        });


        jlDatos = new JLabel("Mostrar los datos de un cliente");
        jlDatos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlDatos.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(cont.datosPersonales(ga));
                ventana.updateUI();
            }
        });


        jlModificar = new JLabel("Modificar los datos del cliente ");
        jlModificar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlModificar.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(cont.modifDPersonales(ga));
                ventana.updateUI();
            }
        });


        jlTop = new JLabel("Listar el TOP 10 de clientes");
        jlTop.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlTop.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(cont.topClientes(ga, bd));
                ventana.updateUI();
            }
        });

        jlFacturaFechas = new JLabel("Consultar facturas entre dos fechas");
        jlFacturaFechas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlFacturaFechas.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                contenido.add(cont.Importefechas(ga));
                ventana.updateUI();
            }
        });

        jlSimulador = new JLabel("Simulador");
        jlSimulador.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlSimulador.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                superTienda.dispose();
                ventanaPrincipalSimulador v = new ventanaPrincipalSimulador(bd);

            }
        });

        menu.setBorder(BorderFactory.createEmptyBorder(WINDOWBORDER,
                WINDOWBORDER, WINDOWBORDER, WINDOWBORDER));
        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(JInicioaux, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());

        menu.add(jlFacturacion, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(menuFac, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());

        menu.add(jlFacturar, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());

        menu.add(jlLineas, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlLineasAlta, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlLineasBaja, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlAltas, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlBajas, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlDatos, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlModificar, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlTop, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlFacturaFechas, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlSimulador, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());

        // Norte
        ventana.add(titulo, BorderLayout.NORTH);

        // Oeste
        ventana.add(menu, BorderLayout.WEST);

        // Centro
        contenido.setBackground(Color.WHITE);
        scroll.setViewportView(contenido);

        ventana.add(scroll, BorderLayout.CENTER);

        superTienda.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        superTienda.setLocation(350, 0);
        superTienda.setContentPane(ventana);
        superTienda.pack();
        superTienda.setSize(800, 700);
        superTienda.setVisible(true);
    }

    class EscuchadorDesconectar implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            superTienda.dispose();
            Principal v = new Principal(bd);
        }
    }
}
