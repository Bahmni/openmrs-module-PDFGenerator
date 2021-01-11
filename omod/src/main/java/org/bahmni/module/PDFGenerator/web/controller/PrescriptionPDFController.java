package org.bahmni.module.PDFGenerator.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/prescription")
public class PrescriptionPDFController extends BaseRestController {

	private Log log = LogFactory.getLog(this.getClass());

	@RequestMapping(method = RequestMethod.GET, value = "pdf")
	@ResponseBody
	public ResponseEntity<?> getPrescriptionPDF(HttpServletRequest request, HttpServletResponse response) {
		return ResponseEntity.ok("");
	}
}
