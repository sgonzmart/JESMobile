/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

public class Factura_VO {

    private int numfactura;
    private double importe;
    private String fechainicio;
    private String fechafin;
    private int estapagada;
    private int numtlf;
    public static final int PAGADA = 1;
    public static final int NOPAGADA = 0;

    /**
     * Constructor de la clase
     * @param int num_factura
     * @param float importe_factura
     * @param String fecha_inicio
     * @param String fecha_fin
     * @param int esta_pagada
     * @param int numtlf;
     */
    public Factura_VO(int num_factura, double importe_factura, String fecha_inicio,
            String fecha_fin, int esta_pagada, int numero) {

        numfactura = num_factura;
        importe = importe_factura;
        fechainicio = fecha_inicio;
        fechafin = fecha_fin;
        estapagada = esta_pagada;
        numtlf = numero;
    }

    /**
     * Constructor de la clase
     * @param Factura f
     */
    public Factura_VO(Factura_VO f) {

        numfactura = f.numfactura;
        importe = f.importe;
        fechainicio = f.fechainicio;
        fechafin = f.fechafin;
        estapagada = f.estapagada;
        numtlf = f.numtlf;
    }

    /**
     * Constructor de la clase
     */
    public Factura_VO() {

        numfactura = 0;
        importe = 0;
        fechainicio = "";
        fechafin = "";
        estapagada = 0;
        numtlf = 0;
    }

    public void actualizar(double importe_factura, String fecha_inicio,
            String fecha_fin, int esta_pagada) {

        if (importe_factura >= 0) {
            importe = importe_factura;
        }
        if (!fecha_inicio.equals("")) {
            fechainicio = fecha_inicio;
        }
        if (!fecha_fin.equals("")) {
            fechafin = fecha_fin;
        }
        if (esta_pagada > 0) {
            estapagada = esta_pagada;
        }
    }

    /**
     *
     * @return int numfactura
     */
    public int getNumfactura() {

        return (numfactura);
    }

    /**
     *
     * @return int importe
     */
    public double getImporte() {

        return (importe);
    }

    /**
     *
     * @return String fechainicio
     */
    public String getFechainicio() {

        return (fechainicio);
    }

    /**
     *
     * @return String fechafin
     */
    public String getFechafin() {

        return (fechafin);
    }

    /**
     *
     * @return int estapagada
     */
    public int getEstapagada() {

        return (estapagada);
    }

    /**
     *
     * @return int numtlf
     */
    public int getNumtlf() {
        return (numtlf);
    }

    @Override
    public String toString() {
        return ("numfactura = " + numfactura + ", importe = " + importe + ", fechainicio = "
                + fechainicio + ", fechafin = " + fechafin + ", estapagada = " + estapagada
                + ", numtlf = " + numtlf);
    }
}
