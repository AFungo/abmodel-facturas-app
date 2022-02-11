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

public class JTableToPdf {

    private JFrame frame;
    private JTable table;
    private JButton button;
    private Document document;
    private PdfWriter writer;
    private JScrollPane scrollPane;

    public JTableToPdf (JTable table) {
        this.table = table;
    }

    public void openPdf() throws FileNotFoundException, DocumentException {
        document = new Document(PageSize.A4, 0f, 0f, 30, 30);
        writer = PdfWriter.getInstance(document, new FileOutputStream("Tabla-Proveedores.pdf"));
        document.open();
    }

    public void closePdf() {
        document.close();
    }

    public void addData(PdfPTable pdfTable) throws DocumentException {
        pdfTable.setHeaderRows(1);
        
        float fntSize, lineSpacing;
        fntSize = 6.7f;
        lineSpacing = 10f;
        
        PdfPCell cell;
        for (int i = 0; i < table.getColumnCount(); i++) {
            cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, table.getColumnName(i), FontFactory.getFont(FontFactory.COURIER, fntSize))));
            cell.setBackgroundColor(new GrayColor(0.7f));
            pdfTable.addCell(cell);
        }

        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                Object value = table.getModel().getValueAt(i, j);
                if (value != null) {
                    pdfTable.addCell(new Phrase(lineSpacing, table.getModel().getValueAt(i, j).toString(), FontFactory.getFont(FontFactory.COURIER, fntSize)));
                } else {
                    pdfTable.addCell("");
                }
            }
        }

        document.add(pdfTable);
    }

    public void createAndShowGui() {
        frame = new JFrame("PDF creator");

        int rows = 100;
        int cols = 3;

        String data [][] = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = i + "-" + j;
            }
        }

        button = new JButton("Create PDF");

        scrollPane = new JScrollPane(table);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
                    openPdf();
                    addData(pdfTable);
                    closePdf();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (DocumentException e1) {
                    e1.printStackTrace();
                }
            }
        });

//      frame.add(table.getTableHeader(), BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}