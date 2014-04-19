package BaseDatos;

import MSJ.MSJ;

public class Cliente_VO {

    // Relativos al cliente
    private String nif;
    private String pass;
    private String nombre;
    private String apellidos;
    private String email;
    private int telefono;
    // Relativos a la direccion
    private String tipovia;
    private String nomvia;
    private String numcalle;
    private String piso;
    private String puerta;
    private String escalera;
    private String localidad;
    private String provincia;
    private int codpostal;
    // Relativos a la tarjeta / cuenta bancaria
    private String tipopago;
    private String tipotarjeta;
    private String numtarjeta;
    private String caducidad;
    private String titular;
    private int codseg;
    private String numcuenta;
    private int estado;
    public static final int ACTIVO = 1;
    public static final int INACTIVO = 0;

    /**
     * Constructor de la clase
     * @param String nif_cli
     * @param String pasw_cli
     * @param String name_cli
     * @param String apell_cli
     * @param String email_cli
     * @param int tfn_cli
     * @param String tipovia_dir
     * @param String via_dir
     * @param String num_dir
     * @param int piso_dir
     * @param String puerta_dir
     * @param String escalera_dir
     * @param String localidad_dir
     * @param String prov_dir
     * @param int cp_dir
     * @param String tipo_pago
     * @param String tipo_tarj
     * @param String num_tarj
     * @param String fecha_cad
     * @param String titular_pago
     * @param int cod_seguridad
     * @param String num_cuenta
     * @param int estado
     */
    public Cliente_VO(String nif_cli, String pasw_cli, String name_cli,
            String apell_cli, String email_cli, int tfn_cli,
            String tipovia_dir, String via_dir, String num_dir, String piso_dir,
            String puerta_dir, String escalera_dir, String localidad_dir,
            String prov_dir, int cp_dir,
            String tipo_pago, String tipo_tarj, String num_tarj, String fecha_cad,
            String titular_pago, int cod_seguridad, String num_cuenta, int estado_cli) {

        // Relativos al cliente ------------------------------------------------
        nif = nif_cli;
        pass = pasw_cli;
        nombre = name_cli;
        apellidos = apell_cli;
        email = email_cli;
        telefono = tfn_cli;
        // Relativos a la dirección --------------------------------------------
        tipovia = tipovia_dir;
        nomvia = via_dir;
        numcalle = num_dir;
        piso = piso_dir;
        puerta = puerta_dir;
        escalera = escalera_dir;
        localidad = localidad_dir;
        provincia = prov_dir;
        codpostal = cp_dir;
        // Relativos al pago ---------------------------------------------------
        tipopago = tipo_pago;
        tipotarjeta = tipo_tarj;
        numtarjeta = num_tarj;
        caducidad = fecha_cad;
        titular = titular_pago;
        codseg = cod_seguridad;
        numcuenta = num_cuenta;
        estado = estado_cli;
        //----------------------------------------------------------------------
    }

    /**
     * Constructor de la clase
     * @param Cliente c
     */
    public Cliente_VO(Cliente_VO c) {

        // Relativos al cliente ------------------------------------------------
        nif = c.nif;
        pass = c.pass;
        nombre = c.nombre;
        apellidos = c.apellidos;
        email = c.email;
        telefono = c.telefono;
        // Relativos a la dirección --------------------------------------------
        tipovia = c.tipovia;
        nomvia = c.nomvia;
        numcalle = c.numcalle;
        piso = c.piso;
        puerta = c.puerta;
        escalera = c.escalera;
        localidad = c.localidad;
        provincia = c.provincia;
        codpostal = c.codpostal;
        // Relativos al pago ---------------------------------------------------
        tipopago = c.tipopago;
        tipotarjeta = c.tipotarjeta;
        numtarjeta = c.numtarjeta;
        caducidad = c.caducidad;
        titular = c.titular;
        codseg = c.codseg;
        numcuenta = c.numcuenta;
        estado = c.estado;
        //----------------------------------------------------------------------
    }

    /**
     * Constructor de la clase
     */
    public Cliente_VO() {

        // Relativos al cliente ------------------------------------------------
        nif = "";
        pass = "";
        nombre = "";
        apellidos = "";
        email = "";
        telefono = -1;
        // Relativos a la dirección -------------------------------------------
        tipovia = "";
        nomvia = "";
        numcalle = "";
        piso = "";
        puerta = "";
        escalera = "";
        localidad = "";
        provincia = "";
        codpostal = -1;
        // Relativos al pago ---------------------------------------------------
        tipopago = "";
        tipotarjeta = "";
        numtarjeta = "";
        caducidad = "";
        titular = "";
        codseg = -1;
        numcuenta = "";
        estado = -1;
        //----------------------------------------------------------------------
    }

