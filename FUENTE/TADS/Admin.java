/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TADS;

import BaseDatos.BaseDatos;
import BaseDatos.Cliente_VO;
import BaseDatos.Linea_VO;
import BaseDatos.Servicio_VO;
import MSJ.MSJ;
import java.util.ArrayList;

public class Admin {

    private static BaseDatos bd;

    /**
     * Constuctor de la clase
     * @param basedatos
     */
    public Admin(BaseDatos basedatos) {

        bd = basedatos;
    }

    /**
     * Valida el login del administrador
     * @param nick
     * @param contrasena
     * @return true (correcto) o false (incorrecto)
     */
    public boolean loginAdmin(String nick, String contrasena) {

        Cliente_VO cl = obtenerCliente(nick);
        if (nick.equals(cl.getNif()) && contrasena.equals(cl.getPass())) {
            return (true);
        } else {
            return (false);
        }
    }

    /**
     * Registra un cliente con los datos indicados por parametros
     * @param nif_cli
     * @param pasw_cli
     * @param name_cli
     * @param apell_cli
     * @param email_cli
     * @param tfn_cli
     * @param tipovia_dir
     * @param via_dir
     * @param num_dir
     * @param piso_dir
     * @param puerta_dir
     * @param escalera_dir
     * @param localidad_dir
     * @param prov_dir
     * @param cp_dir
     * @param tipo_pago
     * @param tipo_tarj
     * @param num_tarj
     * @param fecha_cad
     * @param titular_pago
     * @param cod_seguridad
     * @param num_cuenta
     * @return int resultado
     */
    public int registrarCliente(String nif_cli, String pasw_cli, String name_cli,
            String apell_cli, String email_cli, int tfn_cli,
            String tipovia_dir, String via_dir, String num_dir, String piso_dir,
            String puerta_dir, String escalera_dir, String localidad_dir,
            String prov_dir, int cp_dir,
            String tipo_pago, String tipo_tarj, String num_tarj, String fecha_cad,
            String titular_pago, int cod_seguridad, String num_cuenta) {

        // Busca un cliente en la BD con NIF = "nif_cli"
        if (existeCliente(nif_cli)) { // Si existen
            Cliente_VO cl = obtenerCliente(nif_cli);
            if (cl.estaActivo()) { // Y si encima esta activo
                return (MSJ.NIF_YA_REGISTRADO);
            } else { // Si existe pero esta inactivo (se ha borrado del sistema alguna vez)
                cl.modifCliente(pasw_cli, name_cli, apell_cli, email_cli, tfn_cli,
                        tipovia_dir, via_dir, num_dir, piso_dir, puerta_dir,
                        escalera_dir, localidad_dir, prov_dir, cp_dir,
                        tipo_pago, tipo_tarj, num_tarj, fecha_cad,
                        titular_pago, cod_seguridad, num_cuenta, Cliente_VO.ACTIVO);
                int info = bd.actualizaCliente(cl); // Insertado en la BD
                return ((info < 0) ? info : MSJ.OK); // devolvemos OK/ERROR DE LA BD
            }
        }
        // Si no existe el cliente
        Cliente_VO cl = new Cliente_VO(nif_cli, pasw_cli, name_cli, apell_cli, email_cli, tfn_cli,
                tipovia_dir, via_dir, num_dir, piso_dir, puerta_dir, escalera_dir,
                localidad_dir, prov_dir, cp_dir,
                tipo_pago, tipo_tarj, num_tarj, fecha_cad,
                titular_pago, cod_seguridad, num_cuenta, Cliente_VO.ACTIVO);
        int info = bd.insertaCliente(cl); // Guardado en la BD
        return ((info < 0) ? info : MSJ.OK); // Devuelve: o el error de la BD, o OK
    }

    /**
     * Borra el cliente "nif"
     * @param nif
     * @return int ERROR/OK
     */
    public int borrarCliente(String nif) {

        Cliente_VO c = obtenerCliente(nif);

        if (c == null) { // NO DEBERIA ESTAR CARGADO SI NO EXISTE, pero idem(arriba)
            return (MSJ.CLIENTE_NO_EXISTE);
        }

        c.setEstado(Cliente_VO.INACTIVO);
        int info = bd.actualizaCliente(c);
        return ((info < 0) ? info : MSJ.OK); // Devuelve: o el error de la BD, o OK
    }

