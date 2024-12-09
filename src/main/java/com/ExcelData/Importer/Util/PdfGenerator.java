package com.ExcelData.Importer.Util;

import com.ExcelData.Importer.Entity.Student;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class PdfGenerator {

    public static void generatePdf(List<Student> students, OutputStream outputStream) throws DocumentException, IOException {
        // Création du document PDF
        Document document = new Document(PageSize.A4);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        // Création du PdfWriter
        PdfWriter.getInstance(document, bufferedOutputStream);

        // Ouvrir le document pour écrire dedans
        document.open();

        // Titre du document avec une taille et un style spécifique
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
        Paragraph title = new Paragraph("Student list", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Espacement avant le tableau
        document.add(new Paragraph(" "));

        // Création du tableau pour afficher les étudiants
        PdfPTable table = new PdfPTable(6);  // 6 colonnes (Nom, Prénom, Email, etc.)

        // Définir la largeur du tableau pour qu'il occupe toute la largeur de la page
        table.setWidthPercentage(100);  // Remplir 100% de la largeur de la page

        // Définir la largeur des colonnes (en proportion)
        float[] columnWidths = {1f, 1f, 2f, 1.5f, 1.5f, 1f};  // Ajustez les valeurs en fonction des colonnes
        table.setWidths(columnWidths);

        // En-têtes du tableau
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        PdfPCell cell = new PdfPCell(new Phrase("Last name", headerFont));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("First name", headerFont));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Email", headerFont));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("City", headerFont));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Phone", headerFont));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Average", headerFont));
        cell.setBackgroundColor(BaseColor.DARK_GRAY);
        table.addCell(cell);

        // Ajouter les données des étudiants
        Font contentFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
        for (Student student : students) {
            table.addCell(new Phrase(student.getLastName(), contentFont));
            table.addCell(new Phrase(student.getFirstName(), contentFont));
            table.addCell(new Phrase(student.getEmail(), contentFont));
            table.addCell(new Phrase(student.getCity(), contentFont));
            table.addCell(new Phrase(student.getTel(), contentFont));
            table.addCell(new Phrase(String.valueOf(student.getAverage()), contentFont));
        }

        // Ajouter le tableau au document
        document.add(table);

        // Fermer le document
        document.close();

        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }


}
