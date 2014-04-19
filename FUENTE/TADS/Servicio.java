/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TADS;

import BaseDatos.Servicio_VO;
import BaseDatos.Linea_VO;
import BaseDatos.BaseDatos;
import MSJ.MSJ;
import java.util.ArrayList;

public class Servicio {

    private static BaseDatos bd;

    public Servicio(BaseDatos basedatos) {

        bd = basedatos;
    }

    public int nuevoServicio(int tfn, int cod_servicio, int num_destino,
            String fecha_servicio, int duracion_servicio, int datos_servicio) {

        Linea l = new Linea(bd);
        if (!l.existeLinea(tfn)) {
            return (MSJ.LINEA_NO_EXISTE);
        }
        Servicio_VO s = new Servicio_VO(-1, cod_servicio, num_destino,
                fecha_servicio, duracion_servicio, datos_servicio, -1, tfn);

        int info = bd.insertaServicio(s);
        return (info);
    }

    public int nuevoServicio(Linea_VO l, int cod_servicio, int num_destino,
            String fecha_servicio, int duracion_servicio, int datos_servicio) {

        return (nuevoServicio(l.getNumtlf(), cod_servicio, num_destino,
                fecha_servicio, duracion_servicio, datos_servicio));
    }

    public int facturar(int id_sv, int id_fac) {

        if (!existeServicio(id_sv)) {
            return (MSJ.SERVICIO_NO_EXISTE);
        }
        Factura gf = new Factura(bd);
        if (!gf.existeFactura(id_fac)) {
            return (MSJ.FACTURA_NO_EXISTE);
        }
        Servicio_VO s = obtenerServicio(id_sv);
        s.setFactura(id_fac);
        return (bd.actualizaServicio(s));

    }

    public ArrayList listarServiciosLinea(int tfn) {

        return (bd.buscaServicio(new Servicio_VO(-1, -1, -1, "", -1, -1, -1, tfn)));
    }

    public ArrayList listarSMSLinea(int tfn) {

        return (bd.buscaServicio(new Servicio_VO(-1, Servicio_VO.SMS, -1, "", -1, -1, -1, tfn)));
    }

    public ArrayList listarMMSLinea(int tfn) {

        return (bd.buscaServicio(new Servicio_VO(-1, Servicio_VO.MMS, -1, "", -1, -1, -1, tfn)));
    }

    public ArrayList listarVOZLinea(int tfn) {

        return (bd.buscaServicio(new Servicio_VO(-1, Servicio_VO.VOZ, -1, "", -1, -1, -1, tfn)));
    }

    public ArrayList listarDATOSLinea(int tfn) {

        return (bd.buscaServicio(new Servicio_VO(-1, Servicio_VO.DATOS, -1, "", -1, -1, -1, tfn)));
    }

    public ArrayList listarServiciosLineaEntreDosFechas(int tfn, String ppio, String fin) {

        ArrayList listaSv = listarServiciosLinea(tfn);
        ArrayList listaResul = new ArrayList();
        Servicio_VO s = new Servicio_VO();
        for (int i = 0; i < listaSv.size(); i++) {
            s = (Servicio_VO) listaSv.get(i);
            if (esMayor(ppio, s.getFecha()) && esMenor(s.getFecha(), fin)) {
                listaResul.add(s);
            }
        }
        return (listaResul);
    }

    public ArrayList listarSMSLineaEntreDosFechas(int tfn, String ppio, String fin) {

        ArrayList listaSv = listarSMSLinea(tfn);
        ArrayList listaResul = new ArrayList();
        Servicio_VO s = new Servicio_VO();
        for (int i = 0; i < listaSv.size(); i++) {
            s = (Servicio_VO) listaSv.get(i);
            if (esMayor(ppio, s.getFecha()) && esMenor(s.getFecha(), fin)) {
                listaResul.add(s);
            }
        }
        return (listaResul);
    }

    public ArrayList listarMMSLineaEntreDosFechas(int tfn, String ppio, String fin) {

        ArrayList listaSv = listarMMSLinea(tfn);
        ArrayList listaResul = new ArrayList();
        Servicio_VO s = new Servicio_VO();
        for (int i = 0; i < listaSv.size(); i++) {
            s = (Servicio_VO) listaSv.get(i);
            if (esMayor(ppio, s.getFecha()) && esMenor(s.getFecha(), fin)) {
                listaResul.add(s);
            }
        }
        return (listaResul);
    }

    public ArrayList listarVOZLineaEntreDosFechas(int tfn, String ppio, String fin) {

        ArrayList listaSv = listarVOZLinea(tfn);
        ArrayList listaResul = new ArrayList();
        Servicio_VO s = new Servicio_VO();
        for (int i = 0; i < listaSv.size(); i++) {
            s = (Servicio_VO) listaSv.get(i);
            if (esMayor(ppio, s.getFecha()) && esMenor(s.getFecha(), fin)) {
                listaResul.add(s);
            }
        }
        return (listaResul);
    }

