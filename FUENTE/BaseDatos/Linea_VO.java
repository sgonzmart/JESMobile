/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

public class Linea_VO {

    private int numtlf; // CLAVE PRIMARIA
    private String nif; // CLAVE AJENA: REF: CLIENTE
    private int estado; // ACTIVO/INACTIVO
    public static final int ACTIVA = 1;
    public static final int INACTIVA = 0;

    /**
     * Constructor de la clase
     * @param int num_tlf
     * @param String nif_cliente
     */
    public Linea_VO(int num_tlf, String nif_cliente, int estado_linea) {

        numtlf = num_tlf;
        nif = nif_cliente;
        estado = estado_linea;
    }

    /**
     * Constructor de la clase
     * @param Linea l
     */
    public Linea_VO(Linea_VO l) {

        numtlf = l.numtlf;
        nif = l.nif;
        estado = l.estado;
    }

    /**
     * Constructor de la clase
     */
    public Linea_VO() {

        numtlf = 0;
        nif = "";
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
     * @return String nif
     */
    public String getNif() {

        return (nif);
    }

    public int getEstado() {
        return (estado);
    }

    public void setEstado(int _estado) {

        if (_estado != -1) {
            estado = _estado;
        }
    }
}
