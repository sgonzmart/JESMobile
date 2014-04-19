package Simulador;

import BaseDatos.*;
import MSJ.MSJ;
import TADS.*;

public class GestorSimulador {

    static BaseDatos bd;

    /**
     * Constructor
     * @param BaseDatos db
     */
    public GestorSimulador(BaseDatos db) {

        bd = db;
    }

    /**
     *  
     * @param     int codserv;
     * @param     int nselec;
     * @param     int numdest;
     * @param     String fech;
     * @param     int dur;
     * @param     int d;
     * @param     int ids;
     * @return    int ERROR/OK
     */
    public int registrarServicio(int nselec, int codserv, int numdest, String fech, int dur, int d) {
        // Busca linea con numero nselec en la base de datos

        //Linea l1 = new Linea (nselec,"",1);
        //ArrayList arrayl1 = bd.buscaLinea(l1);

        Linea gl = new Linea(bd);
        Servicio ser = new Servicio(bd);
        if (!gl.existeLinea(nselec)) {  // si la linea no existe
            return (MSJ.LINEA_NO_EXISTE);
        } //Si el numero de telefono existe, registro el nuevo servicio
        else {
            //Servicio_VO S1 = new Servicio_VO(-1, codserv, numdest, fech, dur, d, -1, nselec);
            int info = ser.nuevoServicio(nselec, codserv, numdest, fech, dur, d);
            return ((info < 0) ? info : MSJ.OK);
        }


    }
}
