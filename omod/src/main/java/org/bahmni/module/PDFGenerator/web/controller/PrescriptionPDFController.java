package org.bahmni.module.PDFGenerator.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bahmni.module.PDFGenerator.prescription.Doctor;
import org.bahmni.module.PDFGenerator.prescription.Medicine;
import org.bahmni.module.PDFGenerator.prescription.Prescription;
import org.bahmni.module.PDFGenerator.prescription.services.DrugOrderService;
import org.bahmni.module.bahmnicore.util.BahmniDateUtil;
import org.openmrs.DrugOrder;
import org.openmrs.Patient;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping(value = "/prescription")
public class PrescriptionPDFController extends BaseRestController {

	@Autowired
	DrugOrderService drugOrderService;

	private Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(method = RequestMethod.GET, value = "pdf")
	@ResponseBody
	public ResponseEntity<?> getPrescriptionPDF(@RequestParam(value = "patientUuid") String patientUuid,
													 @RequestParam(value = "startDate", required = false) String startDateStr,
													 @RequestParam(value = "endDate", required = false) String endDateStr) throws ParseException, IOException {
		log.info("Retrieving active drug orders for patient with uuid " + patientUuid);
		Date startDate = BahmniDateUtil.convertToDate(startDateStr, BahmniDateUtil.DateFormatType.UTC);
		Date endDate = BahmniDateUtil.convertToDate(endDateStr, BahmniDateUtil.DateFormatType.UTC);

		Doctor doctor = new Doctor("superman", "superman@bahmni.com");
		Patient patient = drugOrderService.getPatient(patientUuid);
		List<Medicine> medicines = new ArrayList<>();
		List<DrugOrder> drugOrders = getActiveOrders(patientUuid, startDate, endDate);
		for (DrugOrder dr: drugOrders) {
			Medicine medicine = new Medicine(dr.getDrug().getName(), ""+dr.getDose(), dr.getQuantityUnits().getDisplayString(), dr.getFrequency().toString(), "" + dr.getDuration(), "" + dr.getQuantity(), dr.getDosingInstructions());
			medicines.add(medicine);
		}

		Prescription prescription = new Prescription(doctor, patient, medicines);

		byte[] prescriptionPDFInBytes = prescription.createPrescriptionPDF();

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=prescription.pdf")
				.contentType(MediaType.parseMediaType("application/pdf"))
				.body(prescriptionPDFInBytes);
	}

	private List<DrugOrder> getActiveOrders(String patientUuid, Date startDate, Date endDate) {
		List<DrugOrder> activeDrugOrders = drugOrderService.getActiveDrugOrders(patientUuid, startDate, endDate);
		return activeDrugOrders;
	}
}