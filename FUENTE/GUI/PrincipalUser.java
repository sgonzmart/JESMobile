/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import TADS.*;
import BaseDatos.*;

public final class PrincipalUser extends JFrame {

    Image logo = new ImageIcon(getClass().getResource("/Imagenes/Logo.png"))
            .getImage().getScaledInstance(200, 150, 10);
    Image inicio = new ImageIcon(getClass().getResource("/Imagenes/Welcome.gif"))
            .getImage().getScaledInstance(468, 60, 10);
    ImageIcon logoIcon = new ImageIcon(logo);
    ImageIcon inicioIcon = new ImageIcon(inicio);
    JLabel jlImagen = new JLabel(logoIcon);
    JLabel jlImagenini = new JLabel(inicioIcon, JLabel.CENTER);
    JButton jbDesconectar;
    JPanel ventana, titulo, menu, contenido;
    JPanel menuFac, menuLin, menuCon, menuDat;
    JFrame superTienda;
    JLabel jlNombre, jlInicio, jlFacturacion, jlLineas, jlDatos, jlConsumo, jlMensaje;
    JLabel jlUltimaFac, jlFacturasAnt, jlmisLineas, jlConsumoAct, jlDatosPer, jlModifDatos;
    BaseDatos BD;
    Cliente gc;
    Cliente_VO c;
    Factura fac;
    Linea lin;
    JScrollPane scroll = new JScrollPane();
    private static final int GAP = 5;
    private static final int GAPGRANDE = 20;
    Contenido cont;

