/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tablas;

import javax.swing.table.*;
import javax.swing.event.*;
import java.util.LinkedList;

public class ModeloTabla implements TableModel {

    /** Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     *
     */
    public int getColumnCount() {
        return 7;
    }

    /** Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     *
     */
    public int getRowCount() {

        return datos.size();
    }

    /** Returns the value for the cell at <code>columnIndex</code> and
     * <code>rowIndex</code>.
     *
     * @param	rowIndex	the row whose value is to be queried
     * @param	columnIndex 	the column whose value is to be queried
     * @return	the value Object at the specified cell
     *
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        Fila aux;


        aux = (Fila) (datos.get(rowIndex));

        // Se obtiene el campo apropiado según el valor de columnIndex
        switch (columnIndex) {
            case 0:
                return aux.getServicio();
            case 1:
                return aux.getTelefono();
            case 2:
                return aux.getFecha();
            case 3:
                return aux.getHora();
            case 4:
                return aux.getDuracion();
            case 5:
                return aux.getVolumen();
            case 6:
                return aux.getImporte();
            default:
                return null;
        }
    }

    public void borraFila(int fila) {
        // Se borra la fila 
        datos.remove(fila);

        // Y se avisa a los suscriptores, creando un TableModelEvent...
        TableModelEvent evento = new TableModelEvent(this, fila, fila,
                TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);

        // ... y pasándoselo a los suscriptores
        avisaSuscriptores(evento);
    }

    public void anyadeFila(Fila nuevaFila) {
        // Añade la persona al modelo 
        datos.add(nuevaFila);

        // Avisa a los suscriptores creando un TableModelEvent...
        TableModelEvent evento;
        evento = new TableModelEvent(this, this.getRowCount() - 1,
                this.getRowCount() - 1, TableModelEvent.ALL_COLUMNS,
                TableModelEvent.INSERT);

        // ... y avisando a los suscriptores
        avisaSuscriptores(evento);
    }

    /** Adds a listener to the list that is notified each time a change
     * to the data model occurs.
     *
     * @param	l		the TableModelListener
     *
     */
    public void addTableModelListener(TableModelListener l) {
        // Añade el suscriptor a la lista de suscriptores
        listeners.add(l);
    }

    /** Returns the most specific superclass for all the cell values
     * in the column.  This is used by the <code>JTable</code> to set up a
     * default renderer and editor for the column.
     *
     * @param columnIndex  the index of the column
     * @return the common ancestor class of the object values in the model.
     *
     */
    public Class getColumnClass(int columnIndex) {
        // Devuelve la clase que hay en cada columna.
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            case 6:
                return String.class;
            default:
                // Devuelve una clase Object por defecto.
                return Object.class;
        }
    }

    /** Returns the name of the column at <code>columnIndex</code>.  This is used
     * to initialize the table's column header name.  Note: this name does
     * not need to be unique; two columns in a table can have the same name.
     *
     * @param	columnIndex	the index of the column
     * @return  the name of the column
     *
     */
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Servicio";
            case 1:
                return "Telefono      ";
            case 2:
                return "Fecha";
            case 3:
                return "Hora";
            case 4:
                return "Duracion";
            case 5:
                return "Vol(Kb)";
            case 6:
                return "Importe";
            default:
                return null;
        }
    }

    /** Returns true if the cell at <code>rowIndex</code> and
     * <code>columnIndex</code>
     * is editable.  Otherwise, <code>setValueAt</code> on the cell will not
     * change the value of that cell.
     *
     * @param	rowIndex	the row whose value to be queried
     * @param	columnIndex	the column whose value to be queried
     * @return	true if the cell is editable
     * @see #setValueAt
     *
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Permite que la celda no sea editable.
        return false;
    }

    public void setEditingColumn(int ColumnIndex) {
    }

    public void setEditingRow(int rowIndex) {
    }

    /** Removes a listener from the list that is notified each time a
     * change to the data model occurs.
     *
     * @param	l		the TableModelListener
     *
     */
    public void removeTableModelListener(TableModelListener l) {
        // Elimina los suscriptores.
        listeners.remove(l);
    }

    /** Sets the value in the cell at <code>columnIndex</code> and
     * <code>rowIndex</code> to <code>aValue</code>.
     *
     * @param	aValue		 the new value
     * @param	rowIndex	 the row whose value is to be changed
     * @param	columnIndex 	 the column whose value is to be changed
     * @see #getValueAt
     * @see #isCellEditable
     *
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // Obtiene la persona de la fila indicada
        Fila aux;
        aux = (Fila) (datos.get(rowIndex));

        // Cambia el campo de Persona que indica columnIndex, poniendole el 
        // aValue que se nos pasa.
        switch (columnIndex) {
            case 0:
                aux.setServicio((String) aValue);
                break;
            case 1:
                aux.setTelefono((String) aValue);
                break;
            case 2:
                aux.setFecha((String) aValue);
                break;
            case 3:
                aux.setHora((String) aValue);
                break;
            case 4:
                aux.setDuracion((String) aValue);
                break;
            case 5:
                aux.setVolumen((String) aValue);
                break;
            case 6:
                aux.setImporte((String) aValue);
                break;
            default:
                break;
        }

        // Avisa a los suscriptores del cambio, creando un TableModelEvent ...
        TableModelEvent evento = new TableModelEvent(this, rowIndex, rowIndex,
                columnIndex);

        // ... y pasándoselo a los suscriptores.
        avisaSuscriptores(evento);
    }

    /**
     * Pasa a los suscriptores el evento.
     */
    private void avisaSuscriptores(TableModelEvent evento) {
        int i;

        // Bucle para todos los suscriptores en la lista, se llama al metodo
        // tableChanged() de los mismos, pasándole el evento.
        for (i = 0; i < listeners.size(); i++) {
            ((TableModelListener) listeners.get(i)).tableChanged(evento);
        }
    }
    /** Lista con los datos. Cada elemento de la lista es una instancia de
     * Persona */
    private LinkedList datos = new LinkedList();
    /** Lista de suscriptores. El JTable será un suscriptor de este modelo de
     * datos */
    private LinkedList listeners = new LinkedList();
}
