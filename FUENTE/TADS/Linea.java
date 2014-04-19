/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TADS;

import BaseDatos.Linea_VO;
import BaseDatos.Cliente_VO;
import BaseDatos.BaseDatos;
import BaseDatos.Factura_VO;
import BaseDatos.Servicio_VO;
import MSJ.MSJ;
import java.util.ArrayList;

public class Linea {

    private static BaseDatos bd;

    public Linea(BaseDatos basedatos) {

        bd = basedatos;
    }

    public int nuevaLinea(String nif) {

        //if (!GestorClientes.existeCliente(c)){
        //    return(null);
        //}

        // Valor entre 699999999 y 600000000, ambos incluidos.
        int numero = (int) Math.floor(Math.random() * (699999999 - 600000000 + 1) + 600000000);
        // Iteramos hasta que encontremos uno que no exista
        while (existeLinea(numero)) {
            numero = (int) Math.floor(Math.random() * (699999999 - 600000000 + 1) + 600000000);
        }
        Linea_VO li = new Linea_VO(numero, nif, Linea_VO.ACTIVA);
        int info = bd.insertaLinea(li);
        if (info == MSJ.OK) {
            return (numero);
        } else {
            return info;
        }
    }

    public int nuevaLinea(Cliente_VO c) {

        return (nuevaLinea(c.getNif()));
    }

    public int borrarLinea(int num) {

        if (!existeLinea(num)) {
            return (MSJ.LINEA_NO_EXISTE);
        }
        Linea_VO li = obtenerLinea(num);
        li.setEstado(Linea_VO.INACTIVA);
        int info = bd.actualizaLinea(li);
        return (info > 0 ? MSJ.OK : info);
    }

    public int borrarLinea(Linea_VO l) {

        return (borrarLinea(l.getNumtlf()));
    }

    public boolean existeLinea(int num) {

        Linea_VO li = new Linea_VO(num, "", -1);
        ArrayList arrayLi = bd.buscaLinea(li);
        if (!arrayLi.isEmpty()) {
            return (true);
        } else {
            return (false);
        }
    }

    public boolean existeLinea(Linea_VO li) {

        return (existeLinea(li.getNumtlf()));
    }

    private Linea_VO obtenerLinea(int num) {

        if (!existeLinea(num)) {
            return (null);
        }
        Linea_VO li = new Linea_VO(num, "", -1);
        ArrayList arrayLi = bd.buscaLinea(li);
        li = (Linea_VO) arrayLi.get(0);
        return (li);
    }

    private ArrayList listarLineasCliente(String nif) {

        Linea_VO li = new Linea_VO(-1, nif, -1);
        ArrayList arrayLi = bd.buscaLinea(li);
        return (arrayLi);
    }

    public ArrayList listarLineasCliente(Cliente_VO c) {

        return (listarLineasCliente(c.getNif()));
    }

    public ArrayList listarLineasActivasCliente(String nif) {
        Linea_VO li = new Linea_VO(-1, nif, Linea_VO.ACTIVA);
        ArrayList arrayLi = bd.buscaLinea(li);
        return (arrayLi);
    }

    public ArrayList listarTelefonosCliente(String nif) {

        ArrayList listaLineas = bd.buscaLinea(new Linea_VO(-1, nif, Linea_VO.ACTIVA));
        ArrayList listaNum = new ArrayList();
        for (int i = 0; i < listaLineas.size(); i++) {
            listaNum.add(((Linea_VO) listaLineas.get(i)).getNumtlf());
        }
        return (listaNum);
    }

    public ArrayList listarTodasLineasActivas() {
        ArrayList arrayLi = bd.listaLineasActivas();
        return (arrayLi);
    }

    public int facturar(int num) {

        ArrayList listaSv = bd.buscaServicio(new Servicio_VO(-1, -1, -1, "", -1, -1, -1, num));
        ArrayList listaSinFacturar = new ArrayList();

        for (int i = 0; i < listaSv.size(); i++) {
            if (((Servicio_VO) (listaSv.get(i))).getIdFactura() == 0) {
                listaSinFacturar.add((Servicio_VO) listaSv.get(i));
            }
        }
        if (listaSinFacturar.isEmpty()) {
            return (MSJ.OK);
        }
        String fechaact = Factura.obtenerFechaActual();
        // Creamos la factura, para luego rellenarla:
        Factura f = new Factura(bd);
        f.crearFactura("", fechaact, num);

        // cargamos la factura, para saber el id:
        ArrayList listaFa = bd.buscaFactura(new Factura_VO(-1, -1, "", fechaact, -1, num));
        int id_f = ((Factura_VO) listaFa.get(0)).getNumfactura();
        // Ahora tenemos que añadir todos los servicios a esa factura
        Servicio s = new Servicio(bd);
        Servicio_VO s_vo;
        double precio = 0;
        for (int i = 0; i < listaSinFacturar.size(); i++) {
            s_vo = (Servicio_VO) (listaSinFacturar.get(i));
                        s.facturar(s_vo.getIdserv(), id_f);
            precio += s_vo.coste();

        }
        f.actualizarFactura(id_f, precio, "", "", -1);
        Factura_VO fac = (new Factura(bd)).obtenerFactura(id_f);
        return (MSJ.OK);
    }

    public double importeTotalLinea(int tfn) {

        Factura f = new Factura(bd);
        ArrayList li = f.listarFacturasLinea(tfn);
        if (li.isEmpty()) {
            return (0);
        }
        double total = 0.0;
        Factura_VO f_VO;
        for (int i = 0; i < li.size(); i++) {
            f_VO = (Factura_VO) li.get(i);
            total += f_VO.getImporte();
        }
        return (total);
    }
}