    public PrincipalUser(JFrame Tienda, Cliente g, BaseDatos db) {
        superTienda = Tienda;
        gc = g;
        c = gc.getCliente();
        BD = db;
        titulo = new JPanel(new GridBagLayout());
        titulo.setBackground(Color.ORANGE);
        menu = new JPanel(new GridBagLayout());
        menuFac = new JPanel(new GridBagLayout());
        menuCon = new JPanel(new GridBagLayout());
        menuLin = new JPanel(new GridBagLayout());
        menuDat = new JPanel(new GridBagLayout());
        contenido = new JPanel(new GridBagLayout());
        ventana = new JPanel(new BorderLayout());
        jbDesconectar = new JButton("Desconectar");
        jbDesconectar.addActionListener(new EscuchadorDesconectar());
        cont = new Contenido();
        fac = new Factura(db);
        lin = new Linea(db);
        jlNombre = new JLabel(c.getNombre() + " " + c.getApellidos());
        jlMensaje = new JLabel("Nombre del cliente:   ");
        AyudaGB posTitulo = new AyudaGB();
        AyudaGB posMenu = new AyudaGB();
        final Servicio ser = new Servicio(db);

        titulo.add(new Gap(GAPGRANDE), posTitulo.nextRow());
        titulo.add(new Gap(GAP), posTitulo.nextRow());
        titulo.add(jlImagen, posTitulo.nextCol().width(2).align(AyudaGB.WEST));
        titulo.add(new Gap(GAP), posTitulo.nextCol());
        titulo.add(jlMensaje, posTitulo.nextCol().align(AyudaGB.SOUTHEAST).expandW());
        titulo.add(jlNombre, posTitulo.nextCol().align(AyudaGB.SOUTHEAST));
        titulo.add(new Gap(4 * GAP), posTitulo.nextCol());
        titulo.add(jbDesconectar, posTitulo.nextCol().align(AyudaGB.SOUTHEAST));
        titulo.add(new Gap(GAP), posTitulo.nextRow());
        contenido.add(jlImagenini);
        //Definimos las etiquetas del menu y su comportamiento
        jlInicio = new JLabel(" > Inicio ");
        jlInicio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlInicio.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                contenido.removeAll();
                menuFac.removeAll();
                menuLin.removeAll();
                menuCon.removeAll();
                menuDat.removeAll();
                contenido.add(jlImagenini);
                ventana.updateUI();
            }
        });

        jlFacturacion = new JLabel(" > Facturacion ");
        jlFacturacion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlFacturacion.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                menuFac.removeAll();
                menuLin.removeAll();
                menuCon.removeAll();
                menuDat.removeAll();
                AyudaGB posMenuFac = new AyudaGB();
                jlFacturasAnt = new JLabel("      > Facturas Anteriores  ");
                jlUltimaFac = new JLabel("      > Ultima Factura  ");
                jlUltimaFac.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                jlFacturasAnt.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuFac.add(new Gap(GAP), posMenuFac.nextRow());
                menuFac.add(jlUltimaFac, posMenuFac.nextRow());
                menuFac.add(new Gap(GAP), posMenuFac.nextRow());
                menuFac.add(jlFacturasAnt, posMenuFac.nextRow());
                menuFac.add(new Gap(GAP), posMenuFac.nextRow());
                contenido.removeAll();
                contenido.add(cont.mostrarFactura(gc, BD));
                ventana.updateUI();

                jlFacturasAnt.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent evt) {
                        contenido.removeAll();
                        contenido.add(cont.facturasAnteriores(fac, gc, lin, BD));
                        ventana.updateUI();
                    }
                });

                jlUltimaFac.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent evt) {
                        contenido.removeAll();
                        contenido.add(cont.mostrarFactura(gc, BD));
                        ventana.updateUI();
                    }
                });
            }
        });

        jlLineas = new JLabel(" > Mis Lineas ");
        jlLineas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlLineas.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                menuFac.removeAll();
                menuLin.removeAll();
                menuCon.removeAll();
                menuDat.removeAll();
                AyudaGB posMenuTar = new AyudaGB();
                jlmisLineas = new JLabel("      > Mis Lineas ");
                jlmisLineas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                menuLin.add(new Gap(GAP), posMenuTar.nextRow());
                menuLin.add(jlmisLineas, posMenuTar.nextRow());
                menuLin.add(new Gap(GAP), posMenuTar.nextRow());
                contenido.removeAll();
                contenido.add(cont.misLineas(gc));
                ventana.updateUI();

                jlmisLineas.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent evt) {
                        contenido.removeAll();
                        contenido.add(cont.misLineas(gc));
                        ventana.updateUI();
                    }
                });


            }
        });

        jlConsumo = new JLabel(" > Mi Consumo ");
        jlConsumo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlConsumo.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                menuFac.removeAll();
                menuLin.removeAll();
                menuCon.removeAll();
                menuDat.removeAll();
                AyudaGB posMenuCon = new AyudaGB();

                jlConsumoAct = new JLabel("      > Informacion de mi consumo ");
                jlConsumoAct.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                menuCon.add(new Gap(GAP), posMenuCon.nextRow());
                menuCon.add(jlConsumoAct, posMenuCon.nextRow());
                menuCon.add(new Gap(GAP), posMenuCon.nextRow());
                contenido.removeAll();
                contenido.add(cont.consumoActual(gc, ser));
                ventana.updateUI();
            }
        });

        jlDatos = new JLabel(" > Mis Datos ");
        jlDatos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        jlDatos.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                menuFac.removeAll();
                menuLin.removeAll();
                menuCon.removeAll();
                menuDat.removeAll();
                AyudaGB posMenuDat = new AyudaGB();
                final AyudaGB pos = new AyudaGB();
                jlDatosPer = new JLabel("      > Datos Personales ");
                jlModifDatos = new JLabel("      > Modificar Datos Personales ");

                menuDat.add(new Gap(GAP), posMenuDat.nextRow());
                menuDat.add(jlDatosPer, posMenuDat.nextRow());
                menuDat.add(new Gap(GAP), posMenuDat.nextRow());
                menuDat.add(jlModifDatos, posMenuDat.nextRow());
                menuDat.add(new Gap(GAP), posMenuDat.nextRow());
                contenido.removeAll();
                contenido.add(cont.datosPersonales(gc), pos.expandW());
                ventana.updateUI();

                jlDatosPer.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent evt) {
                        contenido.removeAll();
                        contenido.add(cont.datosPersonales(gc), pos.expandW());
                        ventana.updateUI();
                    }
                });

                jlModifDatos.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent evt) {
                        contenido.removeAll();
                        contenido.add(cont.modifDPersonales(gc), pos.expandW());
                        ventana.updateUI();
                    }
                });

            }
        });

        menu.add(new Gap(GAPGRANDE), posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlInicio, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlFacturacion, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(menuFac, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlLineas, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(menuLin, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlConsumo, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(menuCon, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(jlDatos, posMenu.nextRow());
        menu.add(new Gap(GAP), posMenu.nextRow());
        menu.add(menuDat, posMenu.nextRow());
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
            Principal v = new Principal(BD);
        }
    }
}
