package BaseDatos;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import MSJ.MSJ;
import java.util.ArrayList;

public class BaseDatos {

    private Connection con = null;
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String protocol = "jdbc:derby:";
    private String dbName = "TfnDB";

    /* BASE DE DATOS */
    /**
     * Crea una conexion con la base de datos de nombre "dbName".
     */
    public void conectarBD() {

        try {
            Class.forName(driver);//.newInstance();
        } catch (Exception ex) {
            System.out.println("error en el Driver: " + ex.getMessage());
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            con = DriverManager.getConnection(protocol + dbName + ";create=true");
        } catch (Exception ex) {
            System.out.print("error en la conexion a la BD: " + ex.getMessage());
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crea la base de datos si no existe aun.
     *
     * @throws SQLException
     */
    public void creaBD() {

        PreparedStatement psCreate = null;

        // CLIENTE
        try {
            System.out.print("\tTabla clientes ");
            psCreate = con.prepareStatement("CREATE TABLE CLIENTE ("
                    + "nif          VARCHAR(9)      ,"
                    + "pass         VARCHAR(20)      NOT NULL, "
                    + "nombre       VARCHAR(15)     NOT NULL, "
                    + "apellidos    VARCHAR(50)     NOT NULL, "
                    + "email        VARCHAR(100)    NOT NULL, "
                    + "telefono     INTEGER, "
                    + "tipovia      VARCHAR(15)     NOT NULL, "
                    + "nomvia       VARCHAR(50)     NOT NULL, "
                    + "numcalle     VARCHAR(10)     NOT NULL, "
                    + "piso         VARCHAR(10), "
                    + "puerta       VARCHAR(10), "
                    + "escalera     VARCHAR(10)     NOT NULL, "
                    + "localidad    VARCHAR(50)     NOT NULL, "
                    + "provincia    VARCHAR(50)     NOT NULL, "
                    + "codpostal    INTEGER         NOT NULL, "
                    + "tipopago     VARCHAR(25)     NOT NULL, "
                    + "tipotarjeta  VARCHAR(20), "
                    + "numtarjet    VARCHAR(18), "
                    + "caducidad    VARCHAR(20), " // fecha caducidad de la tarjeta: aaaa/mm/dd/hh/mm/ss = aaaa/mm/00/00/00/00/00
                    + "titular      VARCHAR(100)    NOT NULL, "
                    + "codseg       INTEGER, "
                    + "numcuenta    VARCHAR(20), "
                    + "estado       INTEGER         NOT NULL, "
                    + "PRIMARY KEY (nif))");

            psCreate.executeUpdate();
            System.out.println("Cliente introducido correctamente");
        } catch (SQLException z) {
            System.out.println("Excepcion: " + z.getMessage());
        }

        // LINEA
        try {
            System.out.print("\tTabla Linea ");
            psCreate = con.prepareStatement("CREATE TABLE LINEA ("
                    + "numtlf       INTEGER,"
                    + "nif  VARCHAR(9),"
                    + "estado INTEGER NOT NULL, "
                    + "FOREIGN KEY (nif)  REFERENCES CLIENTE(nif),"
                    + "PRIMARY KEY (numtlf))");

            psCreate.executeUpdate();
            System.out.println("introducida correctamente");
        } catch (SQLException z) {
            System.out.println("Excepcion: " + z.getMessage());
        }

        // FACTURA
        try {
            System.out.print("\tTabla Factura ");
            psCreate = con.prepareStatement("CREATE TABLE FACTURA ("
                    + "numfactura       INTEGER     GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                    + "importe          REAL, "
                    + "fechainicio      VARCHAR(20), "
                    + "fechafin         VARCHAR(20), "
                    + "estapagada       INTEGER     NOT NULL, "
                    + "numtlf       INTEGER,"
                    + "FOREIGN KEY (numtlf)  REFERENCES LINEA(numtlf),"
                    + "PRIMARY KEY (numfactura))");

            psCreate.executeUpdate();
            System.out.println("introducida correctamente");
        } catch (SQLException z) {
            System.out.println("Excepcion: " + z.getMessage());
        }


        // SERVICIOS
        try {
            System.out.print("\tTabla Servicios ");
            psCreate = con.prepareStatement("CREATE TABLE SERVICIO ("
                    + "idserv       INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                    + "codserv      INTEGER     NOT NULL, "
                    + "numdestino   INTEGER     NOT NULL, "
                    + "fecha        VARCHAR(20) NOT NULL, "
                    + "duracion     INTEGER, "
                    + "datos        INTEGER, "
                    + "idfactura	  INTEGER,"
                    + "numtlf   INTEGER,"
                    + "PRIMARY KEY (idserv), "
                    + "FOREIGN KEY (idfactura)  REFERENCES FACTURA (numfactura),"
                    + "FOREIGN KEY (numtlf)  REFERENCES LINEA(numtlf))");
            psCreate.executeUpdate();
            System.out.println("introducida correctamente");
        } catch (SQLException z) {
            System.out.println("Excepcion: " + z.getMessage());
        }
    }

    /**
     * Destruye la conexion creada con la base de datos
     */
    public void desconectarBD() {
        try {
            DriverManager.getConnection(protocol + ";shutdown=true");
        } catch (SQLException ex) {
            if (!((ex.getErrorCode() == 50000) && ("XJ015".equals(ex.getSQLState())))) {
                Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (Exception ex) {
                Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // CLIENTES //--------------------------------------------------------------
    /**
     * Inserta un cliente en la base de datos
     * @param x
     * @return 
     */
    public int insertaCliente(Cliente_VO x) {

        PreparedStatement psInsert = null;
        try {
            psInsert = con.prepareStatement("INSERT into CLIENTE values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            psInsert.setString(1, x.getNif());
            psInsert.setString(2, x.getPass());
            psInsert.setString(3, x.getNombre());
            psInsert.setString(4, x.getApellidos());
            psInsert.setString(5, x.getEmail());
            psInsert.setInt(6, x.getTelefono());
            psInsert.setString(7, x.getTipovia());
            psInsert.setString(8, x.getNomvia());
            psInsert.setString(9, x.getNumcalle());
            psInsert.setString(10, x.getPiso());
            psInsert.setString(11, x.getPuerta());
            psInsert.setString(12, x.getEscalera());
            psInsert.setString(13, x.getLocalidad());
            psInsert.setString(14, x.getProvincia());
            psInsert.setInt(15, x.getCodpostal());
            psInsert.setString(16, x.getTipopago());
            psInsert.setString(17, x.getTipotarjeta());
            psInsert.setString(18, x.getNumtarjeta());
            psInsert.setString(19, x.getCaducidad());
            psInsert.setString(20, x.getTitular());
            psInsert.setInt(21, x.getCodseg());
            psInsert.setString(22, x.getNumcuenta());
            psInsert.setInt(23, x.getEstado());
            psInsert.executeUpdate();
            return MSJ.OK;
        } catch (Exception ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print("error insertar: ");
            return MSJ.INSERCION_INCORRECTA;
        }
    }

    /**
     * Actualiza los datos de una fila de la tabla "Clientes" cuyo x.nif debe existir.
     * @param x Objeto que contiene los datos a cambiar en la tabla incluyendo el nif.
     * @return <code>ELIMINACION_CLIENTE_ERRONEA</code> Si la actualizacion se realiza.<br>
     *         <code>OK</code> Si la actualizacion falla.
     */
    public int actualizaCliente(Cliente_VO x) {

        PreparedStatement psUpdate = null;

        try {
            String query1 = "pass = '" + x.getPass() + "'"
                    + ", nombre = '" + x.getNombre() + "'"
                    + ", apellidos = '" + x.getApellidos() + "'"
                    + ", email = '" + x.getEmail() + "'"
                    + ", telefono = " + x.getTelefono()
                    + ", tipovia = '" + x.getTipovia() + "'"
                    + ", nomvia = '" + x.getNomvia() + "'"
                    + ", numcalle = '" + x.getNumcalle() + "'"
                    + ", piso = '" + x.getPiso() + "'"
                    + ", puerta = '" + x.getPuerta() + "'"
                    + ", escalera = '" + x.getEscalera() + "'"
                    + ", localidad = '" + x.getLocalidad() + "'"
                    + ", provincia = '" + x.getProvincia() + "'"
                    + ", codpostal = " + x.getCodpostal()
                    + ", tipopago = '" + x.getTipopago() + "'"
                    + ", tipotarjeta = '" + x.getTipotarjeta() + "'"
                    + ", numtarjet = '" + x.getNumtarjeta() + "'"
                    + ", caducidad = '" + x.getCaducidad() + "'"
                    + ", titular = '" + x.getTitular() + "'"
                    + ", codseg = " + x.getCodseg()
                    + ", numcuenta = '" + x.getNumcuenta() + "'"
                    + ", estado = " + x.getEstado();

            query1 = "UPDATE CLIENTE SET " + query1 + " WHERE nif = '" + x.getNif() + "'";

            psUpdate = con.prepareStatement(query1);
            psUpdate.executeUpdate();
            return (MSJ.OK);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Actualizacion Erronea");
            return (MSJ.ACTUALIZACION_CLIENTE_ERRONEA);
        }
    }

    /**
     * Elimina los datos de una fila de la tabla "Clientes" cuyo x.NIF debe existir.
     * Ademas tambien elimina todos los elementos de las demás tablas que tengan relaciÃ³n,
     * como facturas, lineas.
     */
    public int eliminaCliente(Cliente_VO x) {
        PreparedStatement psDelete = null;

        try {
            // Elimina la referencia al Cliente en LINEA
            Linea_VO p1;
            Linea_VO p2 = new Linea_VO(0, x.getNif(), 0);
            ArrayList lista = buscaLinea(p2);
            for (int i = 0; i < lista.size(); i++) {
                p1 = (Linea_VO) lista.get(i);
                p2 = new Linea_VO(p1.getNumtlf(), "", 0);
                actualizaLinea(p2);
            }

            // Elimina el Cliente.
            String query = "DELETE FROM CLIENTE WHERE nif= '" + x.getNif() + "'";

            psDelete = con.prepareStatement(query);
            psDelete.executeUpdate();
            return (MSJ.OK);
        } catch (SQLException ex) {

            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return (MSJ.ELIMINACION_CLIENTE_ERRONEA);
        }
    }

    /**
     * Busca todas las filas de la tabla "Clientes".
     * return Una lista con todos los objetos CLIENTE de la base de datos.
     */
    public ArrayList listaClientes() {

        PreparedStatement psSearch = null;

        try {
            psSearch = con.prepareStatement("SELECT COUNT(nif) FROM CLIENTE");
            ResultSet rs = psSearch.executeQuery();
            rs.next();
            int tamanio = rs.getInt(1);

            // Crea una lista con los objetos encontrados.
            ArrayList listaClientes = new ArrayList(tamanio);

            psSearch = con.prepareStatement("SELECT * FROM CLIENTE");
            rs = psSearch.executeQuery();
            while (rs.next()) {
                Cliente_VO a = new Cliente_VO(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getInt(6),
                        rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13),
                        rs.getString(14), rs.getInt(15),
                        rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19),
                        rs.getString(20), rs.getInt(21), rs.getString(22), rs.getInt(23));
                listaClientes.add(a);
            }
            return listaClientes;

        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Busca las filas de la tabla "CLIENTE" que contengan todos los campos del objeto
     * que se pasa como argumento.
     * DEVUELVE: Una lista con todos los objetos CLIENTE de la base de datos.
     */
    public ArrayList buscaCliente(Cliente_VO x) {

        PreparedStatement psSearch = null;
        try {
            String query1 = (x.getNif().equals("")) ? "" : "nif = '" + x.getNif() + "'";
            String query2 = (x.getPass().equals("")) ? "" : "pass = '" + x.getPass() + "'";
            String query3 = (x.getNombre().equals("")) ? "" : "nombre = '" + x.getNombre() + "'";
            String query4 = (x.getApellidos().equals("")) ? "" : "apellidos = '" + x.getApellidos() + "'";
            String query5 = (x.getEmail().equals("")) ? "" : "email = '" + x.getEmail() + "'";
            String query6 = (x.getTelefono() == -1) ? "" : "telefono = " + x.getTelefono();
            String query7 = (x.getTipovia().equals("")) ? "" : "tipovia = '" + x.getTipovia() + "'";
            String query8 = (x.getNomvia().equals("")) ? "" : "nomvia = '" + x.getNomvia() + "'";
            String query9 = (x.getNumcalle().equals("")) ? "" : "numcalle = '" + x.getNumcalle() + "'";
            String query10 = (x.getPiso().equals("")) ? "" : "piso = " + x.getPiso();
            String query11 = (x.getPuerta().equals("")) ? "" : "puerta = '" + x.getPuerta() + "'";
            String query12 = (x.getEscalera().equals("")) ? "" : "escalera = '" + x.getEscalera() + "'";
            String query13 = (x.getLocalidad().equals("")) ? "" : "localidad = '" + x.getLocalidad() + "'";
            String query14 = (x.getProvincia().equals("")) ? "" : "provincia = '" + x.getProvincia() + "'";
            String query15 = (x.getCodpostal() == -1) ? "" : "codpostal = " + x.getCodpostal();
            String query16 = (x.getTipopago().equals("")) ? "" : "tipopago = '" + x.getTipopago() + "'";
            String query17 = (x.getTipotarjeta().equals("")) ? "" : "tipotarjeta = '" + x.getTipotarjeta() + "'";
            String query18 = (x.getNumtarjeta().equals("")) ? "" : "numtarjet = '" + x.getNumtarjeta() + "'";
            String query19 = (x.getCaducidad().equals("")) ? "" : "caducidad = '" + x.getCaducidad() + "'";
            String query20 = (x.getTitular().equals("")) ? "" : "titular = '" + x.getTitular() + "'";
            String query21 = (x.getCodseg() == -1) ? "" : "codseg = " + x.getCodseg();
            String query22 = (x.getNumcuenta().equals("")) ? "" : "numcuenta = '" + x.getNumcuenta() + "'";
            String query23 = (x.getEstado() == -1) ? "" : "estado =    " + x.getEstado();

            query1 = ((!query1.equals("") && !query2.equals("")) ? query1 + " AND " + query2 : query1 + query2);
            query1 = ((!query1.equals("") && !query3.equals("")) ? query1 + " AND " + query3 : query1 + query3);
            query1 = ((!query1.equals("") && !query4.equals("")) ? query1 + " AND " + query4 : query1 + query4);
            query1 = ((!query1.equals("") && !query5.equals("")) ? query1 + " AND " + query5 : query1 + query5);
            query1 = ((!query1.equals("") && !query6.equals("")) ? query1 + " AND " + query6 : query1 + query6);
            query1 = ((!query1.equals("") && !query7.equals("")) ? query1 + " AND " + query7 : query1 + query7);
            query1 = ((!query1.equals("") && !query8.equals("")) ? query1 + " AND " + query8 : query1 + query8);
            query1 = ((!query1.equals("") && !query9.equals("")) ? query1 + " AND " + query9 : query1 + query9);
            query1 = ((!query1.equals("") && !query10.equals("")) ? query1 + " AND " + query10 : query1 + query10);
            query1 = ((!query1.equals("") && !query11.equals("")) ? query1 + " AND " + query11 : query1 + query11);
            query1 = ((!query1.equals("") && !query12.equals("")) ? query1 + " AND " + query12 : query1 + query12);
            query1 = ((!query1.equals("") && !query13.equals("")) ? query1 + " AND " + query13 : query1 + query13);
            query1 = ((!query1.equals("") && !query14.equals("")) ? query1 + " AND " + query14 : query1 + query14);
            query1 = ((!query1.equals("") && !query15.equals("")) ? query1 + " AND " + query15 : query1 + query15);
            query1 = ((!query1.equals("") && !query16.equals("")) ? query1 + " AND " + query16 : query1 + query16);
            query1 = ((!query1.equals("") && !query17.equals("")) ? query1 + " AND " + query17 : query1 + query17);
            query1 = ((!query1.equals("") && !query18.equals("")) ? query1 + " AND " + query18 : query1 + query18);
            query1 = ((!query1.equals("") && !query19.equals("")) ? query1 + " AND " + query19 : query1 + query19);
            query1 = ((!query1.equals("") && !query20.equals("")) ? query1 + " AND " + query20 : query1 + query20);
            query1 = ((!query1.equals("") && !query21.equals("")) ? query1 + " AND " + query21 : query1 + query21);
            query1 = ((!query1.equals("") && !query22.equals("")) ? query1 + " AND " + query22 : query1 + query22);
            query1 = ((!query1.equals("") && !query23.equals("")) ? query1 + " AND " + query23 : query1 + query23);

            psSearch = con.prepareStatement("SELECT COUNT(nif) FROM CLIENTE WHERE " + query1);
            ResultSet rs = psSearch.executeQuery();
            rs.next();
            int tamanio = rs.getInt(1);

            // Crea una lista con los objetos encontrados.
            ArrayList listaClientes = new ArrayList(tamanio);

            psSearch = con.prepareStatement("SELECT * FROM CLIENTE WHERE " + query1);
            rs = psSearch.executeQuery();
            while (rs.next()) {
                Cliente_VO a = new Cliente_VO(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getInt(6),
                        rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
                        rs.getString(11), rs.getString(12), rs.getString(13),
                        rs.getString(14), rs.getInt(15),
                        rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19),
                        rs.getString(20), rs.getInt(21), rs.getString(22), rs.getInt(23));
                listaClientes.add(a);
            }
            return listaClientes;

        } catch (SQLException ex) {

            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    // LINEAS //----------------------------------------------------------------
    public int insertaLinea(Linea_VO x) {
        PreparedStatement psInsert = null;

        try {
            psInsert = con.prepareStatement("INSERT into LINEA values (?, ?, ?)");
            psInsert.setInt(1, x.getNumtlf());
            psInsert.setString(2, x.getNif());
            psInsert.setInt(3, x.getEstado());

            psInsert.executeUpdate();

            return (MSJ.OK);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return (MSJ.INSERCION_INCORRECTA);
        }
    }

    /**
     * Elimina una Linea telefonica
     * @param Linea x
     * @return int ERROR/OK
     */
    public int eliminaLinea(Linea_VO x) {

        PreparedStatement psDelete = null;

        try {
            String query = "DELETE FROM LINEA WHERE numtlf = " + x.getNumtlf();

            psDelete = con.prepareStatement(query);
            psDelete.executeUpdate();
            return (MSJ.OK);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return (MSJ.ELIMINACION_LINEA_ERRONEA);
        }
    }

    /**
     * Actualiza una Linea con los datos dados
     * @param Linea x
     * @return int ERROR/OK
     */
    public int actualizaLinea(Linea_VO x) {

        PreparedStatement psUpdate = null;

        try {
            String query1 = "";
            String query2 = (x.getNif().equals("")) ? "" : "nif = '" + x.getNif() + "'";
            String query3 = (x.getEstado() == -1) ? "" : "estado =    " + x.getEstado();

            query1 = ((!query1.equals("") && !query2.equals("")) ? query1 + ", " + query2 : query1 + query2);
            query1 = ((!query1.equals("") && !query3.equals("")) ? query1 + ", " + query3 : query1 + query3);

            query1 = "UPDATE LINEA SET " + query1 + " WHERE numtlf = " + x.getNumtlf();

            psUpdate = con.prepareStatement(query1);
            psUpdate.executeUpdate();

            return (MSJ.OK);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return MSJ.ACTUALIZACION_LINEA_ERRONEA;
        }

    }

    /**
     * Devuelve una lista con todas las lineas telefonicas almacenadas
     * @return ArrayList
     */
    public ArrayList listaLineas() {

        PreparedStatement psSearch = null;

        try {
            psSearch = con.prepareStatement("SELECT COUNT(numtlf) FROM LINEA");
            ResultSet rs = psSearch.executeQuery();
            rs.next();
            int tamanio = rs.getInt(1);

            // Crea una lista con los objetos encontrados.
            ArrayList listaLineas = new ArrayList(tamanio);

            psSearch = con.prepareStatement("SELECT * FROM LINEA");
            rs = psSearch.executeQuery();
            while (rs.next()) {
                Linea_VO a = new Linea_VO(rs.getInt(1), rs.getString(2), rs.getInt(3));
                listaLineas.add(a);
            }
            return listaLineas;
        } catch (SQLException ex) {

            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Devuelve una lista con todas las lineas telefonicas activas almacenadas
     * @return ArrayList
     */
    public ArrayList listaLineasActivas() {

        PreparedStatement psSearch = null;

        try {
            psSearch = con.prepareStatement("SELECT COUNT(numtlf) FROM LINEA");
            ResultSet rs = psSearch.executeQuery();
            rs.next();
            int tamanio = rs.getInt(1);

            // Crea una lista con los objetos encontrados.
            ArrayList listaLineas = new ArrayList(tamanio);

            psSearch = con.prepareStatement("SELECT * FROM LINEA");
            rs = psSearch.executeQuery();
            while (rs.next()) {
                if (rs.getInt(3) == 1) {
                    Linea_VO a = new Linea_VO(rs.getInt(1), rs.getString(2), rs.getInt(3));
                    listaLineas.add(a);
                }
            }
            return listaLineas;
        } catch (SQLException ex) {

            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Busca todas las lineas que tengan algun parametro en comun con la linea dada
     * @param Linea x
     * @return ArrayList
     */
    public ArrayList buscaLinea(Linea_VO x) {
        PreparedStatement psSearch = null;
        try {
            String query1 = (x.getNumtlf() == -1) ? "" : "numtlf = " + x.getNumtlf();
            String query2 = (x.getEstado() == -1) ? "" : "estado = " + x.getEstado();
            String query3 = (x.getNif().equals("")) ? "" : "nif = '" + x.getNif() + "'";

            query1 = ((!query1.equals("") && !query2.equals("")) ? query1 + " AND " + query2 : query1 + query2);
            query1 = ((!query1.equals("") && !query3.equals("")) ? query1 + " AND " + query3 : query1 + query3);

            psSearch = con.prepareStatement("SELECT COUNT(numtlf) FROM LINEA WHERE " + query1);
            ResultSet rs = psSearch.executeQuery();
            rs.next();
            int tamanio = rs.getInt(1);
            // Crea una lista con los objetos encontrados.
            ArrayList listaLinea = new ArrayList(tamanio);

            psSearch = con.prepareStatement("SELECT * FROM LINEA WHERE " + query1);
            rs = psSearch.executeQuery();
            while (rs.next()) {
                Linea_VO a = new Linea_VO(rs.getInt(1), rs.getString(2), rs.getInt(3));
                listaLinea.add(a);
            }
            return listaLinea;
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    // FACTURAS //--------------------------------------------------------------
    /**
     * Inserta una factura en la BD
     * @param Factura x
     * @return ERROR/OK
     */
    public int insertaFactura(Factura_VO x) {

        PreparedStatement psInsert = null;

        try {
            psInsert = con.prepareStatement("INSERT into FACTURA (importe, fechainicio, "
                    + "fechafin, estapagada, numtlf) values (?, ?, ?, ?, ?)");
            psInsert.setDouble(1, x.getImporte());
            psInsert.setString(2, x.getFechainicio());
            psInsert.setString(3, x.getFechafin());
            psInsert.setInt(4, x.getEstapagada());
            psInsert.setInt(5, x.getNumtlf());
            psInsert.executeUpdate();
            return (MSJ.OK);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return (MSJ.INSERCION_INCORRECTA);
        }
    }

    /**
     * Elimina la factura de la BD
     * @param Factura x
     * @return ERROR/OK
     */
    public int eliminaFactura(Factura_VO x) {

        PreparedStatement psDelete = null;

        try {
            String query = "DELETE FROM FACTURA WHERE numfactura = " + x.getNumfactura();

            psDelete = con.prepareStatement(query);
            psDelete.executeUpdate();
            return MSJ.OK;
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return MSJ.ELIMINACION_FACTURA_INCORRECTA;
        }
    }

    /**
     * Actualiza la factura con los datos de x
     * @param Factura x
     * @return ERROR/OK
     */
    public int actualizaFactura(Factura_VO x) {
        PreparedStatement psUpdate = null;

        try {
            String query1 = "";
            String query2 = (x.getImporte() == -1.0) ? "" : "importe = " + x.getImporte();
            String query3 = (x.getFechainicio().equals("")) ? "" : "fechainicio = '" + x.getFechainicio() + "'";
            String query4 = (x.getFechafin().equals("")) ? "" : "fechafin = '" + x.getFechafin() + "'";
            String query5 = (x.getEstapagada() == -1) ? "" : "estapagada = " + x.getEstapagada();
            String query6 = (x.getNumtlf() == -1) ? "" : "numtlf = " + x.getNumtlf();


            query1 = ((!query1.equals("") && !query2.equals("")) ? query1 + ", " + query2 : query1 + query2);
            query1 = ((!query1.equals("") && !query3.equals("")) ? query1 + ", " + query3 : query1 + query3);
            query1 = ((!query1.equals("") && !query4.equals("")) ? query1 + ", " + query4 : query1 + query4);
            query1 = ((!query1.equals("") && !query5.equals("")) ? query1 + ", " + query5 : query1 + query5);
            query1 = ((!query1.equals("") && !query6.equals("")) ? query1 + ", " + query6 : query1 + query6);

            
            query1 = "UPDATE FACTURA SET " + query1 + " WHERE numfactura = " + x.getNumfactura();

            psUpdate = con.prepareStatement(query1);
            psUpdate.executeUpdate();
            return (MSJ.OK);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return MSJ.ACTUALIZACION_FACTURA_ERRONEA;
        }
    }

    /**
     * devuelve una lista con todas las facturas de la BD
     * @return ArrayList
     */
    public ArrayList listaFacturas() {

        PreparedStatement psSearch = null;

        try {
            psSearch = con.prepareStatement("SELECT COUNT(numfactura) FROM FACTURA");
            ResultSet rs = psSearch.executeQuery();
            rs.next();
            int tamanio = rs.getInt(1);

            // Crea una lista con los objetos encontrados.
            ArrayList listaFactura = new ArrayList(tamanio);

            psSearch = con.prepareStatement("SELECT * FROM FACTURA");
            rs = psSearch.executeQuery();
            while (rs.next()) {
                Factura_VO a = new Factura_VO(rs.getInt(1), rs.getFloat(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getInt(6));
                listaFactura.add(a);
            }
            return listaFactura;

        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Busca una factura que tenga parametros en comun con x
     * @param Factura x
     * @return ERROR/OK
     */
    public ArrayList buscaFactura(Factura_VO x) {
        PreparedStatement psSearch = null;
        try {
            String query1 = (x.getNumfactura() == -1) ? "" : "numfactura = " + x.getNumfactura();
            String query2 = (x.getImporte() == -1.0) ? "" : "importe = " + x.getImporte();
            String query3 = (x.getFechainicio().equals("")) ? "" : "fechainicio = '" + x.getFechainicio() + "'";
            String query4 = (x.getFechafin().equals("")) ? "" : "fechafin = '" + x.getFechafin() + "'";
            String query5 = (x.getEstapagada() == -1) ? "" : "estapagada = " + x.getEstapagada();
            String query6 = (x.getNumtlf() == -1) ? "" : "numtlf = " + x.getNumtlf();

            query1 = ((!query1.equals("") && !query2.equals("")) ? query1 + " AND " + query2 : query1 + query2);
            query1 = ((!query1.equals("") && !query3.equals("")) ? query1 + " AND " + query3 : query1 + query3);
            query1 = ((!query1.equals("") && !query4.equals("")) ? query1 + " AND " + query4 : query1 + query4);
            query1 = ((!query1.equals("") && !query5.equals("")) ? query1 + " AND " + query5 : query1 + query5);
            query1 = ((!query1.equals("") && !query6.equals("")) ? query1 + " AND " + query6 : query1 + query6);

            psSearch = con.prepareStatement("SELECT COUNT (numfactura) FROM FACTURA WHERE " + query1);
            ResultSet rs = psSearch.executeQuery();
            rs.next();
            int tamanio = rs.getInt(1);

            // Crea una lista con los objetos encontrados.
            ArrayList listaFactura = new ArrayList(tamanio);

            psSearch = con.prepareStatement("SELECT * FROM FACTURA WHERE " + query1);
            rs = psSearch.executeQuery();

            while (rs.next()) {
                Factura_VO a = new Factura_VO(rs.getInt(1), rs.getFloat(2), rs.getString(3),
                        rs.getString(4), rs.getInt(5), rs.getInt(6));
                listaFactura.add(a);
            }

            return listaFactura;

        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    // SERVICIOS //-------------------------------------------------------------
    /**
     * Inserta el servicio x en la BD
     * @param Servicio x
     * @return ERROR/OK
     */
    public int insertaServicio(Servicio_VO x) {

        PreparedStatement psInsert = null;

        try {
            psInsert = con.prepareStatement("INSERT into SERVICIO(codserv,numdestino,fecha,duracion,datos,numtlf)"
                    + " values (?,?,?,?,?,?)");
            psInsert.setInt(1, x.getCodserv());
            psInsert.setInt(2, x.getNumdestino());
            psInsert.setString(3, x.getFecha());
            psInsert.setInt(4, x.getDuracion());
            psInsert.setInt(5, x.getDatos());
            psInsert.setInt(6, x.getNumtlf());

            psInsert.executeUpdate();
            return MSJ.OK;
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return MSJ.INSERCION_INCORRECTA;
        }
    }

    /**
     * Actualiza el servicio x en la BD
     * @param Servicio x
     * @return ERROR/OK
     */
    public int actualizaServicio(Servicio_VO x) {
        PreparedStatement psUpdate = null;

        try {
            String query1 = "";
            String query2 = (x.getCodserv() == -1) ? "" : "codserv = " + x.getCodserv();
            String query3 = (x.getNumdestino() == -1) ? "" : "numdestino = " + x.getNumdestino();
            String query4 = (x.getFecha().equals("")) ? "" : "fecha = '" + x.getFecha() + "'";
            String query5 = (x.getDuracion() == -1) ? "" : "duracion = " + x.getDuracion();
            String query6 = (x.getDatos() == -1) ? "" : "datos = " + x.getDatos();
            String query7 = (x.getNumtlf() == -1) ? "" : "numtlf = " + x.getNumtlf();
            String query8 = (x.getIdFactura() == 0) ? "" : "idfactura = " + x.getIdFactura();

            query1 = ((!query1.equals("") && !query2.equals("")) ? query1 + ", " + query2 : query1 + query2);
            query1 = ((!query1.equals("") && !query3.equals("")) ? query1 + ", " + query3 : query1 + query3);
            query1 = ((!query1.equals("") && !query4.equals("")) ? query1 + ", " + query4 : query1 + query4);
            query1 = ((!query1.equals("") && !query5.equals("")) ? query1 + ", " + query5 : query1 + query5);
            query1 = ((!query1.equals("") && !query6.equals("")) ? query1 + ", " + query6 : query1 + query6);
            query1 = ((!query1.equals("") && !query7.equals("")) ? query1 + ", " + query7 : query1 + query7);
            query1 = ((!query1.equals("") && !query8.equals("")) ? query1 + ", " + query8 : query1 + query8);


            query1 = "UPDATE SERVICIO SET " + query1 + " WHERE idserv = " + x.getIdserv();


            psUpdate = con.prepareStatement(query1);
            psUpdate.executeUpdate();
            return (MSJ.OK);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return MSJ.ACTUALIZACION_SERVICIO_INCORRECTA;
        }
    }

    /**
     * Lista todos los servicios almacenados en la BD
     * @return ArrayList
     */
    public ArrayList listaServicios() {
        PreparedStatement psSearch = null;

        try {
            psSearch = con.prepareStatement("SELECT COUNT(idserv) FROM SERVICIO");
            ResultSet rs = psSearch.executeQuery();
            rs.next();
            int tamanio = rs.getInt(1);

            // Crea una lista con los objetos encontrados.
            ArrayList listaServicio = new ArrayList(tamanio);

            psSearch = con.prepareStatement("SELECT * FROM SERVICIO");
            rs = psSearch.executeQuery();
            while (rs.next()) {

                Servicio_VO a = new Servicio_VO(rs.getInt(1), rs.getInt(2), rs.getInt(3),
                        rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8));
                listaServicio.add(a);
            }
            return listaServicio;

        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList buscaServicio(Servicio_VO x) {
        PreparedStatement psSearch = null;
        try {

            String query1 = (x.getIdserv() == -1) ? "" : "idserv = " + x.getIdserv();
            String query2 = (x.getCodserv() == -1) ? "" : "codserv = " + x.getCodserv();
            String query3 = (x.getNumdestino() == -1) ? "" : "numdestino = " + x.getNumdestino();
            String query4 = (x.getFecha().equals("")) ? "" : "fecha = '" + x.getFecha() + "'";
            String query5 = (x.getDuracion() == -1) ? "" : "duracion = " + x.getDuracion();
            String query6 = (x.getDatos() == -1) ? "" : "datos = " + x.getDatos();
            String query7 = (x.getNumtlf() == -1) ? "" : "numtlf = " + x.getNumtlf();
            String query8 = (x.getIdFactura() == -1) ? "" : "idfactura = " + x.getIdFactura();

            query1 = ((!query1.equals("") && !query2.equals("")) ? query1 + " AND " + query2 : query1 + query2);
            query1 = ((!query1.equals("") && !query3.equals("")) ? query1 + " AND " + query3 : query1 + query3);
            query1 = ((!query1.equals("") && !query4.equals("")) ? query1 + " AND " + query4 : query1 + query4);
            query1 = ((!query1.equals("") && !query5.equals("")) ? query1 + " AND " + query5 : query1 + query5);
            query1 = ((!query1.equals("") && !query6.equals("")) ? query1 + " AND " + query6 : query1 + query6);
            query1 = ((!query1.equals("") && !query7.equals("")) ? query1 + " AND " + query7 : query1 + query7);
            query1 = ((!query1.equals("") && !query8.equals("")) ? query1 + " AND " + query8 : query1 + query8);


            psSearch = con.prepareStatement("SELECT COUNT(codserv) FROM SERVICIO WHERE " + query1);
            ResultSet rs = psSearch.executeQuery();
            rs.next();
            int tamanio = rs.getInt(1);
            // Crea una lista con los objetos encontrados.
            ArrayList listaServicios = new ArrayList(tamanio);

            psSearch = con.prepareStatement("SELECT * FROM SERVICIO WHERE " + query1);
            rs = psSearch.executeQuery();


            while (rs.next()) {
                Servicio_VO a = new Servicio_VO(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
                        rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8));

                listaServicios.add(a);
            }
            return listaServicios;

        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int eliminaServicio(Servicio_VO x) {

        PreparedStatement psDelete = null;

        try {
            String query = "DELETE FROM SERVICIO WHERE Idserv = " + x.getIdserv();
            psDelete = con.prepareStatement(query);
            psDelete.executeUpdate();
            return (MSJ.OK);
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
            return MSJ.ELIMINACION_SERVICIO_INCORRECTO;
        }
    }
}
