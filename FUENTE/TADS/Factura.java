/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TADS;

import BaseDatos.*;
import MSJ.MSJ;
import java.util.*;

public class Factura {

    private static BaseDatos bd;

    public Factura(BaseDatos basedatos) {

        bd = basedatos;
    }

    public int crearFactura(String fecha_inicio, String fecha_fin, int tfn) {

        if (!esFecha(fecha_inicio)) {
            fecha_inicio = obtenerUltimaFechaFacturadaLinea(tfn);
        }
        if (!esFecha(fecha_fin)) {
            fecha_fin = obtenerFechaActual();
        }
        Factura_VO f = new Factura_VO(-1, 0, fecha_inicio, fecha_fin, Factura_VO.NOPAGADA, tfn);
        bd.insertaFactura(f);
        ArrayList listaFc = bd.buscaFactura(f);
        if (listaFc.isEmpty()) {
            return (MSJ.ACTUALIZACION_FACTURA_ERRONEA);
        }
        f = (Factura_VO) listaFc.get(0);
        return (MSJ.OK);
    }

    public int actualizarFactura(int num_factura, double importe_factura, String fecha_inicio,
            String fecha_fin, int esta_pagada) {

        Factura_VO f = obtenerFactura(num_factura);
        f.actualizar(importe_factura, fecha_inicio, fecha_fin, esta_pagada);
        int info = bd.actualizaFactura(f);

        return (info > 0 ? MSJ.OK : info);
    }

    public int pagar(int num_factura) {

        return (actualizarFactura(num_factura, 0, "", "", Factura_VO.PAGADA));
    }

    public ArrayList listarFacturasLinea(int tfn) {

        return (bd.buscaFactura(new Factura_VO(-1, -1, "", "", -1, tfn)));
    }

    static protected String obtenerFechaActual() {

        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        String fecha = (Integer.toString(cal.get(Calendar.YEAR)));
        fecha = fecha + "/";
        if ((cal.get(Calendar.MONTH) + 1) < 9) {
            fecha = fecha + "0";
        }
        fecha = fecha + (Integer.toString(cal.get(Calendar.MONTH) + 1));
        fecha = fecha + "/";
        if ((cal.get(Calendar.DAY_OF_MONTH)) < 9) {
            fecha = fecha + "0";
        }
        fecha = fecha + (Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
        fecha = fecha + ("/");
        if ((cal.get(Calendar.HOUR_OF_DAY)) < 9) {
            fecha = fecha + "0";
        }
        fecha = fecha + (Integer.toString(cal.get(Calendar.HOUR_OF_DAY)));
        fecha = fecha + "/";
        if ((cal.get(Calendar.MINUTE)) < 9) {
            fecha = fecha + "0";
        }
        fecha = fecha + (Integer.toString(cal.get(Calendar.MINUTE)));
        return (fecha);
    }

    private String obtenerUltimaFechaFacturadaLinea(int tfn) {

        ArrayList listaFc = listarFacturasLinea(tfn);
        Factura_VO f;
        String ultima_fecha = "1801/01/01/00/00";
        for (int i = 0; i < listaFc.size(); i++) {
            f = (Factura_VO) listaFc.get(i);
            if (esMenor(ultima_fecha, f.getFechafin())) {
                ultima_fecha = f.getFechafin();
            }
        }
        return (ultima_fecha);
    }

    public boolean existeFactura(int id_fac) {

        Factura_VO f = new Factura_VO(id_fac, -1, "", "", -1, -1);
        ArrayList arrayFc = bd.buscaFactura(f);
        if (!arrayFc.isEmpty()) {
            return (true);
        } else {
            return (false);
        }
    }

    public Factura_VO obtenerFactura(int id_fac) {

        Factura_VO f = new Factura_VO(id_fac, -1, "", "", -1, -1);
        ArrayList arrayFc = bd.buscaFactura(f);
        if (!arrayFc.isEmpty()) {
            return ((Factura_VO) arrayFc.get(0));
        } else {
            return (null);
        }
    }

    static protected boolean esFecha(String f) {

        if (f.length() != 16) {
            return (false);
        }
        try {
            // aaaa/mm/dd/hh/mm

            if ((Integer.parseInt(f.substring(0, 4)) >= 1800)
                    && (f.charAt(4) == '/')
                    && (Integer.parseInt(f.substring(5, 7)) >= 0)
                    && (f.charAt(7) == '/')
                    && (Integer.parseInt(f.substring(8, 10)) >= 0)
                    && (f.charAt(10) == '/')
                    && (Integer.parseInt(f.substring(11, 13)) >= 0)
                    && (f.charAt(13) == '/')
                    && (Integer.parseInt(f.substring(14, 16)) >= 0)) {
                return (true);
            } else {
                return (false);
            }
        } catch (Exception e) {
            return (false);
        }
    }

    static protected boolean esMayor(String fecha1, String fecha2) {

        if (!esFecha(fecha1) || !esFecha(fecha2)) {
            return (false);
        }
        if (Integer.parseInt(fecha1.substring(0, 4)) >= Integer.parseInt(fecha2.substring(0, 4))) {
            if (Integer.parseInt(fecha1.substring(0, 4)) == Integer.parseInt(fecha2.substring(0, 4))) {
                if (Integer.parseInt(fecha1.substring(5, 7)) >= Integer.parseInt(fecha2.substring(5, 7))) {
                    if (Integer.parseInt(fecha1.substring(5, 7)) == Integer.parseInt(fecha2.substring(5, 7))) {
                        if (Integer.parseInt(fecha1.substring(8, 10)) >= Integer.parseInt(fecha2.substring(8, 10))) {
                            if (Integer.parseInt(fecha1.substring(8, 10)) == Integer.parseInt(fecha2.substring(8, 10))) {
                                if (Integer.parseInt(fecha1.substring(11, 13)) >= Integer.parseInt(fecha2.substring(11, 13))) {
                                    if (Integer.parseInt(fecha1.substring(11, 13)) == Integer.parseInt(fecha2.substring(11, 13))) {
                                        if (Integer.parseInt(fecha1.substring(14, 16)) >= Integer.parseInt(fecha2.substring(14, 16))) {
                                            if (Integer.parseInt(fecha1.substring(14, 16)) == Integer.parseInt(fecha2.substring(14, 16))) {
                                                return (false);
                                            }
                                            return (true);
                                        }
                                    }
                                    return (true);
                                }
                            }
                            return (true);
                        }
                    }
                    return (true);
                }
            }
            return (true);
        }
        return (false);
    }

    static protected boolean esMenor(String fecha1, String fecha2) {

        if (!esFecha(fecha1) || !esFecha(fecha2)) {
            return (false);
        }
        if (!esMayor(fecha1, fecha2) && !fecha1.equals(fecha2)) {
            return (true);
        } else {
            return (false);
        }
    }
}