    public ArrayList listarDATOSLineaEntreDosFechas(int tfn, String ppio, String fin) {

        ArrayList listaSv = listarDATOSLinea(tfn);
        ArrayList listaResul = new ArrayList();
        Servicio_VO s = new Servicio_VO();
        for (int i = 0; i < listaSv.size(); i++) {
            s = (Servicio_VO) listaSv.get(i);
            if (esMayor(ppio, s.getFecha()) && esMenor(s.getFecha(), fin)) {
                listaResul.add(s);
            }
        }
        return (listaResul);
    }

    public ArrayList listarServiciosFactura(int id_fac) {

        return (bd.buscaServicio(new Servicio_VO(-1, -1, -1, "", -1, -1, id_fac, -1)));
    }

    public ArrayList listarSMSFactura(int id_fac) {

        return (bd.buscaServicio(new Servicio_VO(-1, Servicio_VO.SMS, -1, "", -1, -1, id_fac, -1)));
    }

    public ArrayList listarMMSFactura(int id_fac) {

        return (bd.buscaServicio(new Servicio_VO(-1, Servicio_VO.MMS, -1, "", -1, -1, id_fac, -1)));
    }

    public ArrayList listarVOZFactura(int id_fac) {

        return (bd.buscaServicio(new Servicio_VO(-1, Servicio_VO.VOZ, -1, "", -1, -1, id_fac, -1)));
    }

    public ArrayList listarDATOSFactura(int id_fac) {

        return (bd.buscaServicio(new Servicio_VO(-1, Servicio_VO.DATOS, -1, "", -1, -1, id_fac, -1)));
    }

    public ArrayList listarServiciosSinFacturar(int num) {

        ArrayList listaSv = bd.buscaServicio(new Servicio_VO(-1, -1, -1, "", -1, -1, -1, num));
        ArrayList listaSinFacturar = new ArrayList();

        for (int i = 0; i < listaSv.size(); i++) {
            if (((Servicio_VO) (listaSv.get(i))).getIdFactura() == 0) {
                listaSinFacturar.add((Servicio_VO) listaSv.get(i));
            }
        }

        return (listaSinFacturar);
    }

    private boolean esFecha(String f) {

        if (f.length() != 14) {
            return (false);
        }
        try {
            if ((Integer.parseInt(f.substring(0, 3)) > 1800)
                    && (f.charAt(4) == '/')
                    && (Integer.parseInt(f.substring(5, 7)) > 0)
                    && (f.charAt(7) == '/')
                    && (Integer.parseInt(f.substring(8, 10)) > 0)
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

    private boolean esMayor(String fecha1, String fecha2) {

        if (!esFecha(fecha1) || !esFecha(fecha2)) {
            return (false);
        }
        if (Integer.parseInt(fecha1.substring(0, 3)) >= Integer.parseInt(fecha2.substring(0, 3))) {
            if (Integer.parseInt(fecha1.substring(0, 3)) == Integer.parseInt(fecha2.substring(0, 3))) {
                if (Integer.parseInt(fecha1.substring(5, 7)) >= Integer.parseInt(fecha2.substring(5, 7))) {
                    if (Integer.parseInt(fecha1.substring(5, 7)) == Integer.parseInt(fecha2.substring(5, 7))) {
                        if (Integer.parseInt(fecha1.substring(8, 10)) >= Integer.parseInt(fecha2.substring(8, 10))) {
                            if (Integer.parseInt(fecha1.substring(8, 10)) == Integer.parseInt(fecha2.substring(8, 10))) {
                                if (Integer.parseInt(fecha1.substring(11, 13)) >= Integer.parseInt(fecha2.substring(11, 13))) {
                                    if (Integer.parseInt(fecha1.substring(11, 13)) == Integer.parseInt(fecha2.substring(11, 13))) {
                                        if (Integer.parseInt(fecha1.substring(14, 16)) >= Integer.parseInt(fecha2.substring(14, 16))) {
                                            if (Integer.parseInt(fecha1.substring(14, 16)) >= Integer.parseInt(fecha2.substring(14, 16))) {
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

    private boolean esMenor(String fecha1, String fecha2) {

        if (!esMayor(fecha1, fecha2) && !fecha1.equals(fecha2)) {
            return (true);
        } else {
            return (false);
        }
    }

    private boolean existeServicio(int id_sv) {

        Servicio_VO sv = new Servicio_VO(id_sv, -1, -1, "", -1, -1, -1, -1);
        ArrayList arraySv = bd.buscaServicio(sv);
        if (!arraySv.isEmpty()) {
            return (true);
        } else {
            return (false);
        }
    }

    private Servicio_VO obtenerServicio(int id_sv) {

        Servicio_VO sv = new Servicio_VO(id_sv, -1, -1, "", -1, -1, -1, -1);
        ArrayList arraySv = bd.buscaServicio(sv);
        if (!arraySv.isEmpty()) {
            return ((Servicio_VO) arraySv.get(0));
        } else {
            return (null);
        }
    }
}
