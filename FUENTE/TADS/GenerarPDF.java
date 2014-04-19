/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TADS;

import BaseDatos.BaseDatos;
import BaseDatos.Cliente_VO;
import BaseDatos.Servicio_VO;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.IOException;
import java.util.ArrayList;

public class GenerarPDF {

    private String strNombreDelPDF;
    private BaseDatos BD;
    private Cliente_VO c_VO;
    private int id_fac;
    private Font fuenteNegra10 = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD, new BaseColor(0, 0, 0));
    private Font fuente8 = new Font(Font.FontFamily.TIMES_ROMAN, 10.0f, Font.NORMAL, new BaseColor(0, 0, 0));
    private Font fuenteAzul25 = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.BOLD, new BaseColor(124, 195, 255));
    // private static final Font DEFAULTFONT = new java.awt.Font("Comic Sans MS", 3, 15);
    BaseColor grisClaro = new BaseColor(230, 230, 230);
    BaseColor azulClaro = new BaseColor(124, 195, 255);
    Document document;
    PdfWriter writer;
    String strRotuloPDF;

    public GenerarPDF(String titulo, String nomPDF, BaseDatos bd, int idf, Cliente_VO c) {
        strRotuloPDF = titulo;
        strNombreDelPDF = nomPDF;
        BD = bd;
        c_VO = c;

        id_fac = idf;
        try {       //Hoja tamanio carta, rotarla (cambiar a horizontal)
            document = new Document(PageSize.LETTER.rotate());

            writer = PdfWriter.getInstance(
                    // that listens to the document
                    document,
                    // direccionar el PDF-stream a un archivo
                    new FileOutputStream(strNombreDelPDF));
            document.open();

            agregarMetaDatos(document);
            agregarContenido(document);

            document.close();

            System.out.println("Se ha generado el PDF: " + strNombreDelPDF);

        } catch (Exception e) {
        }
    }

    private void agregarContenido(Document document) throws DocumentException {
        Paragraph ParrafoHoja = new Paragraph();

        // Agregamos una linea en blanco al principio del documento
        agregarLineasEnBlanco(ParrafoHoja, 1);
        // 2.- AGREGAMOS LA IMAGEN
        try {
            Image im = Image.getInstance("src/Imagenes/Logo.png");
            im.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP);
            im.setWidthPercentage(50);
            ParrafoHoja.add(im);
        } catch (Exception e) {
            e.printStackTrace();
        }
        agregarLineasEnBlanco(ParrafoHoja, 10);
        // Colocar un encabezado (en mayusculas)
        ParrafoHoja.add(new Paragraph(strRotuloPDF.toUpperCase(), fuenteAzul25));

        agregarLineasEnBlanco(ParrafoHoja, 1);
        // agregamos datos del cliente y la linea
        agregarDatos(ParrafoHoja);
        agregarLineasEnBlanco(ParrafoHoja, 1);
        // 1.- AGREGAMOS LA TABLA
        agregarTabla(ParrafoHoja);
        agregarLineasEnBlanco(ParrafoHoja, 2);


        document.add(ParrafoHoja);

    }

    private void agregarDatos(Paragraph parrafo) throws BadElementException {

        parrafo.add(new Paragraph("Cliente: " + c_VO.getNif(), fuenteNegra10));
        parrafo.add(new Paragraph("Nombre : " + c_VO.getNombre() + " " + c_VO.getApellidos(), fuenteNegra10));
    }

    private void agregarTabla(Paragraph parrafo) throws BadElementException {
        //Anchos de las columnas
        float anchosFilas[] = {0.2f, 1f, 1f, 0.5f};
        PdfPTable tabla = new PdfPTable(anchosFilas.length);
        String rotulosColumnas[] = {"Telefono destino", "Fecha", "Duracion", "Importe"};
        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(100);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        PdfPCell cell = new PdfPCell(new Paragraph("Servicios de voz", fuenteNegra10));
        cell.setColspan(9);
        //Centrar contenido de celda
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //Color de fondo de la celda
        cell.setBackgroundColor(azulClaro);
        tabla.addCell(cell);



        // Mostrar los rotulos de las columnas
        for (int i = 0; i < rotulosColumnas.length; i++) {
            cell = new PdfPCell(new Paragraph(rotulosColumnas[i], fuenteNegra10));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(grisClaro);
            tabla.addCell(cell);
        }

        //Consultar la tabla factura
        Servicio s = new Servicio(BD);
        ArrayList lVoz = s.listarVOZFactura(id_fac);


        //Iterar Mientras haya una fila siguiente
        for (int i = 0; i < lVoz.size(); i++) {           //Agregar 9 celdas
            Servicio_VO svo = (Servicio_VO) lVoz.get(i);
            cell = new PdfPCell(new Paragraph(Integer.toString(svo.getNumdestino()), fuente8));
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph(svo.getFecha(), fuente8));
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph(String.valueOf(svo.getDuracion()), fuente8));
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph(String.valueOf(svo.getDuracion() * Servicio_VO.PRECIOVOZMIN), fuente8));
            tabla.addCell(cell);
        }
        //Agregar la tabla con los datos al parrafo que nos llego como entrada
        parrafo.add(tabla);

        agregarLineasEnBlanco(parrafo, 2);

        //Agregamos servicios SMS
        float anchosFilas1[] = {0.2f, 1f, 1f};
        tabla = new PdfPTable(anchosFilas1.length);
        String rotulosColumnas1[] = {"Telefono destino", "Fecha", "Importe"};
        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(100);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        cell = new PdfPCell(new Paragraph("Servicios de SMS", fuenteNegra10));
        cell.setColspan(9);
        //Centrar contenido de celda
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //Color de fondo de la celda
        cell.setBackgroundColor(azulClaro);
        tabla.addCell(cell);

        // Mostrar los rotulos de las columnas
        for (int i = 0; i < rotulosColumnas1.length; i++) {
            cell = new PdfPCell(new Paragraph(rotulosColumnas1[i], fuenteNegra10));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(grisClaro);
            tabla.addCell(cell);
        }
        lVoz = s.listarSMSFactura(id_fac);

        //Iterar Mientras haya una fila siguiente
        for (int i = 0; i < lVoz.size(); i++) {           //Agregar 9 celdas
            Servicio_VO svo = (Servicio_VO) lVoz.get(i);
            cell = new PdfPCell(new Paragraph(Integer.toString(svo.getNumdestino()), fuente8));
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph(svo.getFecha(), fuente8));
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph(String.valueOf(Servicio_VO.PRECIOSMS), fuente8));
            tabla.addCell(cell);

        }
        //Agregar la tabla con los datos al parrafo que nos llego como entrada
        parrafo.add(tabla);

        //Agregar servicio de MMS
        agregarLineasEnBlanco(parrafo, 2);
        //Agregamos servicios SMS
        float anchosFilas2[] = {0.2f, 1f, 1f, 0.5f};
        tabla = new PdfPTable(anchosFilas2.length);
        String rotulosColumnas2[] = {"Telefono destino", "Fecha", "Datos transferidos", "Importe"};
        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(100);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        cell = new PdfPCell(new Paragraph("Servicios de MMS", fuenteNegra10));
        cell.setColspan(9);
        //Centrar contenido de celda
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //Color de fondo de la celda
        cell.setBackgroundColor(azulClaro);
        tabla.addCell(cell);

        // Mostrar los rotulos de las columnas
        for (int i = 0; i < rotulosColumnas2.length; i++) {
            cell = new PdfPCell(new Paragraph(rotulosColumnas2[i], fuenteNegra10));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(grisClaro);
            tabla.addCell(cell);
        }

        lVoz = s.listarMMSFactura(id_fac);
        for (int i = 0; i < lVoz.size(); i++) {           //Agregar 9 celdas
            Servicio_VO svo = (Servicio_VO) lVoz.get(i);
            cell = new PdfPCell(new Paragraph(Integer.toString(svo.getNumdestino()), fuente8));
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph(svo.getFecha(), fuente8));
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph(Integer.toString(svo.getDatos()), fuente8));
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph(String.valueOf(Servicio_VO.PRECIOMMS), fuente8));
            tabla.addCell(cell);
        }
        //Agregar la tabla con los datos al parrafo que nos llego como entrada
        parrafo.add(tabla);

        agregarLineasEnBlanco(parrafo, 2);
        //Agregamos servicios SMS
        float anchosFilas3[] = {0.2f, 1f, 1f};
        tabla = new PdfPTable(anchosFilas3.length);
        String rotulosColumnas3[] = {"Datos transeferidos", "Fecha", "Importe"};
        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(100);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        cell = new PdfPCell(new Paragraph("Servicios de Datos", fuenteNegra10));
        cell.setColspan(9);
        //Centrar contenido de celda
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //Color de fondo de la celda
        cell.setBackgroundColor(azulClaro);
        tabla.addCell(cell);
        // Mostrar los rotulos de las columnas
        for (int i = 0; i < rotulosColumnas3.length; i++) {
            cell = new PdfPCell(new Paragraph(rotulosColumnas3[i], fuenteNegra10));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(grisClaro);
            tabla.addCell(cell);
        }
        lVoz = s.listarDATOSFactura(id_fac);
        for (int i = 0; i < lVoz.size(); i++) {           //Agregar 9 celdas
            Servicio_VO svo = (Servicio_VO) lVoz.get(i);
            cell = new PdfPCell(new Paragraph(Integer.toString(svo.getDatos()), fuente8));
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph(svo.getFecha(), fuente8));
            tabla.addCell(cell);
            cell = new PdfPCell(new Paragraph(String.valueOf(svo.getDatos() * Servicio_VO.PRECIODATOSMB), fuente8));
            tabla.addCell(cell);
        }
        //Agregar la tabla con los datos al parrafo que nos llego como entrada
        parrafo.add(tabla);
    }

    private static void agregarLineasEnBlanco(Paragraph parrafo, int nLineas) {
        for (int i = 0; i < nLineas; i++) {
            parrafo.add(new Paragraph(" "));
        }
    }

    private static void agregarMetaDatos(Document document) {
        document.addTitle("Documento con datos de tabla MySQL");
        document.addSubject("Usando iText y MySQL");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Grupo");
        document.addCreator("Grupo");
    }

    public void abrirPDF() {
        /* Abrir el archivo PDF */
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + strNombreDelPDF);
        } catch (IOException e) {
        }
    }
}
