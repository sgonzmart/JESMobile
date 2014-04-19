/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

public class Fila {

    public Fila() {
        this.telefono = "";
        this.fecha = "";
        this.hora = "";
        this.duracion = "";
        this.importe = "";
        this.volumen = "";
        this.servicio = "";
    }

    public Fila(String telefono, String fecha, String hora, String duracion,
            String importe, String volumen, String servicio) {
        this.telefono = telefono;
        this.fecha = fecha;
        this.hora = hora;
        this.duracion = duracion;
        this.importe = importe;
        this.volumen = volumen;
        this.servicio = servicio;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getImporte() {
        return importe;
    }

    public String getHora() {
        return hora;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getServicio() {
        return servicio;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }
    private String telefono = null;
    private String fecha = null;
    private String hora = null;
    private String duracion = null;
    private String importe = null;
    private String volumen = null;
    private String servicio = null;
}
