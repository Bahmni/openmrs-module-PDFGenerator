package org.bahmni.module.PDFGenerator.prescription;

import org.junit.Test;
import org.openmrs.*;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionTest {

    @Test
    public void shouldCreatePDFWithDoctorAndPatientDetails() throws IOException {
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

        Medicine medicine1 = new Medicine("Paracetamol", "20 MG", "3 per day", "2 days", "10","After having food");
        Medicine medicine2 = new Medicine("Medicine2", "20 MG", "3 per day", "2 days", "10","After having food");
        Medicine medicine3 = new Medicine("Medicine3", "20 MG", "3 per day", "2 days", "10","After having food");
        Medicine medicine4 = new Medicine("Medicine4", "20 MG", "3 per day", "2 days", "10","After having food");
        medicines.add(medicine1); medicines.add(medicine2); medicines.add(medicine3); medicines.add(medicine4);

        Prescription prescription = new Prescription(doctor, patient, medicines);

        prescription.prepareDoctorAndPatientPDF();
    }

}