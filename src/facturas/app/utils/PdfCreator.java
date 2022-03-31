/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facturas.app.utils;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Class for create a pdf document with a jtable
 */
public class PdfCreator {

    private JFrame frame;
    private JTable table;
    private JButton button;
    private Document document;
    private PdfWriter writer;
    private JScrollPane scrollPane;
    private String pathToFile;
    private boolean[] selectedColumns;

    /**
     * Constructor of the class
     * @param pathToFile
     * @param table
     */
    public PdfCreator (String pathToFile, JTable table) {
        this.pathToFile = pathToFile;
        this.table = table;
    }

    /**
     * @param selectedColumns
     */
    public void setSelectedColumns(boolean[] selectedColumns) {
        this.selectedColumns = selectedColumns;
    }
    
    /**
     * open pdf document to write
     */
    public void openPdf() throws FileNotFoundException, DocumentException {
        document = new Document(PageSize.A4, 0f, 0f, 30, 30);
        writer = PdfWriter.getInstance(document, new FileOutputStream(pathToFile));
        document.open();
    }

    /**
     * close the pdf
     */
    public void closePdf() {
        document.close();
    }

    /**
     * add the data to pdf table
     * @param pdfTable table where put the information
     */
    public void addData(PdfPTable pdfTable) throws DocumentException {
        pdfTable.setHeaderRows(1);
        
        float fntSize, lineSpacing;
        fntSize = 6.7f;
        lineSpacing = 10f;
        
        PdfPCell cell;
        for (int i = 0; i < table.getColumnCount(); i++) {
            if (selectedColumns == null || selectedColumns[i]) {
                cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, 
                        table.getColumnName(i), 
                        FontFactory.getFont(FontFactory.COURIER, fntSize)
                )));
                cell.setBackgroundColor(new GrayColor(0.7f));
                pdfTable.addCell(cell);
            }
        }

        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                if (selectedColumns == null || selectedColumns[j]) {
                    Object value = table.getModel().getValueAt(i, j);
                    if (value != null) {
                        pdfTable.addCell(new Phrase(lineSpacing, 
                                table.getModel().getValueAt(i, j).toString(), 
                                FontFactory.getFont(FontFactory.COURIER, fntSize)
                        ));
                    } else {
                        pdfTable.addCell("");
                    }
                }
            }
        }

        document.add(pdfTable);
    }

    /*
     * count the amount of columns of selected columns 
     */
    private int getColumnCount() {
        if (selectedColumns == null) {
            return table.getColumnCount();
        } else {
            int columnCount = 0;
            for (int i = 0; i < selectedColumns.length; i++) {
                if (selectedColumns[i]) {
                    columnCount++;
                }
            }
            return columnCount;
        }
    }
    
    /**
     * Creates a new pdf document
     */
    public void createPDF() {
        try {
            PdfPTable pdfTable = new PdfPTable(getColumnCount());
            openPdf();
            addData(pdfTable);
            closePdf();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
    }
}