package org.bahmni.module.PDFGenerator.prescription;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import org.openmrs.Patient;
import org.springframework.stereotype.Component;

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

    private void attachAllPDFS() throws IOException {
        PdfDocument templateDoc = new PdfDocument(new PdfReader("template.pdf"));

        PdfDocument medicalPrescriptionDoc = new PdfDocument(new PdfWriter("medical-prescription.pdf"));

        PdfPage templatePage = templateDoc.getPage(1);
        Rectangle template = templatePage.getPageSizeWithRotation();

        PdfDocument medicinesDoc = new PdfDocument(new PdfReader("medicines.pdf"));

        PdfDocument patientDoctorDoc = new PdfDocument(new PdfReader("doctor-patient.pdf"));
        PdfPage pdPage = patientDoctorDoc.getPage(1);
        PdfFormXObject doctorPatientCopy = pdPage.copyAsFormXObject(medicalPrescriptionDoc);

        PdfDocument signatureDateDoc = new PdfDocument(new PdfReader("signature-date.pdf"));
        PdfPage signatureDatePage = signatureDateDoc.getPage(1);
        PdfFormXObject signatureDateCopy = signatureDatePage.copyAsFormXObject(medicalPrescriptionDoc);

        int noOfPages = medicinesDoc.getNumberOfPages();

        for (int i=1; i<=noOfPages; i++) {
            PdfPage page = medicalPrescriptionDoc.addNewPage(templateDoc.getDefaultPageSize());

            PdfCanvas canvas = new PdfCanvas(page);

            AffineTransform transformationMatrix = AffineTransform.getScaleInstance(page.getPageSize().getWidth() / template.getWidth(), page.getPageSize().getHeight() / template.getHeight());
            canvas.concatMatrix(transformationMatrix);

            PdfFormXObject pageCopy = templatePage.copyAsFormXObject(medicalPrescriptionDoc);
            canvas.addXObject(pageCopy, 0, 0);

            canvas.addXObject(doctorPatientCopy, 0, 422);

            PdfPage medicinePage = medicinesDoc.getPage(i);
            PdfFormXObject prescriptionPageCopy = medicinePage.copyAsFormXObject(medicalPrescriptionDoc);
            canvas.addXObject(prescriptionPageCopy, 0, 90);


            canvas.addXObject(signatureDateCopy, 0, 50);
        }
        medicalPrescriptionDoc.close();
        medicinesDoc.close();
        signatureDateDoc.close();
        patientDoctorDoc.close();
    }

    public byte[] createPrescriptionPDF() throws IOException {
        this.loadPDFProperties();
        this.preparePDFWithDoctorAndPatientDetails();
        this.preparePDFWithAllMedicines();
        this.preparePDFWithSignatureAndDate();
        this.attachAllPDFS();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfReader("medical-prescription.pdf"), new PdfWriter(baos));

        return baos.toByteArray();
    }

}