    /**
     * Modifica el cliente con los datos pasados por parámetros
     * @param nif_cli
     * @param pasw_cli
     * @param name_cli
     * @param apell_cli
     * @param email_cli
     * @param tfn_cli
     * @param tipovia_dir
     * @param via_dir
     * @param num_dir
     * @param piso_dir
     * @param puerta_dir
     * @param escalera_dir
     * @param localidad_dir
     * @param prov_dir
     * @param cp_dir
     * @param tipo_pago
     * @param tipo_tarj
     * @param num_tarj
     * @param fecha_cad
     * @param titular_pago
     * @param cod_seguridad
     * @param num_cuenta
     * @return int ERROR/OK
     */
    public int modificarCliente(String nif_cli, String pasw_cli, String name_cli,
            String apell_cli, String email_cli, int tfn_cli,
            String tipovia_dir, String via_dir, String num_dir, String piso_dir,
            String puerta_dir, String escalera_dir, String localidad_dir,
            String prov_dir, int cp_dir,
            String tipo_pago, String tipo_tarj, String num_tarj, String fecha_cad,
            String titular_pago, int cod_seguridad, String num_cuenta) {

        Cliente_VO c = obtenerCliente(nif_cli);
        if (c == null) {
            return (MSJ.CLIENTE_NO_EXISTE);
        }

        c.modifCliente(pasw_cli, name_cli, apell_cli, email_cli, tfn_cli,
                tipovia_dir, via_dir, num_dir, piso_dir, puerta_dir, escalera_dir,
                localidad_dir, prov_dir, cp_dir, tipo_pago, tipo_tarj, num_tarj,
                fecha_cad, titular_pago, cod_seguridad, num_cuenta, Cliente_VO.ACTIVO);
        int info = bd.actualizaCliente(c);
        return ((info < 0) ? info : MSJ.OK); // Devuelve: o el error de la BD, o OK
    }

    /**
     * Lista todas las lineas (activas e inactivas) del cliente nif
     * @param nif
     * @return int ERROR/OK
     */
    public ArrayList listaLineasCliente(String nif) {

        Cliente_VO c = obtenerCliente(nif);
        if (c == null) {
            return (new ArrayList());
        }
        Linea l = new Linea(bd);
        return (l.listarLineasCliente(c));
    }

    public ArrayList listaLineasActivasCliente(String nif) {

        Cliente_VO c = obtenerCliente(nif);
        if (c == null) {
            return (new ArrayList());
        }
        Linea l = new Linea(bd);
        return (l.listarLineasActivasCliente(c.getNif()));
    }

    /**
     * lista todas las facturas de la linea "num" del cliente "nif"
     * @param nif
     * @param num
     * @return int ERROR/OK
     */
    public ArrayList listaFacturasLineaCliente(String nif, int num) {

        Cliente_VO c = obtenerCliente(nif);
        if (c == null) {
            return (new ArrayList());
        }
        ArrayList l = listaLineasCliente(nif);
        Linea_VO li;
        boolean exito = false;
        for (int i = 0; i < l.size(); i++) {
            li = (Linea_VO) l.get(i);
            if (li.getNumtlf() == num) {
                exito = true;
                break;
            }
        }
        if (exito == false) {
            return (new ArrayList());
        }
        Factura f = new Factura(bd);
        return (f.listarFacturasLinea(num));
    }

    /**
     * contrata una nueva linea para el cliente "nif"
     * @param nif
     * @return int ERROR/numero de telefono
     */
    public int contratarLineaCliente(String nif) {

        Cliente_VO c = obtenerCliente(nif);
        if (c == null) {
            return (MSJ.CLIENTE_NO_EXISTE);
        }
        if (!c.estaActivo()) {
            return (MSJ.CLIENTE_NO_EXISTE);
        }
        Linea gl = new Linea(bd);
        return (gl.nuevaLinea(c));
    }

