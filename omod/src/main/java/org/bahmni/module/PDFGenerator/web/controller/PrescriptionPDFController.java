package org.bahmni.module.PDFGenerator.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bahmni.module.PDFGenerator.prescription.services.DrugOrderService;
import org.bahmni.module.bahmnicore.service.BahmniObsService;
import org.bahmni.module.bahmnicore.util.BahmniDateUtil;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.module.bahmniemrapi.drugorder.contract.BahmniDrugOrder;
import org.openmrs.module.bahmniemrapi.drugorder.contract.BahmniOrderAttribute;
import org.openmrs.module.bahmniemrapi.encountertransaction.contract.BahmniObservation;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	BahmniObsService bahmniObsService;

	private Log log = LogFactory.getLog(this.getClass());

//	@RequestMapping(method = RequestMethod.GET, value = "pdf")
//	@ResponseBody
//	public List<DrugOrder> getPrescriptionPDF() {
//
//		List<String> visitUuids = new ArrayList<>();
//		visitUuids.add("3786befb-1c47-437d-b1e3-c0593c8ab9fa");
//
//		List<DrugOrder> prescribedDrugOrders = drugOrderService.getPrescribedDrugOrders(visitUuids);
//		log.warn(prescribedDrugOrders.get(0).toString());
//		log.warn(prescribedDrugOrders.get(1).toString());
//		return prescribedDrugOrders;
//	}

	@RequestMapping(method = RequestMethod.GET, value = "pdf")
	@ResponseBody
	public List<DrugOrder> getPrescriptionPDF(@RequestParam(value = "patientUuid") String patientUuid,
													 @RequestParam(value = "startDate", required = false) String startDateStr,
													 @RequestParam(value = "endDate", required = false) String endDateStr) throws ParseException {
		log.info("Retrieving active drug orders for patient with uuid " + patientUuid);
		Date startDate = BahmniDateUtil.convertToDate(startDateStr, BahmniDateUtil.DateFormatType.UTC);
		Date endDate = BahmniDateUtil.convertToDate(endDateStr, BahmniDateUtil.DateFormatType.UTC);
		return getActiveOrders(patientUuid, startDate, endDate);
	}

	private List<DrugOrder> getActiveOrders(String patientUuid, Date startDate, Date endDate) {
		List<DrugOrder> activeDrugOrders = drugOrderService.getActiveDrugOrders(patientUuid, startDate, endDate);
		log.info(activeDrugOrders.size() + " active drug orders found");
		for (DrugOrder dr: activeDrugOrders) {
			log.warn(dr.toString());
		}
		return activeDrugOrders;
	}

//	private List<BahmniDrugOrder> getBahmniDrugOrders(String patientUuid, List<DrugOrder> drugOrders) {
//		Map<String, DrugOrder> drugOrderMap = drugOrderService.getDiscontinuedDrugOrders(drugOrders);
//		try {
//			Collection<BahmniObservation> orderAttributeObs = bahmniObsService.observationsFor(patientUuid, getOrdAttributeConcepts(), null, null, false, null, null, null);
//			List<BahmniDrugOrder> bahmniDrugOrders = bahmniDrugOrderMapper.mapToResponse(drugOrders, orderAttributeObs, drugOrderMap , null);
//			return sortDrugOrdersAccordingToTheirSortWeight(bahmniDrugOrders);
//		} catch (IOException e) {
//			log.error("Could not parse drug order", e);
//			throw new RuntimeException("Could not parse drug order", e);
//		}
//	}
//
//	private Collection<Concept> getOrdAttributeConcepts() {
//		Concept orderAttribute = conceptService.getConceptByName(BahmniOrderAttribute.ORDER_ATTRIBUTES_CONCEPT_SET_NAME);
//		return orderAttribute == null ? Collections.EMPTY_LIST : orderAttribute.getSetMembers();
//	}
}