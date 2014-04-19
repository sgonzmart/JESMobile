/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

public class Servicio_VO {

    private int idserv; // CLAVE PRIMARIA
    private int codserv; // SMS/MMS/VOZ/DATOS
    private int numdestino;
    private String fecha;
    private int duracion;
    private int datos;
    private int numtlf; // CLAVE AJENA: CLIENTE
    private int id_factura; // CLAVE AJENA: FACTURA
    public static final int SMS = 1;
    public static final int MMS = 2;
    public static final int VOZ = 3;
    public static final int DATOS = 4;
    public static final double PRECIOSMS = 0.10;
    public static final double PRECIOMMS = 1;
    public static final double PRECIOVOZMIN = 0.15;
    public static final double PRECIODATOSMB = 0.5;

    /**
     * Constructor de la clase
     * @param int id_servicio
     * @param int cod_servicio
     * @param int num_destino
     * @param String fecha_servicio
     * @param int duracion_servicio
     * @param int datos_servicio
     * @param int numtlf;
     * @param string nif;
     */
    public Servicio_VO(int id_servicio, int cod_servicio, int num_destino,
            String fecha_servicio, int duracion_servicio, int datos_servicio, int idfactura, int numero) {

        idserv = id_servicio;
        codserv = cod_servicio;
        numdestino = num_destino;
        fecha = fecha_servicio;
        duracion = duracion_servicio;
        datos = datos_servicio;
        numtlf = numero;
        id_factura = idfactura;
    }

    /**
     * Constructor de la clase
     * @param Servicio s
     */
    public Servicio_VO(Servicio_VO s) {

        idserv = s.idserv;
        codserv = s.codserv;
        numdestino = s.numdestino;
        fecha = s.fecha;
        duracion = s.duracion;
        datos = s.datos;
        numtlf = s.numtlf;
        id_factura = s.id_factura;
    }

    /**
     * Constructor de la clase
     */
    public Servicio_VO() {
        idserv = -1;
        codserv = -1;
        numdestino = -1;
        fecha = "";
        duracion = -1;
        datos = -1;
        numtlf = -1;
        id_factura = -1;
    }

    /**
     *
     * @returnn int idserv
     */
    public int getIdserv() {

        return (idserv);
    }

    /**
     *
     * @return int codserv
     */
    public int getCodserv() {

        return (codserv);
    }

    /**
     *
     * @returnint numdestino
     */
    public int getNumdestino() {

        return (numdestino);
    }

    /**
     *
     * @return String fecha
     */
    public String getFecha() {

        return (fecha);
    }

    /**
     *
     * @return int duracion
     */
    public int getDuracion() {

        return (duracion);
    }

    /**
     *
     * @return int datos
     */
    public int getDatos() {

        return (datos);
    }

    /**
     *
     * @return int numtlf
     */
    public int getNumtlf() {
        return (numtlf);
    }

    /**
     *
     * @return Sint id_factura
     */
    public int getIdFactura() {
        return (id_factura);
    }

    public void setFactura(int id_fac) {
        id_factura = (id_fac < 0 ? -1 : id_fac);
    }

    @Override
    public String toString() {

        return ("idserv = " + idserv + ", codserv = " + codserv + ", numdestino = "
                + numdestino + ", fecha = " + fecha + ", duracion = " + duracion
                + ", datos = " + datos + ", numtlf = " + numtlf + ", id_factura = " + id_factura);
    }

    public double coste() {

        switch (codserv) {
            case SMS:
                return PRECIOSMS;
            case MMS:
                return PRECIOMMS;
            case VOZ:
                return (PRECIOVOZMIN * duracion / 60);
            case DATOS:
                return (PRECIODATOSMB * datos / 1024);
            default:
                return (0);
        }
    }

    public static String tipoServicio(int n) {
        switch (n) {
            case (SMS):
                return "SMS";
            case (MMS):
                return "MMS";
            case (VOZ):
                return "Llamada";
            case (DATOS):
                return "Internet";
            default:
                return "";
        }
    }
}