package org.bahmni.module.PDFGenerator.prescription;

import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.DeviceGray;
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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.openmrs.Patient;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@Component("prescription")
public class Prescription {
    private Doctor doctor;
    private Patient patient;
    private List<Medicine> medicines;

    private Properties properties;

    private PrescriptionPDFConfig prescriptionPDFConfig = new PrescriptionPDFConfig();

    public Prescription(Doctor doctor, Patient patient, List<Medicine> medicines) {
        this.doctor = doctor;
        this.patient = patient;
        this.medicines = medicines;
    }

    private void preparePDFWithDoctorAndPatientDetails() throws IOException {
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

        document.add(new Paragraph());
        document.add(line);
        document.add(new Paragraph());

        document.close();
    }

    private void preparePDFWithSignatureAndDate() throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter("signature-date.pdf"));
        Document document = new Document(pdfDocument, new PageSize(550, 60));
        document.setMargins(10,10,10,10);

        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        document.add(new Paragraph());
        SolidLine solidLine = new SolidLine();
        solidLine.setLineWidth(1f);
        LineSeparator line = new LineSeparator(solidLine);
        document.add(line);
        document.add(new Paragraph());

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

    private void loadPDFProperties() throws IOException {
        this.properties = prescriptionPDFConfig.getProperties();
    }

    private void preparePDFWithAllMedicines() throws IOException {
        int medicinePartStart = Integer.parseInt(this.properties.getProperty("prescription.template.height")) -
                                    (Integer.parseInt(this.properties.getProperty("prescription.template.headerSize")) + 150);
        int medicinePartEnd = Integer.parseInt(this.properties.getProperty("prescription.template.footerSize")) + 60;
        int medicinePartHeight = medicinePartStart - medicinePartEnd;

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter("medicines.pdf"));
        Document document = new Document(pdfDocument, new PageSize(550, medicinePartHeight));
        document.setMargins(10,10,10,10);

        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Paragraph heading = new Paragraph().add(new Text("Prescriptions: ").setFont(bold).setFontSize(10f));
        document.add(heading);

        Table medicinesTable = new Table(7);
        medicinesTable.setWidth(500f);

        Cell[] headerCells = new Cell[]{
                new Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10f).setPadding(4f).setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Medicine").setFont(bold)),
                new Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10f).setPadding(4f).setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Dose").setFont(bold)),
                new Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10f).setPadding(4f).setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Unit").setFont(bold)),
                new Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10f).setPadding(4f).setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Frequent").setFont(bold)),
                new Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10f).setPadding(4f).setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Duration").setFont(bold)),
                new Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10f).setPadding(4f).setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Quantity").setFont(bold)),
                new Cell().setTextAlignment(TextAlignment.CENTER).setFontSize(10f).setPadding(4f).setBackgroundColor(new DeviceGray(0.75f)).add(new Paragraph("Additional Information").setFont(bold))
        };

        for (Cell cell:headerCells) {
            medicinesTable.addHeaderCell(cell);
        }

        for (Medicine medicine: this.medicines) {
            Cell cell;
            String []medicineDetails = medicine.toString().split("<!>");
            for(String detail: medicineDetails) {
                cell = new Cell().setFontSize(8f).setPadding(4f).setTextAlignment(TextAlignment.CENTER).add(new Paragraph(detail.trim()));
                medicinesTable.addCell(cell);
            }
        }
        document.add(medicinesTable);

        document.close();
    }

    private void attachAllPDFS() throws IOException, DocumentException {
        PdfReader templateReader = new PdfReader("template.pdf");
        PdfStamper prescriptionPDF = new PdfStamper(templateReader, new FileOutputStream("medical-prescription.pdf"));

        PdfReader medicineReader = new PdfReader("medicines.pdf");

        int noOfPages = medicineReader.getNumberOfPages();

        for (int i=1; i<=noOfPages; i++) {
            if(i > 1)  prescriptionPDF.replacePage(templateReader, 1, i);

            PdfContentByte canvas = prescriptionPDF.getOverContent(i);

            PdfReader patientDoctorDetailsPDFReader = new PdfReader("doctor-patient.pdf");

            PdfImportedPage page = prescriptionPDF.getImportedPage(patientDoctorDetailsPDFReader, 1);

            com.itextpdf.text.Image patientDoctorDetailsPDFInstance = com.itextpdf.text.Image.getInstance(page);

            int doctorPatientDetailsEndingPosition = Integer.parseInt(this.properties.getProperty("prescription.template.height")) -
                    (Integer.parseInt(this.properties.getProperty("prescription.template.headerSize")) + 150);

            AffineTransform at = AffineTransform.getTranslateInstance(0, doctorPatientDetailsEndingPosition);
            at.concatenate(AffineTransform.getScaleInstance(patientDoctorDetailsPDFInstance.getScaledWidth(), patientDoctorDetailsPDFInstance.getScaledHeight()));

            canvas.addImage(patientDoctorDetailsPDFInstance, at);




            PdfImportedPage medicinePage = prescriptionPDF.getImportedPage(medicineReader, i);

            com.itextpdf.text.Image medicinePartInstance = com.itextpdf.text.Image.getInstance(medicinePage);

            int medicinePartEndingPosition = Integer.parseInt(this.properties.getProperty("prescription.template.footerSize")) + 60;

            at = AffineTransform.getTranslateInstance(0, medicinePartEndingPosition);
            at.concatenate(AffineTransform.getScaleInstance(medicinePartInstance.getScaledWidth(), medicinePartInstance.getScaledHeight()));

            canvas.addImage(medicinePartInstance, at);



            PdfReader signatureDatePDFReader = new PdfReader("signature-date.pdf");

            PdfImportedPage signatureDatePage = prescriptionPDF.getImportedPage(signatureDatePDFReader, 1);

            com.itextpdf.text.Image signatureDatePDFInstance = com.itextpdf.text.Image.getInstance(signatureDatePage);

            int signatureDatePartEndingPosition = Integer.parseInt(this.properties.getProperty("prescription.template.footerSize"));

            at = AffineTransform.getTranslateInstance(0, signatureDatePartEndingPosition);
            at.concatenate(AffineTransform.getScaleInstance(signatureDatePDFInstance.getScaledWidth(), signatureDatePDFInstance.getScaledHeight()));

            canvas.addImage(signatureDatePDFInstance, at);



            if (i+1 <= noOfPages) prescriptionPDF.insertPage(i+1, new Rectangle(Integer.parseInt(this.properties.getProperty("prescription.template.width")), Integer.parseInt(this.properties.getProperty("prescription.template.height"))));
        }
        prescriptionPDF.close();
    }

    public byte[] createPrescriptionPDF() throws IOException, DocumentException {
        this.loadPDFProperties();
        this.preparePDFWithDoctorAndPatientDetails();
        this.preparePDFWithAllMedicines();
        this.preparePDFWithSignatureAndDate();
        this.attachAllPDFS();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new com.itextpdf.kernel.pdf.PdfReader("medical-prescription.pdf"), new PdfWriter(baos));

        return baos.toByteArray();
    }

}
