package org.bahmni.module.PDFGenerator.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/rest/v1/prescription")
public class PrescriptionController extends BaseRestController {


	private Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(method = RequestMethod.GET, value = "pdf")
	@ResponseBody
	public ResponseEntity<?> getPrescriptionPDF(@RequestParam String prescription) throws IOException {
		/*byte[] pdfInBytes = prescriptionMapper.getPdf(prescription);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=prescription.pdf")
				.contentType(MediaType.valueOf("application/pdf"))
				.body(pdfInBytes);*/
		return ResponseEntity.ok()
				.body(prescription);
	}
}
