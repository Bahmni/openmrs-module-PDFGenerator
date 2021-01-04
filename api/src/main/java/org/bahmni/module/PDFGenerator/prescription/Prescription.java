package org.bahmni.module.PDFGenerator.prescription;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import org.openmrs.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Component
public class Prescription {
    private Doctor doctor;
    private Patient patient;
    private List<Medicine> medicines;

    @Autowired
    private PrescriptionPDFConfig prescriptionPDFConfig;

    public Prescription(Doctor doctor, Patient patient, List<Medicine> medicines) {
        this.doctor = doctor;
        this.patient = patient;
        this.medicines = medicines;
    }

    public void preparePDFWithDoctorAndPatientDetails() throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter("doctor-patient.pdf"));
        Document document = new Document(pdfDocument, new PageSize(550, 150));
        document.setMargins(10,10,10,10);

        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Table table1 = new Table(2);

        Paragraph p1 = new Paragraph().setWidth(250f)
                .add(new Text("Doctor's Name:  ").setFont(bold).setFontSize(8f))
                .add(new Text(doctor.getName()).setFontSize(8f));
        Paragraph p2 = new Paragraph().setWidth(250f)
                .add(new Text("Doctor's Contact Information:  ").setFont(bold).setFontSize(8f))
                .add(new Text(doctor.getContactInformation()).setFontSize(8f));
        Cell cell1 = new Cell().setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).add(p1);
        Cell cell2 = new Cell().setBorder(Border.NO_BORDER).add(p2);
        table1.addCell(cell1); table1.addCell(cell2);
        document.add(table1);

        document.add(new Paragraph());
        SolidLine solidLine = new SolidLine();
        solidLine.setLineWidth(1f);
        LineSeparator line = new LineSeparator(solidLine);
        document.add(line);
        document.add(new Paragraph());

        Table table2 = new Table(2);

        Paragraph p3 = new Paragraph().setWidth(250f)
                .add(new Text("Patient's Phone number:  ").setFont(bold).setFontSize(8f))
                .add(new Text("8134562512").setFontSize(8f));

        Paragraph p4 = new Paragraph().setWidth(250f)
                .add(new Text("Patient's Name:  ").setFont(bold).setFontSize(8f))
                .add(new Text(patient.getFamilyName() + " " + patient.getGivenName()).setFontSize(8f));

        Paragraph p5 = new Paragraph().setWidth(250f)
                .add(new Text("Gender:  ").setFont(bold).setFontSize(8f))
                .add(new Text(patient.getGender()).setFontSize(8f));

        Paragraph p6 = new Paragraph().setWidth(250f)
                .add(new Text("Patient's Id:  ").setFont(bold).setFontSize(8f))
                .add(new Text("" + patient.getId()).setFontSize(8f));

        Paragraph p7 = new Paragraph().setWidth(250f)
                .add(new Text("Age:  ").setFont(bold).setFontSize(8f))
                .add(new Text("" + patient.getAge()).setFontSize(8f));

        Paragraph p8 = new Paragraph().setWidth(250f)
                .add(new Text("Patient's Address:  ").setFont(bold).setFontSize(8f))
                .add(new Text(patient.getPersonAddress().toString()).setFontSize(8f));

        Cell cell3 = new Cell().setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).add(p3);
        Cell cell4 = new Cell().setBorder(Border.NO_BORDER).add(p4);

        Cell cell5 = new Cell().setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).add(p5);
        Cell cell6 = new Cell().setBorder(Border.NO_BORDER).add(p6);

        Cell cell7 = new Cell().setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).add(p7);
        Cell cell8 = new Cell().setBorder(Border.NO_BORDER).add(p8);

        table2.addCell(cell3); table2.addCell(cell4); table2.addCell(cell5); table2.addCell(cell6); table2.addCell(cell7);
        table2.addCell(cell8);

        document.add(table2);

        document.close();
    }

    public void preparePDFWithSignatureAndDate() throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter("signature-date.pdf"));
        Document document = new Document(pdfDocument, new PageSize(550, 40));
        document.setMargins(10,10,10,10);

        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Table table = new Table(2);

        Paragraph p1 = new Paragraph().setWidth(250f)
                .add(new Text("Doctor's Signature:  ").setFont(bold).setFontSize(8f));

        Paragraph p2 = new Paragraph().setWidth(250f)
                .add(new Text("Date:  ").setFont(bold).setFontSize(8f));

        Cell cell1 = new Cell().setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).add(p1);
        Cell cell2 = new Cell().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).add(p2);

        table.addCell(cell1); table.addCell(cell2);

        document.add(table);

        document.close();
    }
}
