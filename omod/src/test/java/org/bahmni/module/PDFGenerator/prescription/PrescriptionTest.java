package org.bahmni.module.PDFGenerator.prescription;

import com.itextpdf.text.DocumentException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class PrescriptionTest {

    private Prescription prescription;

    @Before
    public void setUp() {
        Doctor doctor = new Doctor("Steven Strange", "strange@avengers.com");

        PersonName personName = new PersonName();
        personName.setFamilyName("Steve");
        personName.setGivenName("Rogers");
        Person person = new Person();
        person.addName(personName);
        PersonAddress personAddress = new PersonAddress();
        personAddress.setAddress1("New Jersy United Sates of america");
        personAddress.setAddress2("Road Number: 243");
        personAddress.setAddress3("House Number: 24");
        personAddress.setAddress4("Street Name: Avengers Street");
        personAddress.setPostalCode("345567");

        person.addAddress(personAddress);
        person.setGender("Male");

        person.setId(123456);

        Patient patient = new Patient(person);

        List<Medicine> medicines = new ArrayList<>();

        Medicine medicine1 = new Medicine("Paracetamol", "20 MG", "10", "3 per day", "2 days", "10","After having food");
        Medicine medicine2 = new Medicine("Medicine2", "20 MG",  "10", "3 per day", "2 days", "10","After having food");
        Medicine medicine3 = new Medicine("Medicine3", "20 MG",  "10","3 per day", "2 days", "10","After having food");
        Medicine medicine4 = new Medicine("Medicine4", "20 MG",  "10","3 per day", "2 days", "10","After having food");
        Medicine medicine5 = new Medicine("Medicine5", "20 MG",  "10","3 per day", "2 days", "10","After having food");
        Medicine medicine6 = new Medicine("Medicine6", "20 MG",  "10","3 per day", "2 days", "10","After having food");
        Medicine medicine7 = new Medicine("Medicine7", "20 MG",  "10","3 per day", "2 days", "10","After having food");
        Medicine medicine8 = new Medicine("Medicine8", "20 MG",  "10","3 per day", "2 days", "10","After having food");
        Medicine medicine9 = new Medicine("Medicine9", "20 MG",  "10","3 per day", "2 days", "10","After having food");
        Medicine medicine10 = new Medicine("Medicine10", "20 MG",  "10","3 per day", "2 days", "10","After having food");
        Medicine medicine11 = new Medicine("Medicine11", "20 MG",  "10","3 per day", "2 days", "10","After having food");
        Medicine medicine12 = new Medicine("Medicine12", "20 MG",  "10","3 per day", "2 days", "10","After having food");

        medicines.add(medicine1); medicines.add(medicine2); medicines.add(medicine3); medicines.add(medicine4);
        medicines.add(medicine5); medicines.add(medicine6); medicines.add(medicine7); medicines.add(medicine8);
        medicines.add(medicine9); medicines.add(medicine10); medicines.add(medicine11); medicines.add(medicine12);

        medicines.add(medicine1); medicines.add(medicine2); medicines.add(medicine3); medicines.add(medicine4);
        medicines.add(medicine5); medicines.add(medicine6); medicines.add(medicine7); medicines.add(medicine8);
        medicines.add(medicine9); medicines.add(medicine10); medicines.add(medicine11); medicines.add(medicine12);

        prescription = new Prescription(doctor, patient, medicines);
    }

    @After
    public void clear() {
        prescription = null;
    }

    @Test
    public void shouldReturnByteArrayOfCreatedPrescriptionPDF() {
        try {
            byte[] prescriptionPDFBytes = prescription.createPrescriptionPDF();
        } catch (DocumentException e) {
            fail("Document Exception while creating Prescription PDF");
        } catch (IOException e) {
            fail("IO Exception while creating Prescription PDF");
        }
    }


}