/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TADS;

import BaseDatos.Cliente_VO;
import BaseDatos.BaseDatos;
import BaseDatos.Linea_VO;
import MSJ.MSJ;
import java.util.ArrayList;

public class Cliente {

    private static Cliente_VO c;
    private static BaseDatos bd;

    /**
     * Constructor de la clase
     * @param basedatos
     */
    public Cliente(BaseDatos basedatos) {

        c = null;
        bd = basedatos;
    }

    /**
     * Constructor de la clase
     * @param basedatos
     * @param cliente
     */
    public Cliente(BaseDatos basedatos, Cliente_VO cliente) {

        c = cliente;
        bd = basedatos;
    }

    /**
     * Logea a un cliente en el sistema
     * @param nick
     * @param contrasena
     * @return true (informacion del cliente correcta) o false (incorrecta)
     */
    public int loginCliente(String nick, String contrasena) {

        if (!existeCliente(nick)) {
            return (MSJ.CLIENTE_NO_EXISTE);
        }
        Cliente_VO cl = obtenerCliente(nick);
        if (!cl.estaActivo()) {
            return (MSJ.CLIENTE_NO_EXISTE);
        }
        if (!cl.getPass().equals(contrasena)) {
            return (MSJ.CONTRASENA_INCORRECTA);
        }
        // El cliente ha metido su nick y contraseña bien!
        // Carga todos los datos del cliente de la BD
        c = new Cliente_VO(cl);
        // Ahora ya podemos trabajar con el cliente.

        return (MSJ.OK);
    }

    /**
     * sale del sistema
     * @return OK
     */
    public int logoutCliente() {

        c = null;
        return (MSJ.OK);
    }

    /**
     * Registra un nuevo cliente en el sistema con los datos indicados por parámetros
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
     * @return int OK/ERROR
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
                if (info >= 0) { // Si se ha insertado bien cargamos el cliente en el sistema
                    c = new Cliente_VO(nif_cli, pasw_cli, name_cli, apell_cli, email_cli, tfn_cli,
                            tipovia_dir, via_dir, num_dir, piso_dir, puerta_dir, escalera_dir,
                            localidad_dir, prov_dir, cp_dir,
                            tipo_pago, tipo_tarj, num_tarj, fecha_cad, titular_pago, cod_seguridad,
                            num_cuenta, Cliente_VO.ACTIVO);
                } // sino no
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
        if (info >= 0) { // Si se ha insertado bien cargamos el cliente en el sistema
            c = new Cliente_VO(nif_cli, pasw_cli, name_cli, apell_cli, email_cli, tfn_cli,
                    tipovia_dir, via_dir, num_dir, piso_dir, puerta_dir, escalera_dir,
                    localidad_dir, prov_dir, cp_dir,
                    tipo_pago, tipo_tarj, num_tarj, fecha_cad, titular_pago, cod_seguridad,
                    num_cuenta, Cliente_VO.ACTIVO);
        } // sino no
        return ((info < 0) ? info : MSJ.OK); // Devuelve: o el error de la BD, o OK
    }

    /**
     * borra a el cliente logeado del sistema
     * @return int ERROR/OK
     */
    public int borrarCliente() {

        if (c == null) { // DESDE EL GUI NO SE PODDRIA ACCEDER A BORRARCLIENTE... pero nunca esta de mal la comprobacion
            return (MSJ.NO_HA_HECHO_LOGIN);
        }
        if (!existeCliente(c.getNif())) { // NO DEBERIA ESTAR CARGADO SI NO EXISTE, pero idem(arriba)
            return (MSJ.CLIENTE_NO_EXISTE);
        }

        Linea l = new Linea(bd);
        Linea_VO lvo;
        ArrayList listaLi = l.listarLineasActivasCliente(c.getNif());
        for (int i = 0; i < listaLi.size(); i++) {
            lvo = (Linea_VO) listaLi.get(i);
            l.borrarLinea(lvo);
        }
        Cliente_VO Cl = new Cliente_VO(c);
        Cl.setEstado(Cliente_VO.INACTIVO);
        int info = bd.actualizaCliente(Cl);
        if (info >= 0) { // Si da error actualizarlo en la bd, no cambiamos nada en memoria
            c = null; // Si no da error... es como hacer logout.
        }
        return ((info < 0) ? info : MSJ.OK); // Devuelve: o el error de la BD, o OK
    }

    /**
     * modifica el cliente logeado con los datos pasados por parámetros
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
     * @return ERROR/OK
     */
    public int modificarCliente(String pasw_cli, String name_cli,
            String apell_cli, String email_cli, int tfn_cli,
            String tipovia_dir, String via_dir, String num_dir, String piso_dir,
            String puerta_dir, String escalera_dir, String localidad_dir,
            String prov_dir, int cp_dir,
            String tipo_pago, String tipo_tarj, String num_tarj, String fecha_cad,
            String titular_pago, int cod_seguridad, String num_cuenta) {

        if (c == null) { // DESDE EL GUI NO SE PODDRIA ACCEDER A BORRARCLIENTE... PEEEEEERO, POR SI ACASO ^^
            return (MSJ.NO_HA_HECHO_LOGIN);
        }

        if (!existeCliente(c.getNif())) { // No debería darse, ya que si no tenemos un cliente cargado es que no ha hecho login
            return (MSJ.NIF_NO_EXISTE); // y si esta cargado, deberia existir en la bd tambien
        }
        Cliente_VO Cl = new Cliente_VO(c);
        Cl.modifCliente(pasw_cli, name_cli, apell_cli, email_cli, tfn_cli,
                tipovia_dir, via_dir, num_dir, piso_dir, puerta_dir, escalera_dir,
                localidad_dir, prov_dir, cp_dir, tipo_pago, tipo_tarj, num_tarj,
                fecha_cad, titular_pago, cod_seguridad, num_cuenta, Cliente_VO.ACTIVO);

        int info = bd.actualizaCliente(Cl);
        if (info >= 0) { // Si se ha actualizado bien, actualizamos el cliente cargado en memoria
            c = new Cliente_VO(Cl);
        }
        return ((info < 0) ? info : MSJ.OK); // Devuelve: o el error de la BD, o OK
    }