    /**
     * Modifica los valores del cliente que son distintos de 0 o distintos de ""
     * @param String pasw_cli
     * @param String name_cli
     * @param String apell_cli
     * @param String email_cli
     * @param int tfn_cli
     * @param String tipovia_dir
     * @param String via_dir
     * @param String num_dir
     * @param int piso_dir
     * @param String puerta_dir
     * @param String escalera_dir
     * @param String localidad_dir
     * @param String prov_dir
     * @param int cp_dir
     * @param String tipo_pago
     * @param String tipo_tarj
     * @param String num_tarj
     * @param String fecha_cad
     * @param String titular_pago
     * @param int cod_seguridad
     * @param String num_cuenta
     * @param int estado
     * @return
     */
    public int modifCliente(String pasw_cli, String name_cli,
            String apell_cli, String email_cli, int tfn_cli,
            String tipovia_dir, String via_dir, String num_dir, String piso_dir,
            String puerta_dir, String escalera_dir, String localidad_dir,
            String prov_dir, int cp_dir,
            String tipo_pago, String tipo_tarj, String num_tarj, String fecha_cad,
            String titular_pago, int cod_seguridad, String num_cuenta, int estado_cli) {

        // Relativos al cliente ------------------------------------------------
        /*if (!name_cli.equals(""))       nombre = name_cli;
        if (!pasw_cli.equals(""))       pass = pasw_cli;
        if (!apell_cli.equals(""))      apellidos = apell_cli;
        if (!email_cli.equals(""))      email = email_cli;
        if (tfn_cli!=-1)                telefono = tfn_cli;
        // Relativos a la dirección --------------------------------------------
        if (!via_dir.equals(""))        nomvia = via_dir;
        if (!tipovia_dir.equals(""))    tipovia = tipovia_dir;
        if (piso_dir!=-1)               piso = piso_dir;
        if (!puerta_dir.equals(""))     puerta = puerta_dir;
        if (!num_dir.equals(""))        numcalle = num_dir;
        if (!escalera_dir.equals(""))   escalera = escalera_dir;
        if (!localidad_dir.equals(""))  localidad = localidad_dir;
        if (!prov_dir.equals(""))       provincia = prov_dir;
        if (cp_dir!=-1)                 codpostal = cp_dir;
        // Relativos al pago ---------------------------------------------------
        if (!tipo_pago.equals(""))      tipopago = tipo_pago;
        if (!tipo_tarj.equals(""))      tipotarjeta = tipo_tarj;
        if (!num_tarj.equals(""))       numtarjeta = num_tarj;
        if (!fecha_cad.equals(""))      caducidad = fecha_cad;
        if (!titular_pago.equals(""))   titular = titular_pago;
        if (cod_seguridad!=-1)          codseg = cod_seguridad;
        if (!num_cuenta.equals(""))     numcuenta = num_cuenta;
        if (estado_cli!=-1)             estado = estado_cli;*/
        //----------------------------------------------------------------------

        // Relativos al cliente ------------------------------------------------
        nombre = name_cli;
        pass = pasw_cli;
        apellidos = apell_cli;
        email = email_cli;
        telefono = tfn_cli;
        // Relativos a la dirección --------------------------------------------
        nomvia = via_dir;
        tipovia = tipovia_dir;
        piso = piso_dir;
        puerta = puerta_dir;
        numcalle = num_dir;
        escalera = escalera_dir;
        localidad = localidad_dir;
        provincia = prov_dir;
        codpostal = cp_dir;
        // Relativos al pago ---------------------------------------------------
        tipopago = tipo_pago;
        tipotarjeta = tipo_tarj;
        numtarjeta = num_tarj;
        caducidad = fecha_cad;
        titular = titular_pago;
        codseg = cod_seguridad;
        numcuenta = num_cuenta;
        estado = estado_cli;
        //----------------------------------------------------------------------
        return (MSJ.OK);
    }

    public boolean estaActivo() {
        return (estado == ACTIVO);
    }

    /**
     *
     * @return String nif
     */
    public String getNif() {

        return (nif);
    }

    /**
     *
     * @return String pass
     */
    public String getPass() {

        return (pass);
    }

    /**
     *
     * @return String nombre
     */
    public String getNombre() {

        return (nombre);
    }

    /**
     *
     * @return String apellidos
     */
    public String getApellidos() {

        return (apellidos);
    }

    /**
     *
     * @return String email
     */
    public String getEmail() {

        return (email);
    }

    /**
     *
     * @return int telefono
     */
    public int getTelefono() {

        return (telefono);
    }

    /**
     *
     * @return String tipovia
     */
    public String getTipovia() {

        return (tipovia);
    }

    /**
     *
     * @return String nomvia
     */
    public String getNomvia() {

        return (nomvia);
    }

    /**
     *
     * @return String numcalle
     */
    public String getNumcalle() {

        return (numcalle);
    }

    /**
     *
     * @return int piso
     */
    public String getPiso() {

        return (piso);
    }

    /**
     *
     * @return String puerta
     */
    public String getPuerta() {

        return (puerta);
    }

    /**
     *
     * @return String escalera
     */
    public String getEscalera() {

        return (escalera);
    }

    /**
     *
     * @return String localidad
     */
    public String getLocalidad() {

        return (localidad);
    }

    /**
     *
     * @return String provincia
     */
    public String getProvincia() {

        return (provincia);
    }

    /**
     *
     * @return String codpostal
     */
    public int getCodpostal() {

        return (codpostal);
    }

    /**
     *
     * @return String tipopago
     */
    public String getTipopago() {

        return (tipopago);
    }

    /**
     *
     * @return String tipotarjeta
     */
    public String getTipotarjeta() {

        return (tipotarjeta);
    }

    /**
     *
     * @return String numtarjeta
     */
    public String getNumtarjeta() {

        return (numtarjeta);
    }

    /**
     *
     * @return String caducidad
     */
    public String getCaducidad() {

        return (caducidad);
    }

    /**
     *
     * @return String titular
     */
    public String getTitular() {

        return (titular);
    }

    /**
     *
     * @return String codseg
     */
    public int getCodseg() {

        return (codseg);
    }

    /**
     *
     * @return String numcuenta
     */
    public String getNumcuenta() {

        return (numcuenta);
    }

    /**
     *
     * @return boolean estado
     */
    public int getEstado() {
        return (estado);
    }

    public void setEstado(int _estado) {

        if (_estado != -1) {
            estado = _estado;
        }
    }
}
