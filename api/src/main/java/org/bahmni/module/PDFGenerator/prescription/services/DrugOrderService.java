package org.bahmni.module.PDFGenerator.prescription.services;

import org.openmrs.DrugOrder;
import org.openmrs.Patient;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DrugOrderService {

    List<DrugOrder> getPrescribedDrugOrders(List<String> visitUuids);

    List<DrugOrder> getActiveDrugOrders(String patientUuid, Date startDate, Date endDate);

    Patient getPatient(String patientUuid);

}