    /**
     * lista todas las lineas (activas e inactivas) del cliente loggeado
     * @return ArrayList
     */
    public ArrayList listaLineas() {

        if (c == null) {
            return (new ArrayList());
        }

        if (!existeCliente(c.getNif())) {
            return (new ArrayList());
        }
        Linea gl = new Linea(bd);
        return (gl.listarLineasCliente(c));
    }

    /**
     * lista todas las lineas activas del cliente loggeado
     * @return ArrayList
     */
    public ArrayList listaLineasActivas() {

        if (c == null) {
            return (new ArrayList());
        }

        if (!existeCliente(c.getNif())) {
            return (new ArrayList());
        }
        Linea gl = new Linea(bd);
        return (gl.listarLineasActivasCliente(c.getNif()));
    }

    /**
     * lista todas las facturas de la linea "num" del cliente loggeado
     * @param num
     * @return ArrayList
     */
    public ArrayList listaFacturas(int num) {

        if (c == null) {
            return (new ArrayList());
        }

        if (!existeCliente(c.getNif())) {
            return (new ArrayList());
        }
        ArrayList l = listaLineas();
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
        Factura gf = new Factura(bd);
        return (gf.listarFacturasLinea(num));
    }

    /**
     * contrata una nueva linea para el cliente loggeado
     * @return ERROR/numero de telefono nuevo
     */
    public int contratarLinea() {

        if (c == null) {
            return (MSJ.NO_HA_HECHO_LOGIN);
        }

        if (!existeCliente(c.getNif())) {
            return (MSJ.NIF_NO_EXISTE);
        }
        Linea gl = new Linea(bd);
        return (gl.nuevaLinea(c));
    }

    /**
     * da de baja la linea "num"
     * @param num
     * @return ERROR/OK
     */
    public int DarBajaLinea(int num) {

        // Comprueba que el cliente ha hecho login
        if (c == null) {
            return (MSJ.NO_HA_HECHO_LOGIN);
        }
        // Comprueba que el cliente existe
        if (!existeCliente(c.getNif())) {
            return (MSJ.NIF_NO_EXISTE);
        }
        // Comprueba que tiene la linea indicada, y que la linea está activa
        ArrayList lineas = listaLineasActivas();
        boolean esta = false;
        for (int i = 0; i < lineas.size(); i++) {
            if (((Linea_VO) lineas.get(i)).getNumtlf() == num) {
                esta = true;
                break;
            }
        }
        // Si no esta, devuelve un mensaje de error
        if (!esta) {
            return (MSJ.LINEA_NO_EXISTE);
        }
        // Si está, la borra y devuelve el resultado de borrarla
        Linea gl = new Linea(bd);
        return (gl.borrarLinea(num));
    }

    /**
     * devuelve el cliente loggeado
     * @return Cliente_VO
     */
    public Cliente_VO getCliente() {

        return (c);
    }

    /**
     * factura todas las lineas del cliente
     * @return ERROR/OK
     */
    public int facturar() {

        // Comprueba que el cliente ha hecho login
        if (c == null) {
            return (MSJ.NO_HA_HECHO_LOGIN);
        }
        // Comprueba que el cliente existe
        if (!existeCliente(c.getNif())) {
            return (MSJ.NIF_NO_EXISTE);
        }
        // Lista todas las lineas y las factura individualmente
        ArrayList listaLi = listaLineas();
        Linea l = new Linea(bd);
        for (int i = 0; i < listaLi.size(); i++) {
            l.facturar(((Linea_VO) listaLi.get(i)).getNumtlf());
        }
        return (MSJ.OK);
    }

    /**
     * Devuelve el importe total facturado del cliente
     * @return
     */
    public double importeTotalCliente() {

        if (c == null) { // DESDE EL GUI NO SE PODDRIA ACCEDER A BORRARCLIENTE... PEEEEEERO, POR SI ACASO ^^
            return (0);
        }
        if (!existeCliente(c.getNif())) { // No debería darse, ya que si no tenemos un cliente cargado es que no ha hecho login
            return (0); // y si esta cargado, deberia existir en la bd tambien
        }

        ArrayList li = listaLineas();
        Linea l = new Linea(bd);
        Linea_VO l_VO;
        double total = 0;
        for (int i = 0; i < li.size(); i++) {
            l_VO = (Linea_VO) li.get(i);
            total += l.importeTotalLinea(l_VO.getNumtlf());
        }
        return (total);
    }

    public BaseDatos GetBD() {
        return bd;
    }
    // OPERACIONES PRIVADAS DEL PAQUETE

    public Cliente_VO obtenerCliente(String nif) {

        if (!existeCliente(nif)) {
            return (null);
        } else {
            ArrayList arrayCl = bd.buscaCliente(new Cliente_VO(nif, "", "", "", "", -1, "", "", "", "", "", "", "", "", -1, "", "", "", "", "", -1, "", -1));
            return ((Cliente_VO) arrayCl.get(0));
        }
    }

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
