package org.bahmni.module.PDFGenerator.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/prescription")
public class PrescriptionPDFController extends BaseRestController {

	private Log log = LogFactory.getLog(this.getClass());

	@Autowired
	ServletContext servletContext;

	@RequestMapping(method = RequestMethod.GET, value = "pdf")
	@ResponseBody
	public ResponseEntity<?> getPrescriptionPDF(HttpServletRequest request, HttpServletResponse response) {
		return ResponseEntity.ok("");
	}
}