    /**
     * da de baja la linea "num" del cliente "nif"
     * @param nif
     * @param num
     * @return int ERROR/OK
     */
    public int DarBajaLineaCliente(String nif, int num) {

        Cliente_VO c = obtenerCliente(nif);
        if (c == null) {
            return (MSJ.CLIENTE_NO_EXISTE);
        }
        Linea gl = new Linea(bd);
        return (gl.borrarLinea(num));
    }

    /**
     * factura todos los clientes
     * @return ERROR/OK
     */
    public int facturar() {

        ArrayList lc = bd.listaClientes();
        Cliente_VO c_VO;
        for (int i = 0; i < lc.size(); i++) { // Genera las facturas de todos los clientes
            c_VO = (Cliente_VO) lc.get(i);
            Cliente c = new Cliente(bd, c_VO);
            c.facturar();
        }
        return (MSJ.OK);
    }

    /**
     * devuelve una lista con los 10 mejores clientes
     * @return ArrayList
     */
    public ArrayList top10() {

        ArrayList lc = bd.listaClientes();
        if (lc.size() <= 10) {
            return (lc);
        }

        ArrayList top10 = new ArrayList();
        double min = 0;
        Cliente_VO c_VO;
        Cliente_VO c_VO2;
        Cliente c;

        for (int i = 0; i < 10; i++) {
            top10.add(lc.get(i));
            c_VO = (Cliente_VO) lc.get(i);
            c = new Cliente(bd, c_VO);
            if (c.importeTotalCliente() < min) {
                min = c.importeTotalCliente();
            }
        }

        for (int i = 10; i < lc.size(); i++) { // Genera las facturas de todos los clientes
            c_VO = (Cliente_VO) lc.get(i);
            c = new Cliente(bd, c_VO);
            if (c.importeTotalCliente() > min) {
                for (int j = 0; j < top10.size(); j++) {
                    c_VO2 = (Cliente_VO) top10.get(j);
                    c = new Cliente(bd, c_VO);
                    if (min == c.importeTotalCliente()) {
                        top10.remove(j);
                    }
                }
                top10.add(c_VO);
            }
        }
        return (top10);
    }

    /**
     * devuelve el total del importe facturado entre las fechas "ppio" y "fin"
     * @param ppio
     * @param fin
     * @return double
     */
    public double importeEntreDosFechas(String ppio, String fin) {

        ArrayList ls = bd.listaServicios();
        Servicio_VO s;
        double total = 0;
        for (int i = 0; i < ls.size(); i++) {
            s = (Servicio_VO) ls.get(i);
            if (Factura.esMayor(s.getFecha(), ppio) && Factura.esMenor(s.getFecha(), fin)) {
                total += s.coste();
            }
        }
        return (total);
    }

    public ArrayList listarTodosLosClientes() {

        return (bd.listaClientes());
    }

    public ArrayList listarClientesActivos() {

        Cliente_VO c = new Cliente_VO("", "", "", "", "", -1, "", "", "", "", "", "", "", "", -1, "", "", "", "", "", -1, "", Cliente_VO.ACTIVO);

        return (bd.buscaCliente(c));
    }

    // OPERACIONES PRIVADAS DEL PAQUETE
    /**
     * Devuelve el cliente con nif "nif"
     * @param nif
     * @return Clinte_VO
     */
    public Cliente_VO obtenerCliente(String nif) {

        if (!existeCliente(nif)) {
            return (null);
        } else {
            ArrayList arrayCl = bd.buscaCliente(new Cliente_VO(nif, "", "", "", "", -1, "", "", "", "", "", "", "", "", -1, "", "", "", "", "", -1, "", -1));
            return ((Cliente_VO) arrayCl.get(0));
        }
    }

    /**
     * indica si existe el cliente "nif"
     * @param nif
     * @return true (existe) o false (no existe)
     */
    private boolean existeCliente(String nif) {

        Cliente_VO cl = new Cliente_VO(nif, "", "", "", "", -1, "", "", "", "", "", "", "", "", -1, "", "", "", "", "", -1, "", -1);
        ArrayList arrayCl = bd.buscaCliente(cl);
        if (!arrayCl.isEmpty()) {
            return (true);
        } else {
            return (false);
        }
    }
}
